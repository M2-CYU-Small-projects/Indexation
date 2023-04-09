package fr.m2_cyu_indexation.index.indexer;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The main service that will call the C program in order to
 * create indexes of the files targeted as inputs.
 *
 * @author Aldric Vitali Silvestre
 */
public class ExternProgramIndexer implements Indexer {
    private final Path indexerPath;

    public ExternProgramIndexer(Path indexerPath) {
        this.indexerPath = indexerPath;
    }

    /**
     * From the given inputs and outputs, call the C program in order to index all images in the targeted folder.
     * @throws RuntimeException if an error happens during or at the end of the process.
     */
    @Override
    public void createIndexes(Path inputPath, Path outputFolderPath) {
        System.out.println("==== Start C program ====");
        Process process = startProgram(inputPath, outputFolderPath);
        int returnCode = waitProgramEnd(process);
        if (returnCode != 0) {
            throw new RuntimeException("Program ended with error code " + returnCode);
        }
        System.out.println("==== End of C program ====");
    }

    private int waitProgramEnd(Process process) {
        try {
            return process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Process startProgram(Path inputPath, Path outputFolderPath) {
        try {
            return new ProcessBuilder()
                    .command(indexerPath.toString(), inputPath.toString(), outputFolderPath.toString())
                    .inheritIO()
                    .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
