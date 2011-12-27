package me.efreak1996.BukkitManager.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BmDatabase1 {

	public static void initialize()
	{
		initializeConn();
		if (!pluginsTableExists()) {
            createPluginsTable();
        }
        if (!sourceTableExists()) {
            createSourceTable();
        }
	}
	private static boolean pluginsTableExists() {
    	ResultSet rs = null;
        try {
            Connection conn = getConnection();

            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "plugins", null);
            if (!rs.next())
                return false;
            return true;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "[BukkitManager]: Table Check Exception", ex);
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "[BukkitManager]: Table Check SQL Exception (on closing)");
            }
        }
}
    private static boolean sourceTableExists() {
	ResultSet rs = null;
    try {
        Connection conn = getConnection();

        DatabaseMetaData dbm = conn.getMetaData();
        rs = dbm.getTables(null, null, "source", null);
        if (!rs.next())
            return false;
        return true;
    } catch (SQLException ex) {
        log.log(Level.SEVERE, "[BukkitManager]: Table Check Exception", ex);
        return false;
    } finally {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "[BukkitManager]: Table Check SQL Exception (on closing)");
        }
    }
}
    private static void createPluginsTable() {
    Statement st = null;
    try {
        Connection conn = getConnection();
        st = conn.createStatement();
        st.executeUpdate(PLUGINS_TABLE);
        conn.commit();
    } catch (SQLException e) {
        log.log(Level.SEVERE, "[BukkitManager]: Create Table Exception", e);
    } finally {
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "[BukkitManager]: Could not create the table (on close)");
        }
    }
}
    private static void createSourceTable() {
    Statement st = null;
    try {
        Connection conn = getConnection();
        st = conn.createStatement();
        st.executeUpdate(SOURCE_TABLE);
        conn.commit();
    } catch (SQLException e) {
        log.log(Level.SEVERE, "[BukkitManager]: Create Table Exception", e);
    } finally {
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "[BukkitManager]: Could not create the table (on close)");
        }
    }
}
    public static Connection initializeConn() {
    try {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + mainDir + "/plugins.db");
        conn.setAutoCommit(false);
        return conn;
    } catch (SQLException ex) {
    	log.log(Level.SEVERE, "SQLite exception on initialize", ex);
    } catch (ClassNotFoundException ex) {
    	log.log(Level.SEVERE, "You need the SQLite library.", ex);
    }
    return conn;
}
    public static Connection getConnection() {
    return conn;
}
    public static void closeConnection() {
    if(conn != null) {
        try {
            conn.close();
        } catch (SQLException ex) {
        	log.log(Level.SEVERE, "Error on Connection close", ex);
        }
    }
}

private static Connection conn;
private final static String PLUGINS_TABLE = "CREATE TABLE `plugins` (`name` STRING,`status` BOOLEAN,`version` STRING);";
private final static String SOURCE_TABLE = "CREATE TABLE `source` (`name` STRING,`url` STRING,`version` STRING);";
public static final Logger log = Logger.getLogger("Minecraft");
static String mainDir = "plugins/bukkitmanager";
}
