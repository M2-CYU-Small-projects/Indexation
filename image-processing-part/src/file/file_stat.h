#ifndef __FILE_STAT_H__
#define __FILE_STAT_H__

/**
 * Checks if a given path is a dir.
 */
int isDir(char* path);

/**
 * Checks if a given path is a file.
 */
int isFile(char* path);

/**
 * Find the basename of the file given by a path.
 * If no filename could be found, NULL will be returned.
 * The function returns a pointer to a character in the string. So if this string is deallocated,
 * the pointer returned will be invalid.
 */
char* findBasename(char* path);

/**
 * Find the extenion of the file given, or NULL if no extension is found.
 * The function returns a pointer to a character in the string. So if this string is deallocated,
 * the pointer returned will be invalid.
 */
char* findExtension(char* path);

#endif // __FILE_STAT_H__