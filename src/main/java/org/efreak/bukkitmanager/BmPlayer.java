package org.efreak.bukkitmanager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class BmPlayer implements OfflinePlayer {
	
	private OfflinePlayer player;
	public static final Database db;
	public static final Configuration config;
	public static IOManager io;
	
	static {
		db = Bukkitmanager.getDb();
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public BmPlayer(OfflinePlayer player) {
		this.player = player;
	}
	
	public static boolean isSyncEnabled() {
		return config.getBoolean("General.Player-Synchronisation");
	}
	
	@Override
	public boolean isOp() {
		return player.isOp();
	}
	
	@Override
	public void setOp(boolean value) {
		player.setOp(value);
	}

	@Override
	public Map<String, Object> serialize() {
		return player.serialize();
	}

	@Override
	public Location getBedSpawnLocation() {
		return player.getBedSpawnLocation();
	}

	public Calendar getFirstPlayedDateTime() {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(player.getFirstPlayed());
		return cal;
	}
	
	@Override
	public long getFirstPlayed() {
		return player.getFirstPlayed();
	}

	public Calendar getLastPlayedDateTime() {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(player.getLastPlayed());
		return cal;
	}
	
	@Override
	public long getLastPlayed() {
		return player.getLastPlayed();
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public Player getPlayer() {
		return player.getPlayer();
	}

	@Override
	public boolean hasPlayedBefore() {
		return player.hasPlayedBefore();
	}

	@Override
	public boolean isBanned() {
		return player.isBanned();
	}

	@Override
	public boolean isOnline() {
		return player.isOnline();
	}

	@Override
	public boolean isWhitelisted() {
		return player.isWhitelisted();
	}

	@Override
	public void setBanned(boolean banned) {
		player.setBanned(banned);
	}

	@Override
	public void setWhitelisted(boolean value) {
		player.setWhitelisted(value);
	}
	
	
	public String getDisplayName() {
		if (isOnline()) return getPlayer().getDisplayName();
		else return db.queryString("SELECT * FROM `player` WHERE `name`='" + getName() + "';", "displayname");
	}
	
	public void setDisplayName(String value) {
		if (isOnline()) getPlayer().setDisplayName(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `displayname`='" + value + "' WHERE `name`='" + getName() + "';");
	}

	public void applyDisplayName() {
		String displayname = getName();
		String temp = db.queryString("SELECT * FROM `player` WHERE `name`='" + getName() + "';", "displayname");
		if (temp != null) displayname = temp;
		getPlayer().setDisplayName(displayname);
	}
	
	public void resetDisplayName() {
		setDisplayName(getName());
	}
	
	public String getPlayerListName() {
		if (isOnline()) return getPlayer().getPlayerListName();
		else if (isSyncEnabled()) return db.queryString("SELECT * FROM `player` WHERE `name`='" + getName() + "';", "listname");
		else return getName();
	}
	
	public void setPlayerListName(String value) {
		if (isOnline()) getPlayer().setPlayerListName(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `listname`='" + value + "' WHERE `name`='" + getName() + "';");
	}

	public void applyPlayerListName() {
		String listname = getName();
		String temp = db.queryString("SELECT * FROM `player` WHERE `name`='" + getName() + "';", "listname");
		if (temp != null) listname = temp;
		getPlayer().setPlayerListName(listname);
	}
	
	public void resetPlayerListName() {
		setPlayerListName(getName());
	}
	
	public int getTotalExp() {
		if (isOnline()) return getPlayer().getTotalExperience();
		else if (isSyncEnabled()) return db.queryInt("SELECT `total_exp` FROM `player` WHERE `name`='" + getName() + "';", "total_exp");
		else return 0;
	}
	
	public float getExp() {
		if (isOnline()) return getPlayer().getExp();
		else if (isSyncEnabled()) return db.queryFloat("SELECT `exp` FROM `player` WHERE `name`='" + getName() + "';", "exp");
		else return 0;
	}
	
	public void setExp(float value) {
		if (isOnline()) getPlayer().setExp(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `exp`=" + value + " WHERE `name`='" + getName() + "';");
	}
	
	public void giveExp(Integer value) {
		if (isOnline()) getPlayer().giveExp(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `exp`=`exp` + " + value + " WHERE `name`='" + getName() + "';");
	}
	
	public int getFoodLevel() {
		if (isOnline()) return getPlayer().getFoodLevel();
		else if (isSyncEnabled()) return db.queryInt("SELECT `foodlevel` FROM `player` WHERE `name`='" + getName() + "';");
		else return 0;
	}
	
	public void setFoodLevel(int value) {
		if (isOnline()) getPlayer().setFoodLevel(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `foodlevel`='" + value + "' WHERE `name`='" + getName() + "';");		
	}
	
	public GameMode getGameMode() {
		if (isOnline()) return getPlayer().getGameMode();
		else if (isSyncEnabled()) return GameMode.valueOf(db.queryString("SELECT `gamemode` FROM `player` WHERE `name`='" + getName() + "';", "gamemode"));
		else return null;
	}
	
	public void setGameMode(GameMode value) {
		if (isOnline()) getPlayer().setGameMode(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `gamemode`='" + value + "' WHERE `name`='" + getName() + "';");		
	}
	
	public int getMaxHealth() {
		if (isOnline()) return getPlayer().getMaxHealth();
		else if (isSyncEnabled()) return db.queryInt("SELECT `max_health` FROM `player` WHERE `name`='" + getName() + "';", "max_health");
		else return 20;
	}
	
	public int getHealth() {
		if (isOnline()) return getPlayer().getHealth();
		else if (isSyncEnabled()) return db.queryInt("SELECT `health` FROM `player` WHERE `name`='" + getName() + "';", "health");
		else return 0;
	}
	
	public void setHealth(int value) {
		if (isOnline()) getPlayer().setHealth(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `health`='" + value + "' WHERE `name`='" + getName() + "';");		
	}
	
	public int getLevel() {
		if (isOnline()) return getPlayer().getLevel();
		else if (isSyncEnabled()) return db.queryInt("SELECT `level` FROM `player` WHERE `name`='" + getName() + "';", "level");
		else return 0;
	}
	
	public void setLevel(int value) {
		if (isOnline()) getPlayer().setLevel(value);
		else if (isSyncEnabled()) setSynced(false);
		if (isSyncEnabled()) db.update("UPDATE `player` SET `level`='" + value + "' WHERE `name`='" + getName() + "';");	  
	}
	
	public Location getLocation() {
		if (isOnline()) return getPlayer().getLocation();
		else if (isSyncEnabled()) {
			Location loc = new Location(Bukkit.getWorld(db.queryString("SELECT `location_world` FROM `player` WHERE  `name`='" + getName() + "';", "location_world")),
					db.queryInt("SELECT `location_x` FROM `player` WHERE `name`='" + getName() + "';", "location_x"),
					db.queryInt("SELECT `location_y` FROM `player` WHERE `name`='" + getName() + "';", "location_y"),
					db.queryInt("SELECT `location_z` FROM `player` WHERE `name`='" + getName() + "';", "location_z"));
			return loc;
		}
		else return null;
	}

	public World getWorld() {
		if (isOnline()) return getPlayer().getLocation().getWorld();
		else if (isSyncEnabled()) return Bukkit.getWorld(db.queryString("SELECT `location_world` FROM `player` WHERE  `name`='" + getName() + "';", "location_world"));
		else return null;		
	}

	public int getX() {
		if (isOnline()) return getPlayer().getLocation().getBlockX();
		else if (isSyncEnabled()) return db.queryInt("SELECT `location_x` FROM `player` WHERE `name`='" + getName() + "';", "location_x");
		else return 0;				
	}

	public int getY() {
		if (isOnline()) return getPlayer().getLocation().getBlockY();
		else if (isSyncEnabled()) return db.queryInt("SELECT `location_y` FROM `player` WHERE `name`='" + getName() + "';", "location_y");
		else return 0;				
	}
	
	public int getZ() {
		if (isOnline()) return getPlayer().getLocation().getBlockZ();
		else if (isSyncEnabled()) return db.queryInt("SELECT `location_z` FROM `player` WHERE `name`='" + getName() + "';", "location_z");
		else return 0;				
	}
	
	public void teleport(Location location, TeleportCause cause) {
		if (isOnline()) getPlayer().teleport(location, cause);
	}
	
	public String getRemotePassword() {
		return db.queryString("SELECT `remote_password` FROM `player` WHERE `name`='" + getName() + "';", "remote_password");
	}
	
	public void setRemotePassword(String password) {
		db.update("UPDATE `remote_password` FROM `player` WHERE `name`='" + getName() + "';");
	}
	
	/*public String getPassword() {
		return db.queryString("SELECT `password` FROM `player` WHERE `name`='" + getName() + "';", "password");
	}
	
	public void setPassword(String password) {
		db.update("UPDATE `password` FROM `player` WHERE `name`='" + getName() + "';");
	}*/
	
	public void setSynced(boolean synced) {
		db.update("UPDATE `player` SET `synced`='" + Database.parseBoolean(synced) + "' WHERE `name`='" + getName() + "';");
	}
	
	public boolean isSynced() {
		if (isSyncEnabled()) return db.queryBoolean("SELECT `synced` FROM `player` WHERE `name`='" + getName() + "';");
		else return true;
	}
	
	public boolean isVisible() {
		return !db.queryBoolean("SELECT `hidden` FROM `player` WHERE `name`='" + getName() + "';", "hidden");
	}
	
	public void hide() {
		Player[] players = Bukkit.getOnlinePlayers();
		for (int i = 0; i < players.length; i++) {
			if (!(players[i].hasPermission("bm.see"))) players[i].hidePlayer(getPlayer());
		}
	}
	
	public void show() {
		Player[] players = Bukkit.getOnlinePlayers();
		for (int i = 0; i < players.length; i++) players[i].showPlayer(getPlayer());
	}
	
	public void sync() {
		if (!isSynced()) {
			setDisplayName(db.queryString("SELECT `displayname` FROM `player` WHERE `name`='" + getName() + "';", "displayname"));
			setPlayerListName(db.queryString("SELECT `listname` FROM `player` WHERE `name`='" + getName() + "';", "listname"));
			setLevel(db.queryInt("SELECT `level` FROM `player` WHERE `name`='" + getName() + "';", "level"));
			setExp(db.queryFloat("SELECT `exp` FROM `player` WHERE `name`='" + getName() + "';", "exp"));
			setHealth(db.queryInt("SELECT `health` FROM `player` WHERE `name`='" + getName() + "';", "health"));
			setFoodLevel(db.queryInt("SELECT `foodlevel` FROM `player` WHERE `name`='" + getName() + "';", "foodlevel"));
			//setGameMode(GameMode.valueOf(db.queryString("SELECT `gamemode` FROM `player` WHERE `name`='" + getName() + "';", "gamemode")));
			getPlayer().saveData();
			setSynced(true);
		}else db.update("UPDATE `player` SET `listname`='" + getPlayerListName() + "', `displayname`='" + getDisplayName() + "', `level`='" + getLevel() + "', `exp`='" + getExp() + "', `total_exp`='" + getPlayer().getTotalExperience() + "', `health`='" + getHealth() + "', `max_health`='" + getPlayer().getMaxHealth() + "', `foodlevel`='" + getPlayer().getFoodLevel() + "', `gamemode`='" + getPlayer().getGameMode() + "', `location_world`='" + getWorld().getName() + "', `location_x`='" + getX() + "', `location_y`='" + getY() + "', `location_z`='" + getZ() + "' WHERE name='" + getName() + "';");
	}

	public void onJoin() {
		if (!db.tableContains("player", "name", getName())) db.update("INSERT INTO player (name, synced, hidden, listname, displayname, level, exp, total_exp, health, max_health, foodlevel, gamemode, remote_password, location_world, location_x, location_y, location_z) " + 
			"VALUES ('" + getName() + "', '1', '0', '" + getPlayer().getPlayerListName() + "', '" + getPlayer().getDisplayName() + "', '" + getPlayer().getLevel() + "', '" + getPlayer().getExp() + "', '" + getPlayer().getTotalExperience() + "', '" + getPlayer().getHealth() + "', '" + getPlayer().getMaxHealth() + "', '" + getPlayer().getFoodLevel() + "', '" + getPlayer().getGameMode() + "', NULL, '" + getWorld().getName() + "', '" + getX() + "', '" + getY() + "', '" + getZ() + "');");
		if (isSyncEnabled()) sync();
		applyDisplayName();
		applyPlayerListName();
		if (!isVisible()) {
			hide();
			io.send(getPlayer(), io.translate("Player.Login.Hidden"));
		}
		//ScoreboardManager.showMainScoreboard(this);
	}
	
	public void onLeave() {
		if (isSyncEnabled()) sync();
	}
}
