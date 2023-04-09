package fr.m2_cyu_indexation.engine;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class RequestEngine implements Engine {

    private final ImageDao imageDao;

    private List<ImageResponse> responses;

    public RequestEngine(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public void submitRequest(Request request) {
        System.out.println("Submit request " + request.getRequestType().name());
        responses = request.submit(imageDao);
    }

    @Override
    public List<ImageResponse> getResponses() {
        return responses;
    }

    @Override
    public byte[] downloadImageData(String imageName) {
        return imageDao.downloadImageData(imageName);
    }
}
