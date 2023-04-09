package fr.m2_cyu_indexation.engine.business.request.greyscale;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.request.RequestType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class GreyscaleRequest extends Request {

    public GreyscaleRequest() {
        super(RequestType.GREYSCALE);
    }

    @Override
    public List<ImageResponse> submit(ImageDao imageDao) {
        return imageDao.findGreyscaleImages();
    }
}
