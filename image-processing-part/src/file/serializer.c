#include "serializer.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "file/file_stat.h"

void writeLongArray(char* name, long* array, int size, FILE* file)
{
    fprintf(file, "%s:", name);
    for (int i = 0; i < size - 1; i++)
    {
        fprintf(file, "%ld,", array[i]);
    }
    fprintf(file, "%ld", array[size - 1]);
    fprintf(file, "\n");
}

int serialize_descriptors(ImgDescriptors* descriptors, char* outputDirectory)
{
    // We want path/to/file/name.txt
    char completePath[4096] = {0};
    char* extension = findExtension(descriptors->name);

    strcat(completePath, outputDirectory);
    strcat(completePath, "/");
    if (extension == NULL || extension == descriptors->name)
    {
        fprintf(stderr, "No exetnsion found, no writing done\n");
        return 1;
    }

    // filename without extension
    int nCharsStem = (extension - descriptors->name);
    strncat(completePath, descriptors->name, nCharsStem);
    strcat(completePath, ".txt");

    FILE* file = fopen(completePath, "w");
    if (file == NULL)
    {
        perror("Cannot write to file");
        return 2;
    }

    fprintf(file, "name:%s\n", descriptors->name);
    fprintf(file, "width:%ld\n", descriptors->width);
    fprintf(file, "height:%ld\n", descriptors->height);
    writeLongArray("greyHistogram", descriptors->greyHistogram, 256, file);
    writeLongArray("redHistogram", descriptors->redHistogram, 256, file);
    writeLongArray("greenHistogram", descriptors->greenHistogram, 256, file);
    writeLongArray("blueHistogram", descriptors->blueHistogram, 256, file);
    fprintf(file, "redRatio:%f\n", descriptors->redRatio);
    fprintf(file, "greenRatio:%f\n", descriptors->greenRatio);
    fprintf(file, "blueRatio:%f\n", descriptors->blueRatio);
    fprintf(file, "averageColor:%d\n", descriptors->averageColor);
    fprintf(file, "gradientNormMean:%f\n", descriptors->gradientNormMean);
    Rect outlinesBox = descriptors->outlinesBox;
    fprintf(file, "outlinesMinX:%ld\n", outlinesBox.minX);
    fprintf(file, "outlinesMaxX:%ld\n", outlinesBox.maxX);
    fprintf(file, "outlinesMinY:%ld\n", outlinesBox.minY);
    fprintf(file, "outlinesMaxY:%ld\n", outlinesBox.maxY);
    Position outlineBaryenter = descriptors->outlinesBarycenter;
    fprintf(file, "outlinesBarycenterX:%ld\n", outlineBaryenter.x);
    fprintf(file, "outlinesBarycenterY:%ld\n", outlineBaryenter.y);
    fprintf(file, "nbOutlinePixels:%ld\n", descriptors->nbOutlines);
    fprintf(file, "isRGB:%d\n", descriptors->isRGB);

    int resp = fclose(file);
    if (resp != 0)
    {
        perror("Error when closing file");
        return 3;
    }

    return 0;
}
