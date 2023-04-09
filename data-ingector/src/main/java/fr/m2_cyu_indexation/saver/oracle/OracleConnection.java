package fr.m2_cyu_indexation.saver.oracle;

import fr.m2_cyu_indexation.config.OracleConfig;
import oracle.jdbc.OracleDriver;

import java.sql.*;

/**
 * The JDBC connection to the oracle database
 *
 * @author Aldric Vitali Silvestre
 */
public class OracleConnection implements AutoCloseable {

    oracle.jdbc.OracleConnection connection;

    public OracleConnection(String username, String password, String ip, int port, String sid) throws SQLException {
        System.out.println("Create connection to oracledb");
        connection = (oracle.jdbc.OracleConnection) DriverManager.getConnection(
            createUrl(ip, port, sid),
            username,
            password
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement createPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    private String createUrl(String ip, int port, String sid) {
        return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
    }

    public static OracleConnection fromConfig(OracleConfig config) throws SQLException {
        return new OracleConnection(
                config.getUsername(),
                config.getPassword(),
                config.getIpAddress(),
                config.getPort(),
                config.getSid()
        );
    }

    @Override
    public void close() throws Exception {
        System.out.println("Close oracledb connection");
        connection.close();
    }

    public Array createVarray(Object[] a) throws SQLException {
        // Oracle is a little special with that
        return connection.createOracleArray("HISTOGRAM", a);
    }
}
