package fr.m2_cyu_indexation.saver;

import fr.m2_cyu_indexation.index.parser.IndexContent;

import java.nio.file.Path;

/**
 * @author Aldric Vitali Silvestre
 */
public class NoOpIndexSaver implements IndexSaver {

    @Override
    public void save(IndexContent indexContent, boolean doUploadImage) {
        System.out.println("Process " + indexContent.getImageName());
        System.out.println(doUploadImage + " ==> " + indexContent);
    }

}
