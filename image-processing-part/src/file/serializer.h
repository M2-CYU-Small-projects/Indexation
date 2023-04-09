#ifndef __SERIALIZER_H__
#define __SERIALIZER_H__

#include "img/img_descriptors.h"

/**
 * Serialize the descriptor to a new file to the folder.
 * The filename will be the one from the descriptor struct.
 */
int serialize_descriptors(ImgDescriptors* descriptors, char* outputDirectory);

#endif // __SERIALIZER_H__