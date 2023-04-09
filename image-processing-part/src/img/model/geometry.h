#ifndef __GEOMETRY_H__
#define __GEOMETRY_H__

typedef struct position_struct
{
    long x;
    long y;
} Position;

typedef struct rect_struct
{
    long minX;
    long maxX;
    long minY;
    long maxY;
} Rect;

#endif // __GEOMETRY_H__