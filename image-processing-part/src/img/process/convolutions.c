#include "convolutions.h"
#include <math.h>
#include <stdlib.h>
#include <stdio.h>

void rgb8MatrixToByteMatrixes(rgb8** matrix, ImageInfos imageInfos, byte*** rMatrix, byte*** gMatrix, byte*** bMatrix)
{
    *rMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    *gMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    *bMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);

    for (long h = imageInfos.nrl; h <= imageInfos.nrh; h++)
    {
        for (long w = imageInfos.ncl; w <= imageInfos.nch; w++)
        {
            rgb8 pixel = matrix[h][w];
            (*rMatrix)[h][w] = pixel.r;
            (*gMatrix)[h][w] = pixel.g;
            (*bMatrix)[h][w] = pixel.b;
        }
    }
}

long doOneConv(byte** matrix, ImageInfos imageInfos, long h, long w, int** filter, long size)
{
    long sum = 0l;
    long center = size / 2;
    for (long i = 0; i < size; i++)
    {
        for (long j = 0; j < size; j++)
        {
            long currentH = h - center + i;
            long currentW = w - center + j;
            // check if out of bounds
            if (currentH >= imageInfos.nrl && currentH < imageInfos.nrh
                && currentW >= imageInfos.ncl && currentW < imageInfos.nch)
            {
                sum += matrix[currentH][currentW] * filter[i][j];
            }
        }

    }
    return sum;
}

byte** convoluteMatrix(byte** matrix, ImageInfos imageInfos, int** filter, long size, byte divideBy)
{
    byte** outputMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    // decal depend on the center
    for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
    {
        for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
        {
            long conv = doOneConv(matrix, imageInfos, h, w, filter, size);
            conv = abs(conv) / divideBy;
            outputMatrix[h][w] = conv;
        }
    }
    return outputMatrix;
}

byte** horizontalGradient3x3(byte** matrix, ImageInfos imageInfos)
{
    int** horizontalFilter = malloc(3 * sizeof(int**));
    horizontalFilter[0] = (int[3]){ -1, 0, 1 };
    horizontalFilter[1] = (int[3]){ -2, 0, 2 };
    horizontalFilter[2] = (int[3]){ -1, 0, 1 };
    byte** m = convoluteMatrix(matrix, imageInfos, horizontalFilter, 3, 4);
    free(horizontalFilter);
    return m;
}

byte** verticalGradient3x3(byte** matrix, ImageInfos imageInfos)
{
    int** verticalFilter = malloc(3 * sizeof(int**));
    verticalFilter[0] = (int[3]){ -1, -2, -1 };
    verticalFilter[1] = (int[3]){ 0,  0,  0 };
    verticalFilter[2] = (int[3]){ 1,  2,  1 };
    byte** m = convoluteMatrix(matrix, imageInfos, verticalFilter, 3, 4);
    free(verticalFilter);
    return m;
}

byte** gradientNorm3x3(byte** matrix, ImageInfos imageInfos)
{
    byte** verticalGradient = verticalGradient3x3(matrix, imageInfos);
    byte** horizontalGradient = horizontalGradient3x3(matrix, imageInfos);
    byte** outputMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
    {
        for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
        {
            byte vg = verticalGradient[h][w];
            byte hg = horizontalGradient[h][w];
            outputMatrix[h][w] = sqrt((vg * vg) + (hg * hg));
        }
    }
    free_bmatrix(verticalGradient, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(horizontalGradient, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    return outputMatrix;
}

byte** contourMatrix3x3(byte** matrix, ImageInfos imageInfos, byte threshold)
{
    byte** verticalGradient = verticalGradient3x3(matrix, imageInfos);
    byte** horizontalGradient = horizontalGradient3x3(matrix, imageInfos);
    byte** outputMatrix = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
    {
        for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
        {
            byte vg = verticalGradient[h][w];
            byte hg = horizontalGradient[h][w];
            byte norm = sqrt((vg * vg) + (hg * hg));
            outputMatrix[h][w] = norm >= threshold ? 255 : 0;
        }
    }
    free_bmatrix(verticalGradient, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(horizontalGradient, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    return outputMatrix;
}

byte** contourMatrix3x3FromGradientNorm(byte** gradientNorm, ImageInfos imageInfos, byte threshold)
{
    byte** countours = bmatrix(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
    {
        for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
        {
            countours[h][w] = gradientNorm[h][w] >= threshold ? 255 : 0;
        }
    }
    return countours;
}

// Only for contourRgb3x3
int hasFullyDetectedPixelArround(byte** fullyDetected, long h, long w)
{
    for (long i = -1; i < 1; i++)
    {
        for (long j = -1; j < 1; j++)
        {
            if (i == 0 && j == 0) continue;
            if (fullyDetected[h + i][w + j] == 255)
            {
                return 1;

            }
        }
    }
    return 0;
}

byte** contourRgb3x3(rgb8** matrix, ImageInfos imageInfos, byte redThreshold, byte greenThreshold, byte blueThreshold, byte sh, byte sb)
{
    byte** Rmatrix;
    byte** Gmatrix;
    byte** Bmatrix;
    rgb8MatrixToByteMatrixes(matrix, imageInfos, &Rmatrix, &Gmatrix, &Bmatrix);

    byte** Rcontour = contourMatrix3x3(Rmatrix, imageInfos, redThreshold);
    byte** Gcontour = contourMatrix3x3(Gmatrix, imageInfos, greenThreshold);
    byte** Bcontour = contourMatrix3x3(Bmatrix, imageInfos, blueThreshold);

    free_bmatrix(Rmatrix, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(Gmatrix, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(Bmatrix, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);

    // Gather byte matrixes
    byte** detected = bmatrix0(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    byte** fullyDetected = bmatrix0(imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);

    // first pass, only fully-detected
    for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
    {
        for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
        {
            byte nbDetected = 0;
            if (Rcontour[h][w] == 255) nbDetected++;
            if (Gcontour[h][w] == 255) nbDetected++;
            if (Bcontour[h][w] == 255) nbDetected++;
            if (nbDetected >= sh)
            {
                fullyDetected[h][w] = 255;
                detected[h][w] = 255;
            }
        }
    }

    // second pass, adding less detected pixels
    if (sb < sh)
    {
        for (long h = imageInfos.nrl + 1; h < imageInfos.nrh - 1; h++)
        {
            for (long w = imageInfos.ncl + 1; w < imageInfos.nch - 1; w++)
            {
                byte nbDetected = 0;
                if (Rcontour[w][h] == 255) nbDetected++;
                if (Gcontour[w][h] == 255) nbDetected++;
                if (Bcontour[w][h] == 255) nbDetected++;
                if (nbDetected >= sb)
                {
                    // find if we have a fully detected pixel arround
                    // don't need to bother about edges here
                    if (hasFullyDetectedPixelArround(fullyDetected, h, w))
                    {
                        detected[h][w] = 255;
                    }
                }
            }
        }
    }
    free_bmatrix(Rcontour, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(Gcontour, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(Bcontour, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);
    free_bmatrix(detected, imageInfos.nrl, imageInfos.nrh, imageInfos.ncl, imageInfos.nch);

    return fullyDetected;
}