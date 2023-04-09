package fr.m2_cyu_indexation.engine.business.request.similarity;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.request.RequestType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class SimilarityRequest extends Request {
    private final String imageName;

    public SimilarityRequest(String imageName) {
        super(RequestType.SIMILARITY);
        this.imageName = imageName;
    }

    @Override
    public List<ImageResponse> submit(ImageDao imageDao) {
        return imageDao.findSimilarImages(imageName);
    }
}
