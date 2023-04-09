package fr.m2_cyu_indexation.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * On creation, this class will search for the properties written in
 * order to be able to easily retrieve them.
 *
 * @author Aldric Vitali Silvestre
 */
public class Config {
    public static final String DEFAULT_PROPERTIES_FILENAME = "application.properties";
    private final OracleConfig oracleConfig;

    public Config() {
        this(DEFAULT_PROPERTIES_FILENAME);
    }

    public Config(String propertiesFilename) {
        Configuration configuration = loadConfiguration(propertiesFilename);
        oracleConfig = loadOracleConfig(configuration);
    }

    public OracleConfig getOracleConfig() {
        return oracleConfig;
    }

    private Configuration loadConfiguration(String propertiesFilename) {
        Configuration configuration;
        try {
            Configurations configurations = new Configurations();
            configuration = configurations.properties(new File(propertiesFilename));
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
