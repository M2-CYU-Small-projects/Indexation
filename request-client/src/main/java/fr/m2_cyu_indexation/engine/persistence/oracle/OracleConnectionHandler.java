package fr.m2_cyu_indexation.engine.persistence.oracle;

import fr.m2_cyu_indexation.config.OracleConfig;
import oracle.jdbc.OracleConnection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The holder of the connection to the oracle database.
 *
 * @apiNote The class is intended to be used in a try-with-resources block in order to have the
 * connection to automatically close whatever happens.
 *
 * @author Aldric Vitali Silvestre
 */
public class OracleConnectionHandler implements AutoCloseable {

    private final String username;
    private final String password;
    private final String ip;
    private final int port;
    private final String sid;

    private OracleConnection connection;

    public OracleConnectionHandler(String username, String password, String ip, int port, String sid) throws SQLException {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.sid = sid;
        // Oracle driver implementation always returns a OracleConnection instance,
        // but no way to avoid the cast here
        connection = createConnection(this.username, this.password, this.ip, this.port, this.sid);
    }

    private OracleConnection createConnection(String username, String password, String ip, int port, String sid)
            throws SQLException {
        System.out.println("Create connection to oracledb");
        return (OracleConnection) DriverManager.getConnection(
                createUrl(ip, port, sid),
                username,
                password
        );
    }

    public void updateConnection() throws SQLException {
        if (connection.isClosed()) {
            connection = createConnection(username, password, ip, port, sid);
        }
    }

    public Statement createStatement() throws SQLException {
        updateConnection();
        return connection.createStatement();
    }

    public PreparedStatement createPreparedStatement(String query) throws SQLException {
        updateConnection();
        return connection.prepareStatement(query);
    }

    public static OracleConnectionHandler fromConfig(OracleConfig config) throws SQLException {
        return new OracleConnectionHandler(
                config.getUsername(),
                config.getPassword(),
                config.getIpAddress(),
                config.getPort(),
                config.getSid()
        );
    }

    private String createUrl(String ip, int port, String sid) {
        return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Close oracledb connection");
        if (!connection.isClosed()){
            connection.close();
        }
    }
}
