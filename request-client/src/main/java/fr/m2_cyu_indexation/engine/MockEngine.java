package fr.m2_cyu_indexation.engine;

import fr.m2_cyu_indexation.engine.business.request.Request;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class MockEngine implements Engine {
    @Override
    public void submitRequest(Request request) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ImageResponse> getResponses() {
        return List.of(
                new ImageResponse("Salut", 111111, 12345),
                new ImageResponse("Salut2", 111111, 34323),
                new ImageResponse("Salut3", 111111, 23)
        );
    }

    @Override
    public byte[] downloadImageData(String imageName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new byte[0];
    }
}
