package fr.m2_cyu_indexation.saver;

import fr.m2_cyu_indexation.index.parser.IndexContent;

import java.nio.file.Path;

/**
 * Responsible for saving data to the wanted source.
 *
 * @author Aldric Vitali Silvestre
 */
public interface IndexSaver {

    void save(IndexContent indexContent, boolean doUploadImage);

}
