#include "file_stat.h"

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

int isDir(char* path)
{
    struct stat fileStats;
    if(stat(path, &fileStats) != 0)
    {
        perror("Cannot see stats of file");
        exit(1);
    }

    return S_ISDIR(fileStats.st_mode);
}

int isFile(char* path)
{
    struct stat fileStats;
    if(stat(path, &fileStats) != 0)
    {
        perror("Cannot see stats of file");
        exit(1);
    }
    return S_ISREG(fileStats.st_mode);
}

char* findBasename(char* path)
{
    char* ptr = strrchr(path, '/');
    if (ptr == NULL)
    {
        // the basename is the entire path
        return path;
    }
    // If the path ends with a backslash without filename after.
    if (ptr[1] == '\0')
    {
        return NULL;
    }
    return ptr + 1;
}

char* findExtension(char* path)
{
    return strrchr(path, '.');
}
