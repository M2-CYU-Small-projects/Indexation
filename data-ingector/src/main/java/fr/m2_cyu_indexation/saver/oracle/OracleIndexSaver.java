package fr.m2_cyu_indexation.saver.oracle;

import fr.m2_cyu_indexation.index.parser.IndexContent;
import fr.m2_cyu_indexation.saver.IndexSaver;
import oracle.jdbc.OracleTypeMetaData;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Aldric Vitali Silvestre
 */
public class OracleIndexSaver implements IndexSaver {

    private final OracleConnection oracleConnection;

    public OracleIndexSaver(OracleConnection connection) {
        this.oracleConnection = connection;
    }

    @Override
    public void save(IndexContent indexContent, boolean doUploadImage) {

        System.out.println("Process " + indexContent.getImageName() + " : " + doUploadImage);

        String query = createQuery(indexContent, doUploadImage);

        try (
                PreparedStatement statement = setVariables(oracleConnection.createPreparedStatement(query), indexContent);
        ) {
           statement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error while handling statement", exception);
        }
    }

    private String createQuery(IndexContent indexContent, boolean doUploadImage) {
        String questionMarks = IntStream.range(0, 20)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(","));
        return "CALL InsertImageMetaDatas(" + questionMarks + ")";
    }

    private PreparedStatement setVariables(PreparedStatement statement, IndexContent content) throws SQLException {
        statement.setString(1, content.getImageName());
        statement.setInt(2, (int) content.getWidth());
        statement.setInt(3, (int) content.getHeight());
        statement.setArray(4, longArrayToSqlArray(content.getGreyHistogram()));
        statement.setArray(5, longArrayToSqlArray(content.getRedHistogram()));
        statement.setArray(6, longArrayToSqlArray(content.getGreenHistogram()));
        statement.setArray(7, longArrayToSqlArray(content.getBlueHistogram()));
        statement.setDouble(8, content.getRedRatio());
        statement.setDouble(9, content.getGreenRatio());
        statement.setDouble(10, content.getBlueRatio());
        statement.setInt(11, content.getAverageColor());
        statement.setDouble(12, content.getGradientNormMean());
        statement.setInt(13, (int) content.getOutlinesMinX());
        statement.setInt(14, (int) content.getOutlinesMaxX());
        statement.setInt(15, (int) content.getOutlinesMinY());
        statement.setInt(16, (int) content.getOutlinesMaxY());
        statement.setInt(17, (int) content.getOutlinesBarycenterX());
        statement.setInt(18, (int) content.getOutlinesBarycenterY());
        statement.setInt(19, (int) content.getNbOutlinePixels());
        statement.setInt(20, content.isRGB() ? 1 : 0);
        return statement;
    }

    private Array longArrayToSqlArray(long[] array) throws SQLException {
        Object[] a = Arrays.stream(array)
                .mapToInt(l -> (int) l)
                .boxed()
                .toArray();

        return oracleConnection.createVarray(a);
    }
}
