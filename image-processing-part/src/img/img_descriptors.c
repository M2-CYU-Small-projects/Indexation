#include "img_descriptors.h"

#include <stdio.h>
#include <string.h>

#include "file/file_stat.h"
#include "img/extensions/pgm/img_infos_pgm.h"
#include "img/extensions/ppm/img_infos_ppm.h"

int fillDescriptors(char* imagePath, char* basename, ImgDescriptors* infos)
{

    strcpy(infos->name, basename);
    char* extension = findExtension(imagePath);
    if (extension == NULL)
    {
        fprintf(stderr, "Path does not represent a file, or the file does not have an extension\n");
        return 1;
    }

    if (strcmp(".ppm", extension) == 0)
    {
        return fillDescriptors_PPM(imagePath, infos);
    }
    else if (strcmp(".pgm", extension) == 0)
    {
        return fillDescriptors_PGM(imagePath, infos);
    }
    else
    {
        fprintf(stderr, "The extension \"%s\" is not supported\n", extension);
        return 2;
    }
}

void disposeImgDescriptors(ImgDescriptors* imgDesc)
{
    // Nothing to deallocate.
}