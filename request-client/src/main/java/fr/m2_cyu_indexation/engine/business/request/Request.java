package fr.m2_cyu_indexation.engine.business.request;

import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public abstract class Request {

    private final RequestType requestType;

    protected Request(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public abstract List<ImageResponse> submit(ImageDao imageDao);
}
