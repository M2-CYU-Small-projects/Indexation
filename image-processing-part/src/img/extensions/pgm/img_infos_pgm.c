#include "img_infos_pgm.h"

#include <memory.h>
#include <stdio.h>
#include <stdlib.h>

#include "NRC/nrc.h"
#include "img/model/ImageInfos.h"
#include "img/process/img_processes.h"

void loadPGM(char* imagePath, byte*** image, ImageInfos *infos)
{
    *image = LoadPGM_bmatrix(
        imagePath, 
        &infos->nrl,
        &infos->nrh,
        &infos->ncl, 
        &infos->nch
    );
}

int fillDescriptors_PGM(char* imagePath, ImgDescriptors* desc)
{
    // Image
    byte** image;
    ImageInfos infos;
    loadPGM(imagePath, &image, &infos);
    desc->width = infos.nch - infos.ncl;
    desc->height = infos.nrh - infos.nrl;
    desc->isRGB = 0;
    fillHistogram(image, &infos, desc->greyHistogram);
    fillHistogramZeroes(desc->redHistogram);
    fillHistogramZeroes(desc->greenHistogram);
    fillHistogramZeroes(desc->blueHistogram);
    desc->averageColor = averageColorGreyscale(image, &infos, &desc->redRatio, &desc->greenRatio, &desc->blueRatio);
    gradientNormRelated(
        image, 
        &infos, 
        &desc->gradientNormMean,
        &desc->outlinesBox,
        &desc->outlinesBarycenter,
        &desc->nbOutlines
    );
    freeGreyscale(image, &infos);
    return 0;
}
