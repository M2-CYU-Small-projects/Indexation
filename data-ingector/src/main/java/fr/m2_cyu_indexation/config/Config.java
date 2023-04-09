package fr.m2_cyu_indexation.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.nio.file.Path;

/**
 * This automatically loads all properties stored in application.properties
 *
 * @author Aldric Vitali Silvestre
 */
public class Config {
    public static final String PROPERTIES_FILENAME = "application.properties";
    private final Path indexerPath;

    private final OracleConfig oracleConfig;

    public Config() {
        Configuration configuration = loadConfiguration();
        indexerPath = loadIndexerPath(configuration);
        oracleConfig = loadOracleConfig(configuration);
    }

    public Path getIndexerPath() {
        return indexerPath;
    }

    public OracleConfig getOracleConfig() {
        return oracleConfig;
    }

    private Path loadIndexerPath(Configuration configuration) {
        Path path = Path.of(configuration.getString("indexer.path"));
        if (!path.toFile().exists()) {
            throw new IllegalArgumentException("The indexer path is not a file : " + indexerPath.toString());
        }
        return path;
    }

    private Configuration loadConfiguration() {
        Configuration configuration;
        try {
            Configurations configurations = new Configurations();
            configuration = configurations.properties(new File(PROPERTIES_FILENAME));
        } catch (ConfigurationException cex) {
            throw new IllegalArgumentException("Error when loading configuration : " + cex.getMessage());
        }
        return configuration;
    }

    private OracleConfig loadOracleConfig(Configuration configuration) {
        Configuration p = configuration.subset("oracledb");
        return new OracleConfig(
                p.getString("username"),
                p.getString("password"),
                p.getString("ip"),
                p.getInt("port"),
                p.getString("sid")
        );
    }
}
