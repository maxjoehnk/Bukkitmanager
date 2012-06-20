package org.efreak1996.Bukkitmanager.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.BmFakepluginsManager;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmBukkitListener implements Listener{
	
	public static BmIOManager io;
	public static BmDatabase db;
	public static BmConfiguration config;
	
	public BmBukkitListener() {
		db = new BmDatabase();
		config = new BmConfiguration();
		io = new BmIOManager();
	}
	
	@EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
		if (event.getPlayer().hasPermission("bm.unkickable")) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!(db.getPlayer(event.getPlayer()))) db.addPlayer(event.getPlayer());
		else {
			Player p = event.getPlayer();
			p.setDisplayName((String) db.getPlayer(p, "displayname"));
			p.setPlayerListName((String) db.getPlayer(p, "listname"));
			if (!db.getPlayerSync(p)) {
				p.setExp(Float.valueOf(((Double) db.getPlayer(p, "exp")).toString()));
				p.setFoodLevel((Integer) db.getPlayer(p, "foodlevel"));
				p.setGameMode(GameMode.valueOf((String) db.getPlayer(p, "gamemode")));
				p.setHealth((Integer) db.getPlayer(p, "health"));
				p.setLevel((Integer) db.getPlayer(p, "level"));
				//p.setPlayerTime((Long) db.getPlayer(p, "Time"), (Boolean) db.getPlayer(p, "Time-Locked"));
				db.setPlayer(p, "synced", true);
				p.saveData();
			}
			if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) {
				Player[] player = Bukkit.getOnlinePlayers();
				for (int i = 0; i < player.length; i++) {
					if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
				}
				io.send(event.getPlayer(), io.translate("Player.Login.Hidden"));
			}
		}
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/plugins")) {
			if (config.getBoolean("Fakepluginlist.Enabled")) {
				BmFakepluginsManager.execute(event.getPlayer());
				event.setCancelled(true);
			}
		}
	}
}
