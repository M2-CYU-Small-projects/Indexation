package fr.m2_cyu_indexation.engine.persistence.oracle;

import fr.m2_cyu_indexation.engine.business.request.most_color.DominantColorType;
import fr.m2_cyu_indexation.engine.business.request.most_color.RecessiveColorType;
import fr.m2_cyu_indexation.engine.business.response.ImageResponse;
import fr.m2_cyu_indexation.engine.dao.ImageDao;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A dao implementation using Jdbc on Oracle database.
 *
 * @author Aldric Vitali Silvestre
 */
public class OracleImageDao implements ImageDao {

    private final OracleConnectionHandler connectionHandler;

    public OracleImageDao(OracleConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }


    @Override
    public List<ImageResponse> findByDominantColor(DominantColorType dominantColorType,
                                                   RecessiveColorType recessiveColorType) {
        String dominantpart = "";
        String recessivepart = "";
        switch (dominantColorType) {
            case RED:
                dominantpart = "redRatio";
                break;
            case BLUE:
                dominantpart = "blueRatio";
                break;
            case GREEN:
                dominantpart = "greenRatio";
                break;
        }

        switch (recessiveColorType) {
            case RED:
                recessivepart = " AND redRatio < 0.25 ";
                break;
            case BLUE:
                recessivepart = " AND blueRatio < 0.25 ";
                break;
            case GREEN:
                recessivepart = " AND greenRatio < 0.25 ";
                break;
        }
        String query = "select imageName, nbOutlinePixel, averageColor from imageTable where "
                + dominantpart + " > 0.4 "
                + recessivepart
                + " ORDER BY " + dominantpart + " DESC";
        List<ImageResponse> responseList = new ArrayList<>();

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                ImageResponse resp = new ImageResponse(resultSet.getString(1),
                                                       resultSet.getInt(2),
                                                       resultSet.getInt(3)
                );
                responseList.add(resp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }

        return responseList;
    }

    @Override
    public List<ImageResponse> findGreyscaleImages() {
        String query = "select imageName, nbOutlinePixel, averageColor from imageTable where isRGB = 0";
        List<ImageResponse> responseList = new ArrayList<>();

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                ImageResponse resp = new ImageResponse(resultSet.getString(1),
                                                       resultSet.getInt(2),
                                                       resultSet.getInt(3)
                );
                responseList.add(resp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }

        return responseList;

    }

    @Override
    public List<ImageResponse> findSimilarImages(String imageName) {

        String query = "select imageName, nbOutlinePixel, averageColor, distanceimagemetadatas('" + imageName + "', imagename)" +
                "from imageTable where distanceimagemetadatas('" + imageName + "', imagename) < 50" +
                "order by distanceimagemetadatas('" + imageName + "', imagename)";
        List<ImageResponse> responseList = new ArrayList<>();

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {

            while (resultSet.next()) {
                ImageResponse resp = new ImageResponse(resultSet.getString(1),
                                                       resultSet.getInt(2),
                                                       resultSet.getInt(3)
                );
                responseList.add(resp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }

        return responseList;
    }

    @Override
    public List<ImageResponse> findTexturedImages() {

        String query = "select imageName, nboutlinepixel, averageColor from imageTable " +
                "where nboutlinepixel > (width * height / 5)" +
                "order by nboutlinepixel DESC";
        List<ImageResponse> responseList = new ArrayList<>();

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {

            while (resultSet.next()) {
                ImageResponse resp = new ImageResponse(resultSet.getString(1),
                                                       resultSet.getInt(2),
                                                       resultSet.getInt(3)
                );
                responseList.add(resp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }

        return responseList;
    }

    @Override
    public List<ImageResponse> findImagesWithCenteredInterest() {
        String query = "select imageName, nboutlinepixel, averageColor from imageTable " +
                "WHERE outlinesbarycenterx < width/2 + 10 AND outlinesbarycenterx > width/2 - 10 " +
                "AND outlinesbarycentery < height/2 + 10 AND outlinesbarycentery > height/2 - 10 ";
        List<ImageResponse> responseList = new ArrayList<>();

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {

            while (resultSet.next()) {
                ImageResponse resp = new ImageResponse(resultSet.getString(1),
                                                       resultSet.getInt(2),
                                                       resultSet.getInt(3)
                );
                responseList.add(resp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return List.of();
        }

        return responseList;
    }

    @Override
    public byte[] downloadImageData(String imageName) {
        String query = "select i.image.source.localdata from imagetable i where imagename = +'" + imageName + "'";

        try (
                PreparedStatement statement = connectionHandler.createPreparedStatement(query);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if(!resultSet.next()) {
                System.err.println("No glob file found for image " + imageName);
                return new byte[0];
            }

            Blob blob = resultSet.getBlob(1);
            long length = blob.length();
            byte[] bytes = blob.getBytes(1l, (int) length);
            blob.free();

            return bytes;

        } catch (Exception exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }
}
