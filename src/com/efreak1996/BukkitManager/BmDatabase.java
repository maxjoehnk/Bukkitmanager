package com.efreak1996.BukkitManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

/**
 * 
 * Loads and manages all databases
 * 
 * @author efreak1996
 * 
 * @version Alpha 1.3
 *
 */

public class BmDatabase {
    
	/**
	 * 
	 * Creates and connects to the databases
	 * 
	 */
	
	public static void initialize() {
		connectDatabase();
		if (config.Config.getBoolean("General.Update-Sources-on-start")) {
			addSources();
			addExtSources();
		}
		updatePlugins();
		if (config.Config.getBoolean("Plugins.Updates.on-start")) checkUpdates();
	}
	
	/**
	 * 
	 * Closes the connections
	 * 
	 */
	
	public static void shutdown() {
		try {
			pluginsConn.close();
		} catch (SQLException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Connects to plugins database
	 * 
	 */
	
	private static void connectDatabase() {
		File database = new File(Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder() + File.separator + "plugins.db");
		try {
			Class.forName("org.sqlite.JDBC");
			if (!database.exists()) {
				database.getAbsoluteFile().createNewFile();
				pluginsConn = DriverManager.getConnection("jdbc:sqlite:" + database);
				pluginsStatement = pluginsConn.createStatement();
				pluginsStatement.executeUpdate("CREATE TABLE plugins ('name' varchar(255) PRIMARY KEY NOT NULL, 'version' varchar(255) NOT NULL);");
				pluginsStatement.executeUpdate("CREATE TABLE source ('name' varchar(255) PRIMARY KEY NOT NULL, 'url' varchar(255) NOT NULL, 'version' varchar(255) NOT NULL);");
			}else {
				pluginsConn = DriverManager.getConnection("jdbc:sqlite:" + database);
				pluginsStatement = pluginsConn.createStatement();
			}
		} catch (IOException e) {
			log.severe("IO exception on initialize");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		} catch (SQLException e) {
			log.severe("SQLite exception on initialize");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.severe("You need the SQLite library.");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Adds the content of source.db to the source table in plugins.db
	 * 
	 */
	
	private static void addSources() {
		out.sendConsole("Updating Sources...");
		wget.fetch(false, "http://efreak1996.cwsurf.de/source.db", "source.db", Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder());
		File database = new File(Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder() + File.separator + "source.db");
		try {
			Class.forName("org.sqlite.JDBC");
			sourceConn = DriverManager.getConnection("jdbc:sqlite:" + database);
			sourceStatement = sourceConn.createStatement();
			pluginsStatement.executeUpdate("DELETE FROM source;");
			ResultSet sourceRS = sourceStatement.executeQuery("SELECT * FROM source;");
			sourceRS.next();
			do {
				pluginsStatement.executeUpdate("INSERT INTO source ('name', 'url', 'version') VALUES ('" + sourceRS.getString("name") + "', '" + sourceRS.getString("url") + "', '" + sourceRS.getString("version") + "');");
				sourceRS.next();
			}while (!sourceRS.isAfterLast());
			out.sendConsole(ChatColor.GREEN + "Sources successfully updated...");
		} catch (SQLException e) {
			log.severe("SQLite exception on initialize");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.severe("You need the SQLite library.");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		} finally {
			try {
				sourceConn.close();
			} catch (SQLException e) {
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * Loads the content of external source files to the source table in plugins.db
	 * 
	 */
	
	private static void addExtSources() {
		File[] databases = new File(plugin.getDataFolder() + File.separator + "externalSources").listFiles();
		for (int i = 0; i < databases.length; i++) {
			try {
				Class.forName("org.sqlite.JDBC");
				extSourceConn[i] = DriverManager.getConnection("jdbc:sqlite:" + databases[i]);
				extSourceStatement[i] = extSourceConn[i].createStatement();
				ResultSet extSourceRS = extSourceStatement[i].executeQuery("SELECT * FROM source;");
				extSourceRS.next();
				do {
					pluginsStatement.executeUpdate("INSERT INTO source ('name', 'url', 'version') VALUES ('" + extSourceRS.getString("name") + "', '" + extSourceRS.getString("url") + "', '" + extSourceRS.getString("version") + "');");
					extSourceRS.next();
				}while (!extSourceRS.isAfterLast());
			} catch (SQLException e) {
				log.severe("SQLite exception on initialize");
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
			} catch (ClassNotFoundException e) {
				log.severe("You need the SQLite library.");
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
			} finally {
				try {
					extSourceConn[i].close();
				} catch (SQLException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * Updates the Plugintable
	 * 
	 */
	
	public static void updatePlugins() {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * FROM plugins Order BY 'name';");
			if (rs.next() == false) {
				Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					pluginsStatement.executeUpdate("INSERT INTO plugins ('name', 'version') VALUES ('" + pdfFile.getName() + "', '" + pdfFile.getVersion() + "');");
				}
			}else {
				Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					rs = pluginsStatement.executeQuery("SELECT * FROM plugins ORDER BY 'name';");
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					do {
						if (rs.getString("name").equalsIgnoreCase(pdfFile.getName())) {
							if (rs.getString("version").equalsIgnoreCase(pdfFile.getVersion())) break;
							else pluginsStatement.executeUpdate("UPDATE plugins SET 'version' = '" + pdfFile.getVersion() + "' WHERE 'name' = '" + pdfFile.getName() + "';");
						}else pluginsStatement.executeUpdate("INSERT INTO plugins ('name', 'version') VALUES ('" + pdfFile.getName() + "', '" + pdfFile.getVersion() + "');");
						rs.next();
					}while (!rs.isAfterLast());
				}
			}
		} catch (SQLException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Checks for newer versions of plugins
	 * 
	 */
	
	public static void checkUpdates() {
		out.sendConsole("Checking for Updates...");
		Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
		PluginDescriptionFile[] pdfFiles = new PluginDescriptionFile[plugins.length];
		ResultSet rs = null;
		StringBuilder updateList = new StringBuilder();
		for (int i = 0; i < plugins.length; i++) {
			pdfFiles[i] = plugins[i].getDescription();
		}
		try {
			for (int i = 0; i < plugins.length; i++) {
				rs = pluginsStatement.executeQuery("SELECT * FROM source WHERE 'name' = '" + pdfFiles[i].getName() + "' ORDER BY 'name';");
				if (!rs.getString("version").equalsIgnoreCase(pdfFiles[i].getVersion())) {
					if (updateList.length() > 0) updateList.append(", ");
					updateList.append(pdfFiles[i].getName());
				}
			}
			if (updateList.length() > 0) {
				out.sendConsole("Found Updates for: " + updateList.toString());
				out.sendConsole("Please run 'bm plugin update'");
			}
		} catch (SQLException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Gets all known infos about a plugin
	 * @param plugin The plugin which should be returned
	 * @return All infos which are known about this plugin
	 */
	
	public static Object[] getPlugin(Plugin plugin) {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * FROM plugins ORDER BY 'name';");
			PluginDescriptionFile pdfFile = plugin.getDescription();
			boolean equals = false;
			do {
				if (rs.getString("name").equalsIgnoreCase(pdfFile.getName())) {
					equals = true;
					break;
				}
			rs.next();
			if (!equals) return null;
			else {
				Object[] infos = new String[2];
				infos[0] = rs.getString("name");
				infos[1] = rs.getString("version");
				return infos;
			}
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * Updates infos about a plugin
	 * 
	 * @param plugin The Plugin which infos should be updated
	 * @param name The new Name of the Plugin
	 * @param version The new Version which should be set
	 * @return The new infos which are set
	 */

	public static Object[] setPlugin(Plugin plugin, String name, String version) {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * FROM plugins ORDER BY 'name';");
			PluginDescriptionFile pdfFile = plugin.getDescription();
			boolean equals = false;
			do {
				if (rs.getString("name").equalsIgnoreCase(pdfFile.getName())) {
					equals = true;
					break;
				}
			rs.next();
			if (!equals) return null;
			else {
				Object[] infos = new String[2];
				infos[0] = rs.getString("name");
				infos[1] = rs.getString("version");
				return infos;
			}
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		return null;
	}
	
	private static Connection pluginsConn = null;
	private static Statement pluginsStatement = null;
	private static Connection sourceConn = null;
	private static Statement sourceStatement = null;
	private static Connection[] extSourceConn = null;
	private static Statement[] extSourceStatement = null;
	private static Logger log = Logger.getLogger("Minecraft");
	public static BmOutput out = new BmOutput();
	private static BmConfiguration config = new BmConfiguration();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitManager");
}