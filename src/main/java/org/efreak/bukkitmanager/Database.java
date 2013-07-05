package org.efreak.bukkitmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.efreak.bukkitmanager.logger.LoggingManager;

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
		if (LoggingManager.isDbLogging("Block.BlockBreak")) 
			createTable("Log_BlockBreakEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockBurn")) 
			createTable("Log_BlockBurnEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockCanBuild")) 
			createTable("Log_BlockCanBuildEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", buildable Boolean NOT NULL" +
				", material varchar(255) NOT NULL" +
				", materialId Integer NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockDamage")) 
			createTable("Log_BlockDamageEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL" +
				", instaBreak Boolean NOT NULL" +
				", itemInHand varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockDispense")) 
			createTable("Log_BlockDispenseEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", item varchar(255) NOT NULL" +
				", velocity varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockFade")) 
			createTable("Log_BlockFadeEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockForm")) 
			createTable("Log_BlockFormEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockFromTo")) 
			createTable("Log_BlockFromToEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", face varchar(255) NOT NULL" +
				", toBlock varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockGrow")) 
			createTable("Log_BlockGrowEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockIgnite")) 
			createTable("Log_BlockIgniteEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", igniteCause varchar(255) NOT NULL" +
				", player varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockPhysics")) 
			createTable("Log_BlockPhysicsEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", changedType varchar(255) NOT NULL" +
				", changedTypeId Integer NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockPistonExtend")) 
			createTable("Log_BlockPistonExtendEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", blocks varchar(255) NOT NULL" +
				", direction varchar(255) NOT NULL" +
				", length Integer NOT NULL" +
				", sticky Boolean NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockPistonRetract")) 
			createTable("Log_BlockPistonRetractEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", retractLocation varchar(255) NOT NULL" +
				", sticky Boolean NOT NULL" +
				", direction varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockPlace")) 
			createTable("Log_BlockPlaceEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", player varchar(255) NOT NULL" +
				", blockAgainst varchar(255) NOT NULL" +
				", blockPlaced varchar(255) NOT NULL" +
				", blockReplacedState varchar(255) NOT NULL" +
				", itemInHand varchar(255) NOT NULL" +
				", canBuild Boolean NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockRedstone")) 
			createTable("Log_BlockRedstoneEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", newCurrent Integer NOT NULL" +
				", oldCurrent Integer NOT NULL");
		if (LoggingManager.isDbLogging("Block.BlockSpread")) 
			createTable("Log_BlockSpreadEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL" +
				", source varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.EntityBlockForm")) 
			createTable("Log_EntityBlockFormEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL" +
				", newState varchar(255) NOT NULL" +
				", entity varchar(255) NOT NULL");
		if (LoggingManager.isDbLogging("Block.LeavesDecay")) 
			createTable("Log_LeavesDecayEvent", "time varchar(255) NOT NULL" +
				", block varchar(255) NOT NULL" +
				", cancelled Boolean NOT NULL");
		if (LoggingManager.isDbLogging("Block.SignChange")) 
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
			if (rs == null) return null;
			if (rs.isAfterLast()) return null;
			if (rs.isBeforeFirst()) rs.next();
			return rs;
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return null;
		}
	}
	
	public String queryString(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return null;
			if (rs.isAfterLast()) return null;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getString(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return null;
		}
	}
	
	public String queryString(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return null;
			if (rs.isAfterLast()) return null;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getString(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return null;
		}
	}
	
	public int queryInt(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1;
			if (rs.isAfterLast()) return -1;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getInt(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public int queryInt(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1;
			if (rs.isAfterLast()) return -1;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getInt(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}

	public float queryFloat(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1F;
			if (rs.isAfterLast()) return -1F;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getFloat(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public float queryFloat(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1F;
			if (rs.isAfterLast()) return -1F;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getFloat(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public double queryDouble(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1F;
			if (rs.isAfterLast()) return -1F;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getDouble(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}
	
	public double queryDouble(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return -1F;
			if (rs.isAfterLast()) return -1F;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getDouble(column);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return 0;
		}
	}

	public boolean queryBoolean(String sql) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return false;
			if (rs.isAfterLast()) return false;
			if (rs.isBeforeFirst()) rs.next();
			return rs.getBoolean(1);
		}catch (SQLException e) {
			if (config.getDebug()) e.printStackTrace();
			return false;
		}
	}
	
	public boolean queryBoolean(String sql, String column) {
		try {
			ResultSet rs = dbStatement.executeQuery(sql);
			if (rs == null) return false;
			if (rs.isAfterLast()) return false;
			if (rs.isBeforeFirst()) rs.next();
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
			if (rs.isAfterLast()) return false;
			if (rs.isBeforeFirst()) rs.next();
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
