package org.efreak.bukkitmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class Database {

	protected Connection dbConn;
	protected Statement dbStatement;
	protected static final Configuration config;
	protected static final IOManager io;
	private static final HashMap<String, Database> dbSystems;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
		dbSystems = new HashMap<String, Database>();
	}
	
	protected abstract void connect() throws ClassNotFoundException, SQLException;
	
	protected abstract void config();
	
	private void setupTables() {
		createTable("player", "name varchar(255) PRIMARY KEY NOT NULL" +
				", synced boolean NOT NULL" +
				", hidden boolean NOT NULL" +
				", listname varchar(255) NOT NULL" +
				", displayname varchar(255) NOT NULL" +
				", level int NOT NULL" +
				", exp Float NOT NULL" +
				", total_exp int NOT NULL" +
				", health int NOT NULL" +
				", max_health int NOT NULL" +
				", foodlevel int NOT NULL" +
				", gamemode varchar(255) NOT NULL" +
				//", password varchar(255)" +
				", remote_password varchar(255)" +
				", location_world varchar(255) NOT NULL" +
				", location_x int NOT NULL" +
				", location_y int NOT NULL" +
				", location_z int NOT NULL");
		//Blockevents
		createTable("Log_BlockBreakEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL");
		createTable("Log_BlockBurnEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL");
		createTable("Log_BlockCanBuildEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", buildable Boolean NOT NULL" +
				", material varchar(255) NOT NULL" +
				", materialId Integer NOT NULL");
		createTable("Log_BlockDamageEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL" +
				", instaBreak Boolean NOT NULL" +
				", itemInHand varchar(255) NOT NULL");
		createTable("Log_BlockDispenseEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", item varchar(255) NOT NULL" +
				", velocity varchar(255) NOT NULL");
		createTable("Log_BlockFadeEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		createTable("Log_BlockFormEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		createTable("Log_BlockFromToEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", face varchar(255) NOT NULL" +
				", toBlock varchar(255) NOT NULL");
		createTable("Log_BlockGrowEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		createTable("Log_BlockIgniteEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", igniteCause varchar(255) NOT NULL" +
				", player varchar(255) NOT NULL");
		createTable("Log_BlockPhysicsEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", changedType varchar(255) NOT NULL" +
				", changedTypeId Integer NOT NULL");
		createTable("Log_BlockPistonExtendEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", blocks varchar(255) NOT NULL" +
				", direction varchar(255) NOT NULL" +
				", length Integer NOT NULL" +
				", sticky Boolean NOT NULL");
		createTable("Log_BlockPistonRetractEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", retractLocation varchar(255) NOT NULL" +
				", sticky Boolean NOT NULL" +
				", direction varchar(255) NOT NULL");
		createTable("Log_BlockPlaceEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL" +
				", blockAgainst varchar(255) NOT NULL" +
				", blockPlaced varchar(255) NOT NULL" +
				", blockReplacedState varchar(255) NOT NULL" +
				", itemInHand varchar(255) NOT NULL" +
				", canBuild Boolean NOT NULL");
		createTable("Log_BlockRedstoneEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", newCurrent Integer NOT NULL" +
				", oldCurrent Integer NOT NULL");
		createTable("Log_BlockSpreadEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL" +
				", source varchar(255) NOT NULL");
		createTable("Log_EntityBlockFormEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL" +
				", entity varchar(255) NOT NULL");
		createTable("Log_LeavesDecayEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL");
		createTable("Log_SignChangeEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", lines varchar(255) NOT NULL" +
				", player varchar(255) NOT NULL");
	}
	
	public void init() {
		try {
			config();
			connect();
			dbStatement = dbConn.createStatement();
			setupTables();
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
			io.sendConsoleError("Can't connect to Database! Driver couldn't be found");
		}
	}
	
	public void shutdown() {
		try {
			dbStatement.close();
			dbConn.close();
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	public boolean createTable(String tableName, String columns) {
		if (dbStatement != null) {
			try {
				dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ");");
				return true;
			} catch (SQLException e) {
				if (config.getDebug()) e.printStackTrace();
				return false;
			}
		}else return false;
	}
	
	public ResultSet query(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return null;
			//if (rs.isAfterLast()) return null;
			//rs.beforeFirst();
			return rs;
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return null;
		}
	}
	
	public String queryString(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return null;
			return rs.getString(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return "";
		}
	}
	
	public String queryString(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return null;
			return rs.getString(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return "";
		}
	}
	
	public int queryInt(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return -1;
			return rs.getInt(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public int queryInt(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return -1;
			return rs.getInt(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}

	public float queryFloat(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return -1F;
			return rs.getFloat(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public float queryFloat(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return -1F;
			return rs.getFloat(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}

	public boolean queryBoolean(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return false;
			return rs.getBoolean(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return false;
		}
	}
	
	public boolean queryBoolean(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (!rs.first()) return false;
			return rs.getBoolean(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return false;
		}
	}

	public int update(String sql) {
		try {
			return dbStatement.executeUpdate(sql);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return -1;
		}
	}
	
	public boolean tableContains(String table, String column, String value) {
		try {
			ResultSet rs = query("SELECT COUNT(" + column + ") AS " + column + "Count FROM " + table + " WHERE " + column + "='" + value + "'");
			if (rs == null) return false;
			if (!rs.first()) return false;
			if (rs.getInt(1) == 0) return false;
			else return true;
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return false;
		}
	}
	
	//Static Stuff
	public static void registerDatabaseSystem(String systemName, Database dbSystem) {
		dbSystems.put(systemName, dbSystem);
	}
	
	public static Database getDatabaseBySystem(String systemName) {
		return dbSystems.get(systemName);
	}
	
	public static int parseBoolean(boolean bool) {
		if (bool) return 1;
		else return 0;
	}
	
	public void log(String table, String columns, String values) {
		try {
			dbStatement.executeUpdate("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
		} catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
}
