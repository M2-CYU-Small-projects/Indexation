package fr.m2_cyu_indexation.engine;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public interface Engine {

    void submitRequest(Request request);

    List<ImageResponse> getResponses();

    byte[] downloadImageData(String imageName);
}
