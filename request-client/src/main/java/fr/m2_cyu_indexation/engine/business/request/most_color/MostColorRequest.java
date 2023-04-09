package fr.m2_cyu_indexation.engine.business.request.most_color;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.request.RequestType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class MostColorRequest extends Request {

    private final DominantColorType dominantColorType;
    private final RecessiveColorType recessiveColorType;

    public MostColorRequest(DominantColorType dominantColorType, RecessiveColorType recessiveColorType) {
        super(RequestType.MOST_COLOR);
        this.dominantColorType = dominantColorType;
        this.recessiveColorType = recessiveColorType;
    }

    @Override
    public List<ImageResponse> submit(ImageDao imageDao) {
        return imageDao.findByDominantColor(dominantColorType, recessiveColorType);
    }
}
