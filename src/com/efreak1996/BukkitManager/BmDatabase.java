package com.efreak1996.BukkitManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.efreak1996.BukkitManager.Util.BmIOManager;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 
 * Loads and manages the database
 * 
 * @author efreak1996
 *
 */

public class BmDatabase {
    
	private static Connection dbConn = null;
	private static Statement dbStatement = null;
	public static BmIOManager io;
	private static BmConfiguration config;
	private static Plugin plugin;
	private static String dbType = "SQLite";
	
	/**
	 * 
	 * Creates and connects to the database
	 * 
	 */
	
	public void initialize() {
		plugin = BmPlugin.getPlugin();
		config = new BmConfiguration();
		io = new BmIOManager();
		dbType = config.getDatabaseType();
		connectDatabase();
		updatePlugins();
	}
	
	/**
	 * 
	 * Closes the connection
	 * 
	 */
	
	public void shutdown() {
		try {
			dbConn.close();
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Connects to the Database
	 * 
	 */
	
	private void connectDatabase() {
		try {
			if (dbType.equalsIgnoreCase("SQLite")) connectSQLite();
			else if (dbType.equalsIgnoreCase("MySQL")) connectMySQL();
			else if (dbType.equalsIgnoreCase("H2")) connectH2();
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}	
	
	/**
	 * 
	 * Connects to a SQLite Database
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	private void connectSQLite() throws SQLException, IOException, ClassNotFoundException {
		File database = new File(plugin.getDataFolder() + File.separator + config.getString("Database.File", "database.db"));
		Class.forName("org.sqlite.JDBC");
		if (!database.exists()) {
			database.getAbsoluteFile().createNewFile();
			dbConn = DriverManager.getConnection("jdbc:sqlite:" + database);
			dbStatement = dbConn.createStatement();
		}else {
			dbConn = DriverManager.getConnection("jdbc:sqlite:" + database);
			dbStatement = dbConn.createStatement();
		}
		createTables();
	}
	
	/**
	 * 
	 * Connects to a MySQL Database
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	
	private void connectMySQL() throws ClassNotFoundException, IOException, SQLException {
		String dbHost = config.getString("Database.Host", "localhost");
		int dbPort = config.getInt("Database.Port", 3306);
		String dbName = config.getString("Database.Name", "minecraft");
		String dbUser = config.getString("Database.Username", "root");
		String dbPass = config.getString("Database.Password", "");
		Class.forName("com.mysql.jdbc.Driver");
		dbConn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName, dbUser, dbPass);
		dbStatement = dbConn.createStatement();
		createTables();
	}
	
	/**
	 * 
	 * Connects to a H2 Database
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	
	private void connectH2() throws ClassNotFoundException, IOException, SQLException {
		String dbHost = config.getString("Database.Host", "localhost");
		String dbName = config.getString("Database.Name", "minecraft");
		String dbUser = config.getString("Database.Username", "root");
		String dbPass = config.getString("Database.Password", "");
		Class.forName("org.h2.Driver");
		dbConn = DriverManager.getConnection("jdbc:h2:tcp://" + dbHost + "/" + dbName, dbUser, dbPass);
		dbStatement = dbConn.createStatement();
		createTables();
	}
	
	/**
	 * 
	 * Creates all Tables
	 * 
	 * @throws SQLException
	 */
	
	private void createTables() throws SQLException {
		//A List of all installed Plugins with some Details for Plugin Updating
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS plugins (name varchar(255) PRIMARY KEY NOT NULL, version varchar(255) NOT NULL);");
		//A List of all Plugins, which works like a cache for the latest versions from the BukkitDev Pluginlist
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS source (name varchar(255) PRIMARY KEY NOT NULL, url varchar(255) NOT NULL, version varchar(255) NOT NULL);");
		//A Playerlist to save Hiddenstates and changes stuff, while the Player is Offline
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS player (player Player PRIMARY KEY NOT NULL, player_name varchar(255) NOT NULL, synced boolean NOT NULL, hidden boolean NOT NULL, listname varchar(255) NOT NULL, displayname varchar(255) NOT NULL, exp Float NOT NULL, foodlevel int NOT NULL, gamemode GameMode NOT NULL, health int NOT NULL, level int NOT NULL);");
		//A List of all registered Users with Password for the RemoteSwingGUI and the Webinterface
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS remote (player varchar(255) PRIMARY KEY NOT NULL, password varchar(255));");
		//All Logging Tables for the Block Events
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockBreakEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, player Player NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockBurnEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockCanBuildEvent (time String NOT NULL, block Block NOT NULL, buildable Boolean NOT NULL, material Material NOT NULL, materialId Integer NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockDamageEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, player Player NOT NULL, instaBreak Boolean NOT NULL, itemInHand ItemStack NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockDispenseEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, item ItemStack NOT NULL, velocity Vector NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockFadeEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, newState BlockState NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockFormEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, newState BlockState NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockFromToEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, face BlockFace NOT NULL, toBlock Block NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockGrowEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, newState BlockState NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockIgniteEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, igniteCause IgniteCause NOT NULL, player Player NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockPhysicsEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, changedType Material NOT NULL, changedTypeId Integer NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockPistonExtendEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, blocks List NOT NULL, direction BlockFace NOT NULL, length Integer NOT NULL, sticky Boolean NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockPistonRetractEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, retractLocation Location NOT NULL, sticky Boolean NOT NULL, direction BlockFace NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockPlaceEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, player Player NOT NULL, blockAgainst Block NOT NULL, blockPlaced Block NOT NULL, blockReplacedState BlockState NOT NULL, itemInHand ItemStack NOT NULL, canBuild Boolean NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockRedstoneEvent (time String NOT NULL, block Block NOT NULL, newCurrent Integer NOT NULL, oldCurrent Integer NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_BlockSpreadEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, newState BlockState NOT NULL, source Block NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_EntityBlockFormEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, newState BlockState NOT NULL, entity Entity NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_LeavesDecayEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_SignChangeEvent (time String NOT NULL, block Block NOT NULL, cancelled Boolean NOT NULL, lines String[] NOT NULL, player Player NOT NULL);");
		//All Logging Tables for the Enchantment Events
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_EnchantItemEvent (time String NOT NULL, enchantBlock Block NOT NULL, cancelled Boolean NOT NULL, enchanter Player NOT NULL, enchantmentsToAdd Map NOT NULL, expLevelCost Integer NOT NULL, inventory Inventory NOT NULL, item ItemStack NOT NULL, view InventoryView NOT NULL, viewers List NOT NULL, button Integer NOT NULL);");
		dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_PrepareItemEnchantEvent (time String NOT NULL, enchantBlock Block NOT NULL, cancelled Boolean NOT NULL, enchanter Player NOT NULL, enchantmentBonus Integer NOT NULL, expLevelCostsOffered Integer[] NOT NULL, inventory Inventory NOT NULL, item ItemStack NOT NULL, view InventoryView NOT NULL, viewers List NOT NULL);");
	}
	
	/*
	 * 
	 * Adds the content of source.db to the source table in plugins.db
	 * 
	 */
	/*
	private void addSources() {
		out.sendConsole("Updating Sources...");
		//BmDownloader.fetch(false, "http://efreak1996.cwsurf.de/source.db", "source.db", plugin.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder());
		File database = new File(plugin.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder() + File.separator + "source.db");
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
			if (config.config.getBoolean("General.Debug")) e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.severe("You need the SQLite library.");
			if (config.config.getBoolean("General.Debug")) e.printStackTrace();
		} finally {
			try {
				sourceConn.close();
			} catch (SQLException e) {
				if (config.config.getBoolean("General.Debug")) e.printStackTrace();
			}
		}
	}
	*/
	/*
	 * 
	 * Loads the content of external source files to the source table in plugins.db
	 * 
	 */
	/*
	private void addExtSources() {
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
				if (config.config.getBoolean("General.Debug")) e.printStackTrace();
			} catch (ClassNotFoundException e) {
				log.severe("You need the SQLite library.");
				if (config.config.getBoolean("General.Debug")) e.printStackTrace();
			} finally {
				try {
					extSourceConn[i].close();
				} catch (SQLException e) {
					if (config.config.getBoolean("General.Debug")) e.printStackTrace();
				}
			}
		}
	}
	*/
	
	/**
	 * 
	 * Updates the Plugintable
	 * 
	 */
	public void updatePlugins() {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM plugins Order BY name;");
			if (rs.next() == false) {
				Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					dbStatement.executeUpdate("INSERT INTO plugins (name, version) VALUES ('" + pdfFile.getName() + "', '" + pdfFile.getVersion() + "');");
				}
			}else {
				Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					rs = dbStatement.executeQuery("SELECT * FROM plugins ORDER BY name;");
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					do {
						if (rs.getString("name").equalsIgnoreCase(pdfFile.getName())) {
							if (rs.getString("version").equalsIgnoreCase(pdfFile.getVersion())) break;
							else dbStatement.executeUpdate("UPDATE plugins SET version = '" + pdfFile.getVersion() + "' WHERE name = '" + pdfFile.getName() + "';");
						}else dbStatement.executeUpdate("INSERT INTO plugins (name, version) VALUES ('" + pdfFile.getName() + "', '" + pdfFile.getVersion() + "');");
						rs.next();
					}while (!rs.isAfterLast());
				}
			}
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	/*
	 * 
	 * Checks for newer versions of plugins
	 * 
	 */
	/*
	public void checkUpdates() {
		out.sendConsole("Checking for Updates...");
		Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
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
			if (config.config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	*/
	/**
	 * 
	 * Gets all known infos about a plugin
	 * 
	 * @param plugin The plugin which should be returned
	 * @return All infos which are known about this plugin
	 */
	public Object[] getPlugin(Plugin plugin) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM plugins ORDER BY name;");
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
			if (config.getDebug()) e.printStackTrace();
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
	public Object[] setPlugin(Plugin plugin, String name, String version) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM plugins ORDER BY name;");
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
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean getPlayer(Player p) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM player;");
			if (rs.isAfterLast()) return false;
			do {
				if (rs.getObject("player").equals(p.toString())) return true;
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public boolean getPlayer(String name) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM player;");
			if (rs.isAfterLast()) return false;
			do {
				if (rs.getObject("player_name").equals(name)) return true;
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public Object getPlayer(Player p, String info) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM player;");
			do {
				if (rs.getObject("player").equals(p.toString())) return rs.getObject(info);
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public Object getPlayer(String name, String info) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM player;");
			do {
				if (rs.getObject("player_name").equals(name)) return rs.getObject(info);
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public boolean getPlayerSync(Player p) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM player;");
			do {
				if (rs.getObject("player").equals(p.toString())) return rs.getBoolean("synced");
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return true;
	}
	public boolean setPlayer(Player p, String info, Object value) {
		try {
			dbStatement.executeUpdate("UPDATE player SET " + info + "='" + value + "' WHERE player='" + p + "';");
			return true;
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return false;
	}
	public boolean setPlayer(String name, String info, Object value) {
		try {
			dbStatement.executeUpdate("UPDATE player SET synced='false' WHERE player_name='" + name + "';");
			dbStatement.executeUpdate("UPDATE player SET " + info + "='" + value + "' WHERE player_name='" + name + "';");
			return true;
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return false;
	}
	public int addPlayer(Player p) {
		try {
			if (config.getDev()) dbStatement.executeUpdate("INSERT INTO remote (player) VALUES ('" + p.getName() + "');");
			return dbStatement.executeUpdate("INSERT INTO player (player, player_name, synced, hidden, listname, displayname, exp, foodlevel, gamemode, health, level) VALUES ('" + p + "', '" + p.getName() + "', 'true', 'false', '" + p.getPlayerListName() + "', '" + p.getDisplayName() + "', '" + p.getExp() + "', '" + p.getFoodLevel() + "', '" + p.getGameMode() + "', '" + p.getHealth() + "', '" + p.getLevel() + "');");
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return 0;
	}
	public void updatePlayer(Player p) {
		try {
			dbStatement.executeUpdate("UPDATE player SET (listname, displayname, exp, foodlevel, gamemode, health, level) = ('" + p.getPlayerListName() + "', '" + p.getDisplayName() + "', '" + p.getExp() + "', '" + p.getFoodLevel() + "', '" + p.getGameMode() + "', '" + p.getHealth() + "', '" + p.getLevel() + "') WHERE player='" + p + "';");
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	public boolean getRemote(String user) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM remote;");
			if (rs.isAfterLast()) return false;
			do {
				if (rs.getObject("player").equals(user)) return true;
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public Object getRemote(String user, String info) {
		ResultSet rs = null;
		try {
			rs = dbStatement.executeQuery("SELECT * FROM remote;");
			do {
				if (rs.getObject("player").equals(user)) return rs.getObject(info);
			rs.next();
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return false;
	}
	public boolean setRemote(String user, String info, Object value) {
		try {
			dbStatement.executeUpdate("UPDATE remote SET " + info + "='" + value + "' WHERE player='" + user + "';");
			return true;
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return false;
	}

	public void log(String table, String columns, String values) {
		try {
			dbStatement.executeUpdate("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
}