package org.efreak.bukkitmanager.databases;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.efreak.bukkitmanager.Database;

/**
 * 
 * The MySQL Implementation of the Database
 *
 */

public class MySQL extends Database {

	@Override
	protected void connect() throws ClassNotFoundException, SQLException {
		String host = config.getString("Database.Host");
		int port = config.getInt("Database.Port");
		String name = config.getString("Database.Name");
		String username = config.getString("Database.Username");
		String password = config.getString("Database.Password");
		Class.forName("com.mysql.jdbc.Driver");
		dbConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name, username, password);
	}

	@Override
	protected void config() {
		config.update("Database.Host", "localhost");
		config.update("Database.Port", 3306);
		config.update("Database.Name", "minecraft");
		config.update("Database.Username", "root");
		config.update("Database.Password", "");
		config.save();
	}
}
