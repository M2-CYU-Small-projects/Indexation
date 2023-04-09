package fr.m2_cyu_indexation.index.parser;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * The contents of an index,
 *
 * @author Aldric Vitali Silvestre
 */
public class IndexContent {
    private String imageName;
    private long width;
    private long height;
    private long[] greyHistogram;
    private long[] redHistogram;
    private long[] greenHistogram;
    private long[] blueHistogram;
    private double redRatio;
    private double greenRatio;
    private double blueRatio;
    private int averageColor;
    private double gradientNormMean;
    private long outlinesMinX;
    private long outlinesMaxX;
    private long outlinesMinY;
    private long outlinesMaxY;
    private long outlinesBarycenterX;
    private long outlinesBarycenterY;
    private long nbOutlinePixels;
    private boolean isRGB;

    public IndexContent() {
    }

    public IndexContent(String imageName,
                        long width,
                        long height,
                        long[] greyHistogram,
                        long[] redHistogram,
                        long[] greenHistogram,
                        long[] blueHistogram,
                        double redRatio,
                        double greenRatio,
                        double blueRatio,
                        int averageColor,
                        double gradientNormMean,
                        long outlinesMinX,
                        long outlinesMaxX,
                        long outlinesMinY,
                        long outlinesMaxY,
                        long outlinesBarycenterX,
                        long outlinesBarycenterY,
                        long nbOutlinePixels,
                        boolean isRGB) {
        this.imageName = imageName;
        this.width = width;
        this.height = height;
        this.greyHistogram = greyHistogram;
        this.redHistogram = redHistogram;
        this.greenHistogram = greenHistogram;
        this.blueHistogram = blueHistogram;
        this.redRatio = redRatio;
        this.greenRatio = greenRatio;
        this.blueRatio = blueRatio;
        this.averageColor = averageColor;
        this.gradientNormMean = gradientNormMean;
        this.outlinesMinX = outlinesMinX;
        this.outlinesMaxX = outlinesMaxX;
        this.outlinesMinY = outlinesMinY;
        this.outlinesMaxY = outlinesMaxY;
        this.outlinesBarycenterX = outlinesBarycenterX;
        this.outlinesBarycenterY = outlinesBarycenterY;
        this.nbOutlinePixels = nbOutlinePixels;
        this.isRGB = isRGB;
    }

    public static IndexContent fromMap(Map<String, String> map) {
        IndexContent indexContent = new IndexContent();

        indexContent.setAverageColor(parseInt(mapGet(map, "averageColor")));

        indexContent.setRedHistogram(parseArray(mapGet(map, "redHistogram")));
        indexContent.setGreenHistogram(parseArray(mapGet(map, "greenHistogram")));
        indexContent.setBlueHistogram(parseArray(mapGet(map, "blueHistogram")));
        indexContent.setGreyHistogram(parseArray(mapGet(map, "greyHistogram")));

        indexContent.setHeight(parseLong(mapGet(map, "height")));
        indexContent.setWidth(parseLong(mapGet(map, "width")));

        indexContent.setRedRatio(parseDouble(mapGet(map, "redRatio")));
        indexContent.setGreenRatio(parseDouble(mapGet(map, "greenRatio")));
        indexContent.setBlueRatio(parseDouble(mapGet(map, "blueRatio")));

        indexContent.setGradientNormMean(parseDouble(mapGet(map, "gradientNormMean")));

        indexContent.setOutlinesBarycenterX(parseLong(mapGet(map, "outlinesBarycenterX")));
        indexContent.setOutlinesBarycenterY(parseLong(mapGet(map, "outlinesBarycenterY")));

        indexContent.setOutlinesMinX(parseLong(mapGet(map, "outlinesMinX")));
        indexContent.setOutlinesMaxX(parseLong(mapGet(map, "outlinesMaxX")));
        indexContent.setOutlinesMinY(parseLong(mapGet(map, "outlinesMinY")));
        indexContent.setOutlinesMaxY(parseLong(mapGet(map, "outlinesMaxY")));

        // We have a jpg file in the content folder
        String name = mapGet(map, "name");
        String split[] = name.split("\\.");
        indexContent.setImageName(split[0] + ".jpg");

        indexContent.setNbOutlinePixels(parseLong(mapGet(map, "nbOutlinePixels")));

        indexContent.setRGB(parseBoolean(mapGet(map, "isRGB")));

        return indexContent;
    }

