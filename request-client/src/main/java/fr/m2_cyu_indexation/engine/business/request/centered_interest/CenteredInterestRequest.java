package fr.m2_cyu_indexation.engine.business.request.centered_interest;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.request.RequestType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class CenteredInterestRequest extends Request {

    public CenteredInterestRequest() {
        super(RequestType.CENTERED_INTEREST);
    }

    @Override
    public List<ImageResponse> submit(ImageDao imageDao) {
        return imageDao.findImagesWithCenteredInterest();
    }
}
