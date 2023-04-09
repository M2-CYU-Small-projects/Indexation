package fr.m2_cyu_indexation.injector;

import fr.m2_cyu_indexation.index.indexer.Indexer;
import fr.m2_cyu_indexation.index.parser.IndexContent;
import fr.m2_cyu_indexation.index.parser.IndexParser;
import fr.m2_cyu_indexation.saver.IndexSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The facade of the program. Index all files of folder and persist the data.
 *
 * @author Aldric Vitali Silvestre
 */
public class Injector {

    private final Indexer indexer;

    private final IndexParser indexParser;
    private final IndexSaver saver;

    public Injector(Indexer indexer, IndexParser indexParser, IndexSaver saver) {
        this.indexer = indexer;
        this.indexParser = indexParser;
        this.saver = saver;
    }

    public void inject(Path inputPath, Path outputFolderPath, boolean doUploadImage) {
        System.out.println("Start indexing files");

        // In case the output folder doesn't exist
        File outputFolder = outputFolderPath.toFile();
        if (!outputFolder.exists()) {
            System.out.println("Create output folder");
            outputFolder.mkdirs();
        }

        indexer.createIndexes(inputPath, outputFolderPath);

        // For all indexed files, parse the content and save it
        try (Stream<Path> pathStream = Files.walk(outputFolderPath, FileVisitOption.FOLLOW_LINKS)) {
            pathStream.filter(p -> p.toFile().isFile())
                    .map(indexParser::parse)
                    .forEach(save(doUploadImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Consumer<IndexContent> save(boolean doUploadImage) {
        return c -> saver.save(c, doUploadImage);
    }

}