    private static String mapGet(Map<String, String> map, String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Key not found in index : " + key);
        }
        return map.get(key);
    }

    private static int parseInt(String numStr) {
        return Integer.parseInt(numStr);
    }

    private static long parseLong(String numStr) {
        return Long.parseLong(numStr);
    }

    private static double parseDouble(String doubleStr) {
        return Double.parseDouble(doubleStr);
    }

    private static boolean parseBoolean(String boolStr) {
        return "1".equals(boolStr);
    }

    private static long[] parseArray(String arrayStr) {
        long[] array = Arrays.stream(arrayStr.split(","))
                .mapToLong(Long::valueOf)
                .toArray();
        if (array.length != 256) {
            throw new IllegalArgumentException("Array length must be 256, not " + array.length);
        }
        return array;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long[] getGreyHistogram() {
        return greyHistogram;
    }

    public void setGreyHistogram(long[] greyHistogram) {
        this.greyHistogram = greyHistogram;
    }

    public long[] getRedHistogram() {
        return redHistogram;
    }

    public void setRedHistogram(long[] redHistogram) {
        this.redHistogram = redHistogram;
    }

    public long[] getGreenHistogram() {
        return greenHistogram;
    }

    public void setGreenHistogram(long[] greenHistogram) {
        this.greenHistogram = greenHistogram;
    }

    public long[] getBlueHistogram() {
        return blueHistogram;
    }

    public void setBlueHistogram(long[] blueHistogram) {
        this.blueHistogram = blueHistogram;
    }

    public double getRedRatio() {
        return redRatio;
    }

    public void setRedRatio(double redRatio) {
        this.redRatio = redRatio;
    }

    public double getGreenRatio() {
        return greenRatio;
    }

    public void setGreenRatio(double greenRatio) {
        this.greenRatio = greenRatio;
    }

    public double getBlueRatio() {
        return blueRatio;
    }

    public void setBlueRatio(double blueRatio) {
        this.blueRatio = blueRatio;
    }

    public int getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(int averageColor) {
        this.averageColor = averageColor;
    }

    public double getGradientNormMean() {
        return gradientNormMean;
    }

    public void setGradientNormMean(double gradientNormMean) {
        this.gradientNormMean = gradientNormMean;
    }

    public long getOutlinesMinX() {
        return outlinesMinX;
    }

    public void setOutlinesMinX(long outlinesMinX) {
        this.outlinesMinX = outlinesMinX;
    }

    public long getOutlinesMaxX() {
        return outlinesMaxX;
    }

    public void setOutlinesMaxX(long outlinesMaxX) {
        this.outlinesMaxX = outlinesMaxX;
    }

    public long getOutlinesMinY() {
        return outlinesMinY;
    }

    public void setOutlinesMinY(long outlinesMinY) {
        this.outlinesMinY = outlinesMinY;
    }

    public long getOutlinesMaxY() {
        return outlinesMaxY;
    }

    public void setOutlinesMaxY(long outlinesMaxY) {
        this.outlinesMaxY = outlinesMaxY;
    }

    public long getOutlinesBarycenterX() {
        return outlinesBarycenterX;
    }

    public void setOutlinesBarycenterX(long outlinesBarycenterX) {
        this.outlinesBarycenterX = outlinesBarycenterX;
    }

    public long getOutlinesBarycenterY() {
        return outlinesBarycenterY;
    }

    public void setOutlinesBarycenterY(long outlinesBarycenterY) {
        this.outlinesBarycenterY = outlinesBarycenterY;
    }

    public long getNbOutlinePixels() {
        return nbOutlinePixels;
    }

    public void setNbOutlinePixels(long nbOutlinePixels) {
        this.nbOutlinePixels = nbOutlinePixels;
    }

    public boolean isRGB() {
        return isRGB;
    }

    public void setRGB(boolean RGB) {
        isRGB = RGB;
    }

    @Override
    public String toString() {
        return "IndexContent{" +
                "imageName='" + imageName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", greyHistogram=" + Arrays.toString(greyHistogram) +
                ", redHistogram=" + Arrays.toString(redHistogram) +
                ", greenHistogram=" + Arrays.toString(greenHistogram) +
                ", blueHistogram=" + Arrays.toString(blueHistogram) +
                ", redRatio=" + redRatio +
                ", greenRatio=" + greenRatio +
                ", blueRatio=" + blueRatio +
                ", averageColor=" + averageColor +
                ", gradientNormMean=" + gradientNormMean +
                ", outlinesMinX=" + outlinesMinX +
                ", outlinesMaxX=" + outlinesMaxX +
                ", outlinesMinY=" + outlinesMinY +
                ", outlinesMaxY=" + outlinesMaxY +
                ", outlinesBarycenterX=" + outlinesBarycenterX +
                ", outlinesBarycenterY=" + outlinesBarycenterY +
                ", nbOutlinePixels=" + nbOutlinePixels +
                ", isRGB=" + isRGB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IndexContent that = (IndexContent) o;
        return width == that.width && height == that.height && Double.compare(that.redRatio,
                                                                              redRatio
        ) == 0 && Double.compare(that.greenRatio, greenRatio) == 0 && Double.compare(that.blueRatio,
                                                                                     blueRatio
        ) == 0 && averageColor == that.averageColor && Double.compare(that.gradientNormMean,
                                                                      gradientNormMean
        ) == 0 && outlinesMinX == that.outlinesMinX && outlinesMaxX == that.outlinesMaxX && outlinesMinY == that.outlinesMinY && outlinesMaxY == that.outlinesMaxY && outlinesBarycenterX == that.outlinesBarycenterX && outlinesBarycenterY == that.outlinesBarycenterY && nbOutlinePixels == that.nbOutlinePixels && isRGB == that.isRGB && Objects.equals(
                imageName,
                that.imageName
        ) && Arrays.equals(greyHistogram, that.greyHistogram) && Arrays.equals(redHistogram,
                                                                               that.redHistogram
        ) && Arrays.equals(greenHistogram, that.greenHistogram) && Arrays.equals(blueHistogram,
                                                                                 that.blueHistogram
        );
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(imageName,
                                  width,
                                  height,
                                  redRatio,
                                  greenRatio,
                                  blueRatio,
                                  averageColor,
                                  gradientNormMean,
                                  outlinesMinX,
                                  outlinesMaxX,
                                  outlinesMinY,
                                  outlinesMaxY,
                                  outlinesBarycenterX,
                                  outlinesBarycenterY,
                                  nbOutlinePixels,
                                  isRGB
        );
        result = 31 * result + Arrays.hashCode(greyHistogram);
        result = 31 * result + Arrays.hashCode(redHistogram);
        result = 31 * result + Arrays.hashCode(greenHistogram);
        result = 31 * result + Arrays.hashCode(blueHistogram);
        return result;
    }

}
