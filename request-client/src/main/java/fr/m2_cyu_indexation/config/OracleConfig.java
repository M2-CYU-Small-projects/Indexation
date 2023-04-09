package fr.m2_cyu_indexation.config;

/**
 * Contains all credentials for the oracle database.
 *
 * @author Aldric Vitali Silvestre
 */
public class OracleConfig {
    private final String username;
    private final String password;
    private final String ipAddress;
    private final int port;
    private final String sid;

    public OracleConfig(String username, String password, String ipAddress, int port, String orcl) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
        this.port = port;
        this.sid = orcl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getSid() {
        return sid;
    }
}