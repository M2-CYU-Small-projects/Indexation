package fr.m2_cyu_indexation.index.indexer;

import java.nio.file.Path;

/**
 * A functional interface that can create indexes of files in input folder,
 * creating files in output folder.
 *
 * @author Aldric Vitali Silvestre
 */
@FunctionalInterface
public interface Indexer {
    void createIndexes(Path inputPath, Path outputFolderPath);
}
