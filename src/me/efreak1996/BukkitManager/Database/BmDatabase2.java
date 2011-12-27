package me.efreak1996.BukkitManager.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BmDatabase2 {

	public static void initialize()
	{
		if (!(new File(mainDir + "/source.db").exists()))
		{
			//wget.fetch(false, "http://dl.dropbox.com/u/29754532/Bukkit%28bukkitmanager%29/source.db", "source.db", mainDir + "/");

		}
		initializeConn();
	}
    /*private static boolean sourceTableExists() {
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
    private static void createSourceTable() {
    wget.fetch(false, "http://dl.dropbox.com/u/29754532/Bukkit%28bukkitmanager%29/source.db", "source.db", mainDir + "/");
}*/
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
public static final Logger log = Logger.getLogger("Minecraft");
static String mainDir = "plugins/bukkitmanager";
}
