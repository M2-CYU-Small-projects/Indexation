package fr.m2_cyu_indexation.index.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Read from a file an image's index content.
 *
 * @author Aldric Vitali Silvestre
 */
public class IndexParser {

    public IndexContent parse(Path inputFilePath) {
        System.out.println("Parse " + inputFilePath);
        Map<String, String> map = createMapping(inputFilePath);
        return IndexContent.fromMap(map);
    }

    private Map<String, String> createMapping(Path inputFilePath) {
        try (Stream<String> lines = Files.lines(inputFilePath)) {
            return lines.filter(s -> !s.isBlank())
                    .filter(s -> s.contains(":"))
                    .map(s -> s.split(":"))
                    .filter(spl -> spl.length == 2)
                    .collect(Collectors.toMap(
                            spl -> spl[0],
                            spl -> spl[1]
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
