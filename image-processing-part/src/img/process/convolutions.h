#ifndef __CONVOLUTIONS_H__
#define __CONVOLUTIONS_H__

#include "NRC/nrc.h"
#include "img/model/ImageInfos.h"

// ==== GLOBAL PURPOSE FUNCTIONS ====
void rgb8MatrixToByteMatrixes(rgb8** matrix, ImageInfos imageInfos, byte*** rMatrix, byte*** gMatrix, byte*** bMatrix);

long doOneConv(byte** matrix, ImageInfos imageInfos, long h, long w, int** filter, long size);
byte** convoluteMatrix(byte** matrix, ImageInfos imageInfos, int** filter, long size, byte divideBy);

// ==== SPECIAL OPERATIONS ====
byte** horizontalGradient3x3(byte** matrix, ImageInfos imageInfos);
byte** verticalGradient3x3(byte** matrix, ImageInfos imageInfos);
byte** gradientNorm3x3(byte** matrix, ImageInfos imageInfos);
byte** contourMatrix3x3(byte** matrix, ImageInfos imageInfos, byte threshold);
byte** contourMatrix3x3FromGradientNorm(byte** gradientNorm, ImageInfos imageInfos, byte threshold);

byte** contourRgb3x3(rgb8** matrix, ImageInfos imageInfos, byte redThreshold, byte greenThreshold, byte blueThreshold, byte sh, byte sb);

#endif // __CONVOLUTIONS_H__