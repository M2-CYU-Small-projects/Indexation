#include "img_processes.h"

#include <memory.h>

#include "img/process/convolutions.h"

#define THRESHOLD 30

void fillHistogram(byte** pixels, ImageInfos* infos, long* array)
{
    memset(array, 0, 256 * sizeof(long));
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            array[pixels[h][w]]++;
        }
    }
}

void fillColorHistograms(rgb8** pixels, ImageInfos* infos, long *greyHist, long *redHist, long *greenHist, long *blueHist)
{
    memset(greyHist, 0, 256 * sizeof(long));
    memset(redHist, 0, 256 * sizeof(long));
    memset(greenHist, 0, 256 * sizeof(long));
    memset(blueHist, 0, 256 * sizeof(long));
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            rgb8 pixel = pixels[h][w];
            byte greyVal = rgbToGrey(pixel.r, pixel.g, pixel.b);
            greyHist[greyVal]++;
            redHist[pixel.r]++;
            greenHist[pixel.g]++;
            blueHist[pixel.b]++;
        }
    }
}

void fillHistogramZeroes(long* array)
{
    memset(array, 0, 256 * sizeof(long));
}

double matrixAverage(byte** matrix, ImageInfos* infos)
{
    unsigned long sum = 0l;
    unsigned long count = 0l;
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            sum += matrix[h][w];
            count += 1;
        }
    }
    return (double)sum / (double)count;
}

int averageColorGreyscale(byte** pixels, ImageInfos* infos, double* redRatio, double* greenRatio, double* blueRatio)
{
    unsigned long sum = 0l;
    unsigned long count = 0l;
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            sum += pixels[h][w];
            count += 1;
        }
    }
    byte average = (double) sum / (double) count * 255.0;
    // This is not true, but this adds to the fact that this
    // is a greyscale image.
    *redRatio = *blueRatio = *greenRatio = 0.0;
    return rgbToInt(average, average, average);
}

int averageColor(rgb8** pixels, ImageInfos* infos, double* redRatio, double* greenRatio, double* blueRatio)
{
    unsigned long redSum = 0l, greenSum = 0l, blueSum = 0l;
    unsigned long count = 0l;

    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            rgb8 pixel = pixels[h][w];
            redSum += pixel.r;
            greenSum += pixel.g;
            blueSum += pixel.b;
            count += 1;
        }
    }
    unsigned long total = redSum + greenSum + blueSum;
    byte redAverage = (double) redSum / (double) total * 255.0;
    byte greenAverage = (double) greenSum / (double) total * 255.0;
    byte blueAverage = (double) blueSum / (double) total * 255.0;

    *redRatio = (double)redSum / (double)total;
    *greenRatio = (double)greenSum / (double)total;
    *blueRatio = (double)blueSum / (double)total;

    return rgbToInt(redAverage, greenAverage, blueAverage);
}

int rgbToInt(byte r, byte g, byte b)
{
    return (r << 16) | (g << 8) | (b);
}

void intToRbg(int val, byte* r, byte* g, byte* b)
{
    *r = (val >> 16) & 0x0ff;
    *g = (val >> 8) & 0x0ff;
    *b = (val) & 0x0ff;
}

byte rgbToGrey(byte r, byte g, byte b)
{
    return (double)r * 0.3 + (double)g * 0.59 + (double)b * 0.11;
}

byte** rgbImageToGreyscale(rgb8** pixels, ImageInfos* infos)
{
    // Use the luminosity method, as this provides better results than other ones
    // grayscale = 0.3 * R + 0.59 * G + 0.11 * B
    byte** greyscale = bmatrix(infos->nrl, infos->nrh, infos->ncl, infos->nch);
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            rgb8 p = pixels[h][w];
            greyscale[h][w] = rgbToGrey(p.r, p.g, p.b);
        }
    }
    return greyscale;
}

byte** rgbImageToGreyscaleFromOneChannel(rgb8** pixels, ImageInfos* infos)
{
    byte** greyscale = bmatrix(infos->nrl, infos->nrh, infos->ncl, infos->nch);
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            rgb8 p = pixels[h][w];
            greyscale[h][w] = (pixels[h][w]).r;
        }
    }
    return greyscale;
}

int isGreyscale(rgb8** pixels, ImageInfos* infos)
{
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            rgb8 pixel = pixels[h][w];
            if (!(pixel.r == pixel.g && pixel.g == pixel.b))
            {
                return 0;
            }
        }
    }
    return 1;
}

void gradientNormRelated(byte** pixels, ImageInfos* infos, double* gradientNormAverage, Rect* outlinesBox, Position* outlinesBarycenter, long* nbOutlines)
{
    outlinesBarycenter->x = 0;
    byte** gradientNorm = gradientNorm3x3(pixels, *infos);
    *gradientNormAverage = matrixAverage(gradientNorm, infos);
    byte** outlines = contourMatrix3x3FromGradientNorm(gradientNorm, *infos, THRESHOLD);
    // Find the box, coords sums (for barycenter) and count
    *nbOutlines = 0;
    unsigned long barycenterSumX = 0l, barycenterSumY = 0l;
    long outMinX = 9999999999, outMaxX = 0, outMinY = 9999999999, outMaxY = 0;
    for (long h = infos->nrl; h <= infos->nrh; h++)
    {
        for (long w = infos->ncl; w <= infos->nch; w++)
        {
            if (outlines[h][w] == 0)
            {
                continue;
            }

            // sums
            (*nbOutlines)++;
            barycenterSumX += w;
            barycenterSumY += h;

            // min / max assignation
            if (w < outMinX)
            {
                outMinX = w;
            }
            else if (w > outMaxX)
            {
                outMaxX = w;
            }

            if (h < outMinY)
            {
                outMinY = h;
            }
            else if (h > outMaxY)
            {
                outMaxY = h;
            }
        }
    }
    // Compute final values
    outlinesBarycenter->x = barycenterSumX / (*nbOutlines);
    outlinesBarycenter->y = barycenterSumY / (*nbOutlines);

    outlinesBox->minX = outMinX;
    outlinesBox->maxX = outMaxX;
    outlinesBox->minY = outMinY;
    outlinesBox->maxY = outMaxY;

    freeGreyscale(gradientNorm, infos);
    freeGreyscale(outlines, infos);
}

void freeGreyscale(byte** pixels, ImageInfos* infos)
{
    free_bmatrix(pixels, infos->nrl, infos->nrh, infos->ncl, infos->nch);
}

void freeRGB(rgb8** pixels, ImageInfos* infos)
{
    free_rgb8matrix(pixels, infos->nrl, infos->nrh, infos->ncl, infos->nch);
}
