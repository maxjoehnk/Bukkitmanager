package com.efreak1996.BukkitManager.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.Logger.Block.*;
import com.efreak1996.BukkitManager.Logger.Enchantment.*;
import com.efreak1996.BukkitManager.Logger.Entity.*;

public class BmLoggingManager {
	
	private static File logDir;
	private static BmConfiguration config;
	private static BmLoggerConfiguration logConfig;
	private static BmIOManager io;
	private static Plugin plugin;
	private static List<BmLogger> logger;

	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		plugin = BmPlugin.getPlugin();
		logConfig = new BmLoggerConfiguration();
		io.sendConsole("Loading Logger...");
		logConfig.initialize();
		logDir = new File(plugin.getDataFolder() + File.separator + "logs");
		logDir.mkdirs();
		logger = new ArrayList<BmLogger>();
		//BlockLogger
		if (logConfig.get("Block.BlockBreak.Enabled")) registerLogger(new BlockBreakLogger());
		if (logConfig.get("Block.BlockBurn.Enabled")) registerLogger(new BlockBurnLogger());
		if (logConfig.get("Block.BlockCanBuild.Enabled")) registerLogger(new BlockCanBuildLogger());
		if (logConfig.get("Block.BlockDamage.Enabled")) registerLogger(new BlockDamageLogger());
		
		//EnchantmentLogger
		if (logConfig.get("Enchantment.EnchantItem.Enabled")) registerLogger(new EnchantItemLogger());
		if (logConfig.get("Enchantment.PrepareItemEnchant.Enabled")) registerLogger(new PrepareItemEnchantLogger());
		io.sendConsole("Logger loaded!");
	}
	
	public void shutdown() {
		for (int i = 0; i < logger.size(); i++) logger.get(i).shutdown();
	}
	
	public static File getLogDir() {
		return logDir;
	}
	
	public static void registerLogger(BmLogger arg1Logger) {
		arg1Logger.setupLogger();
		logger.add(arg1Logger);
	}
}