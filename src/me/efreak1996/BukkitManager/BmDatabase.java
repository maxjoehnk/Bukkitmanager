package me.efreak1996.BukkitManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BmDatabase {
    
	public static void initialize() {
		connectDatabase();
		addSources();
		updatePlugins();
		//checkUpdates();
	}
	
	public static void shutdown() {
		try {
			pluginsConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSources() {
		out.sendConsole("Updating Sources...");
		wget.fetch(false, "http://efreak1996.cwsurf.de/source.db", "source.db", Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder());
		File database = new File(Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder() + File.separator + "source.db");
		try {
			Class.forName("org.sqlite.JDBC");
			sourceConn = DriverManager.getConnection("jdbc:sqlite:" + database);
			sourceConn.setAutoCommit(false);
			sourceStatement = sourceConn.createStatement();
			pluginsStatement.executeUpdate("DELETE FROM source;");
			ResultSet sourceRS = sourceStatement.executeQuery("SELECT * FROM source");
			sourceRS.next();
			do {
				pluginsStatement.executeUpdate("INSERT INTO source (name, url, version) VALUES ('" + sourceRS.getString("name") + "', '" + sourceRS.getString("url") + "', '" + sourceRS.getString("version") + "');");
				sourceRS.next();
			}while (!sourceRS.isAfterLast());
			pluginsConn.commit();
			out.sendConsole(ChatColor.GREEN + "Sources successfully updated...");
		} catch (SQLException ex) {
			log.log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			log.log(Level.SEVERE, "You need the SQLite library.", ex);
		} finally {
			try {
				sourceConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void checkUpdates() {
		out.sendConsole("Checking for Updates...");
		String updateList = null;
		try {
			/*ResultSet sourceRS = null;
			ResultSet pluginsRS = null;
			pluginsRS = pluginsStatement.executeQuery("SELECT * FROM plugins;");
			sourceRS = pluginsStatement.executeQuery("SELECT * FROM source;");				
			pluginsRS.next();
			sourceRS.next();
			do {
				out.sendConsole("pluginsRS: " + pluginsRS.getString("name"));
				do {
					out.sendConsole("sourceRS: " + sourceRS.getString("name"));
					if (pluginsRS.getString("name").equalsIgnoreCase(sourceRS.getString("name"))) {
						if (!pluginsRS.getString("version").equalsIgnoreCase(pluginsRS.getString("version"))) {
							if (updateList != null) updateList = updateList + ", " + pluginsRS.getString("name");
							else updateList = pluginsRS.getString("name");
						}
						out.sendConsole("break");
						break;
					}
					sourceRS.next();
				}while (!sourceRS.isAfterLast());
				pluginsRS.next();
			}while (!pluginsRS.isAfterLast());*/
			ResultSet rs = pluginsStatement.executeQuery("SELECT name, version FROM plugins;");
			String[][] pluginsData = null;
			rs.next();
			int i = 0;
			do {
				i++;
				rs.next();
			}while (!rs.isAfterLast());
			pluginsData = new String[2][i];
			rs = pluginsStatement.executeQuery("SELECT name, version FROM plugins;");
			i = 0;
			do {
				pluginsData[0][i] = rs.getString("name");
				pluginsData[1][i] = rs.getString("version");
				i++;
				rs.next();
			}while (!rs.isAfterLast());
			System.out.println(pluginsData[0].length);
			for (i = 0; i < pluginsData[0].length; i++) {
				rs = pluginsStatement.executeQuery("SELECT * FROM source");
				rs.next();
				do {
					if (pluginsData[0][i].equalsIgnoreCase(rs.getString("name"))) if (pluginsData[1][i].equalsIgnoreCase(rs.getString("version"))) {
						if (updateList != null) updateList = updateList + ", " + rs.getString("name");
						else updateList = rs.getString("name");
					}
					rs.next();
				}while (!rs.isAfterLast());
			}
			if (updateList == null) out.sendConsole("No Updates found!");
			else {
				out.sendConsole("Updates found for: " + updateList);
				out.sendConsole("Please run " + ChatColor.YELLOW + "'bm plugin update all'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePlugins() {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * FROM plugins Order BY name");
			if (rs.next() == false) {
				Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					pluginsStatement.executeUpdate("INSERT INTO plugins (name, status, version) VALUES ('" + pdfFile.getName() + "', 'true', '" + pdfFile.getVersion() + "');");
				}
			}else {
				Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
				for (int i = 0; i < plugins.length; i++) {
					rs = pluginsStatement.executeQuery("SELECT * From plugins Order BY name");
					Plugin plugin = plugins[i];
					PluginDescriptionFile pdfFile = plugin.getDescription();
					boolean equals = false;
					do {
						if (rs.getString("name").equalsIgnoreCase(pdfFile.getName())) {
							if (rs.getString("version").equalsIgnoreCase(pdfFile.getVersion())) {
								equals = true;
								break;
							}else {
								pluginsStatement.executeUpdate("UPDATE plugins SET version = '" + pdfFile.getVersion() + "' WHERE name = '" + pdfFile.getName() + "';");
								equals = true;
							}
						}
						rs.next();
					}while (!rs.isAfterLast());
					if (!equals) {
						pluginsStatement.executeUpdate("INSERT INTO plugins (name, status, version) VALUES ('" + pdfFile.getName() + "', 'true', '" + pdfFile.getVersion() + "');");
					}
				}
			}
			pluginsConn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Object[] getPlugin(Plugin plugin) {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * From plugins Order BY name");
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
				Object[] infos = new String[3];
				infos[0] = rs.getString("name");
				infos[1] = rs.getBoolean("status");
				infos[2] = rs.getString("version");
				return infos;
			}
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object[] setPlugin(Plugin plugin, String name, boolean status, String version) {
		ResultSet rs = null;
		try {
			rs = pluginsStatement.executeQuery("SELECT * From plugins Order BY name");
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
				Object[] infos = new String[3];
				infos[0] = rs.getString("name");
				infos[1] = rs.getBoolean("status");
				infos[2] = rs.getString("version");
				return infos;
			}
			}while (!rs.isAfterLast());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void connectDatabase() {
		File database = new File(Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager").getDataFolder() + File.separator + "plugins.db");
		try {
			Class.forName("org.sqlite.JDBC");
			if (!database.exists()) {
				database.getAbsoluteFile().createNewFile();
				pluginsConn = DriverManager.getConnection("jdbc:sqlite:" + database);
				pluginsConn.setAutoCommit(false);
				pluginsStatement = pluginsConn.createStatement();
				pluginsStatement.executeUpdate("CREATE TABLE 'plugins' ('name' varchar(255) PRIMARY KEY NOT NULL, 'status' boolean NOT NULL,'version' varchar(255) NOT NULL);");
				pluginsStatement.executeUpdate("CREATE TABLE 'source' ('name' varchar(255) PRIMARY KEY NOT NULL, 'url' varchar(255) NOT NULL,'version' varchar(255) NOT NULL);");
				pluginsConn.commit();
			}else {
				pluginsConn = DriverManager.getConnection("jdbc:sqlite:" + database);
				pluginsConn.setAutoCommit(false);
				pluginsStatement = pluginsConn.createStatement();
			}
		} catch (IOException ex) {
			log.log(Level.SEVERE, "IO exception on initialize", ex);
		} catch (SQLException ex) {
			log.log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			log.log(Level.SEVERE, "You need the SQLite library.", ex);
		}
	}
	
	private static Connection pluginsConn = null;
	private static Statement pluginsStatement = null;
	private static Connection sourceConn = null;
	private static Statement sourceStatement = null;
	private static Logger log = Logger.getLogger("Minecraft");
	public static BmOutput out = new BmOutput();
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	public static void initialize(Bukkitmanager plugin)
    {
    	BmDatabase.plugin = plugin;
    	if (config.getBoolean("Plugins.Management", true)) {
    		if (config.getBoolean("Plugins.Update", true)) {
    			updateSource();
    			checkUpdates();
    		}
    	}
    }
    
    public static void checkUpdates() {
		
	}

	public static void updateSource() {
    	
    }    
    
    public static void addPlugin() {
		Plugin plugins[] = Bukkit.getPluginManager().getPlugins();
        PreparedStatement ps = null;
        try {
            Connection conn = BmDatabase1.getConnection();
            ps = conn
                    .prepareStatement("INSERT INTO plugins (name, status, version) VALUES (?,?,?)");
            //ps.setString(1, author);
            for(int i=0;i<plugins.length;i++)
            {
            	ps.setString(1, plugins[i].getDescription().getName());
                ps.setBoolean(2, plugins[i].getServer().getPluginManager().isPluginEnabled(plugins[i]));
                ps.setString(3, plugins[i].getDescription().getVersion());
            }
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "[BukkitManager]: Plugin Insert Exception", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "[BukkitManager]: Plugin Insert Exception (on close)", ex);
            }
        }
    }
    
    public static void copySrc()
    {
    	
    }
    
    
    
    
    
    
    
    
    
    
    //Connection 1 plugins
    
    
    //Connection 2 source
    public static Connection initialize2(String mainDir) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn2 = DriverManager.getConnection("jdbc:sqlite:" + mainDir + "/source.db");
            conn2.setAutoCommit(false);
            return conn2;
        } catch (SQLException ex) {
            Bukkitmanager.severe("SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            Bukkitmanager.severe("You need the SQLite library.", ex);
        }
        return conn2;
    }
    public static Connection getConnection2() {
        return conn2;
    }
    public static void closeConnection2() {
        if(conn2 != null) {
            try {
                conn2.close();
            } catch (SQLException ex) {
                Bukkitmanager.severe("Error on Connection close", ex);
            }
        }
    }

    //Connection Ext sources
    public static Connection initializeExt(File dataFolder) {
        try {
            Class.forName("org.sqlite.JDBC");
            connExt = DriverManager.getConnection("jdbc:sqlite:" + dataFolder.getAbsolutePath() + "/externalsources/source.db");
            connExt.setAutoCommit(false);
            return connExt;
        } catch (SQLException ex) {
            Bukkitmanager.severe("SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            Bukkitmanager.severe("You need the SQLite library.", ex);
        }
        return connExt;
    }
    public static Connection getConnectionExt() {
        return connExt;
    }
    public static void closeConnectionExt() {
        if(connExt != null) {
            try {
                connExt.close();
            } catch (SQLException ex) {
                Bukkitmanager.severe("Error on Connection close", ex);
            }
        }
    }
    
    //plugins.db
    private static boolean pluginsTableExists() {
        	ResultSet rs = null;
            try {
                Connection conn = getConnection1();

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
            Connection conn = getConnection1();

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
            Connection conn = getConnection1();
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
            Connection conn = getConnection1();
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
    public static Connection initialize1(String mainDir) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn1 = DriverManager.getConnection("jdbc:sqlite:" + mainDir + "/plugins.db");
            conn1.setAutoCommit(false);
            return conn1;
        } catch (SQLException ex) {
            Bukkitmanager.severe("SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            Bukkitmanager.severe("You need the SQLite library.", ex);
        }
        return conn1;
    }
    public static Connection getConnection1() {
        return conn1;
    }
    public static void closeConnection1() {
        if(conn1 != null) {
            try {
                conn1.close();
            } catch (SQLException ex) {
                Bukkitmanager.severe("Error on Connection close", ex);
            }
        }
    }
    
    private static Connection conn2;
    private static Connection connExt;
    private final static String PLUGINS_TABLE = "CREATE TABLE `plugins` (`name` STRING,`status` BOOLEAN,`version` STRING);";
    private final static String SOURCE_TABLE = "CREATE TABLE `source` (`name` STRING,`url` STRING,`version` STRING);";
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Bukkitmanager plugin = (Bukkitmanager) Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager");
	public static BmConfiguration config = new BmConfiguration();
}*/