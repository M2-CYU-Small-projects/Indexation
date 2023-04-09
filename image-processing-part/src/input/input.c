#include "input.h"

int retrieveInput(int argc, char* argv[], Input* input)
{
    if (argc < 3)
    {
        // Unsufficient number of arguments
        return 1;
    }

    input->inputPath = argv[1];
    input->outputPath = argv[2];

    return 0;
}