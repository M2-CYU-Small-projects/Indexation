package fr.m2_cyu_indexation.engine.business.response;

/**
 * @author Aldric Vitali Silvestre
 */
public class ImageResponse {

    private byte[] data = {};

    private String imageName;
    private int averageColor;
    private int nbOutlinePixels;

    public ImageResponse(String imageName, int averageColor, int nbOutlinePixels) {
        this.imageName = imageName;
        this.averageColor = averageColor;
        this.nbOutlinePixels = nbOutlinePixels;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getImageName() {
        return imageName;
    }

    public int getAverageColor() {
        return averageColor;
    }

    public int getNbOutlinePixels() {
        return nbOutlinePixels;
    }
}
