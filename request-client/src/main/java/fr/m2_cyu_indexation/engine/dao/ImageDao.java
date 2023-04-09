package fr.m2_cyu_indexation.engine.dao;

import fr.m2_cyu_indexation.engine.business.request.most_color.DominantColorType;
import fr.m2_cyu_indexation.engine.business.request.most_color.RecessiveColorType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;

import java.util.List;

/**
 * Responsible for accessing wanted data from various requests
 *
 * @author Aldric Vitali Silvestre
 */
public interface ImageDao {

    // TODO
    List<ImageResponse> findByDominantColor(DominantColorType dominantColorType, RecessiveColorType recessiveColorType);

    List<ImageResponse> findGreyscaleImages();

    List<ImageResponse> findSimilarImages(String imageName);

    List<ImageResponse> findTexturedImages();

    List<ImageResponse> findImagesWithCenteredInterest();

    byte[] downloadImageData(String imageName);
}
