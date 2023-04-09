#ifndef __IMG_INFOS_PGM_H__
#define __IMG_INFOS_PGM_H__

#include "img/img_descriptors.h"

/**
 * Fills informations for a pgm image.
 * A PGM image is a greyscaleed one.
 */
int fillDescriptors_PGM(char* imagePath, ImgDescriptors* infos);

#endif // __IMG_INFOS_PGM_H__