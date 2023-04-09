package fr.m2_cyu_indexation;

import fr.m2_cyu_indexation.config.Arguments;
import fr.m2_cyu_indexation.config.Config;
import fr.m2_cyu_indexation.index.indexer.ExternProgramIndexer;
import fr.m2_cyu_indexation.index.indexer.Indexer;
import fr.m2_cyu_indexation.index.parser.IndexParser;
import fr.m2_cyu_indexation.injector.Injector;
import fr.m2_cyu_indexation.saver.IndexSaver;
import fr.m2_cyu_indexation.saver.oracle.OracleConnection;
import fr.m2_cyu_indexation.saver.oracle.OracleIndexSaver;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        Arguments arguments = new Arguments(args);

        try (OracleConnection oracleConnection = OracleConnection.fromConfig(config.getOracleConfig())){

            Indexer indexer = new ExternProgramIndexer(config.getIndexerPath());
            IndexParser indexParser = new IndexParser();

            IndexSaver saver = new OracleIndexSaver(oracleConnection);
            Injector injector = new Injector(indexer, indexParser, saver);

            injector.inject(arguments.getInputPath(), arguments.getOutputFolderPath(), arguments.isDoUploadImage());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}