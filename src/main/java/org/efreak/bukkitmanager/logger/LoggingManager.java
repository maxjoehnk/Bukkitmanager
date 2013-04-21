package org.efreak.bukkitmanager.logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.logger.block.*;
import org.efreak.bukkitmanager.logger.enchantment.*;

public class LoggingManager {
	
	private static File logDir;
	private static Configuration config;
	private static LoggerConfiguration logConfig;
	private static IOManager io;
	private static Plugin plugin;
	private static List<BmLogger> logger;

	public void initialize() {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
		plugin = Bukkitmanager.getInstance();
		logConfig = new LoggerConfiguration();
		logConfig.initialize();
		logDir = new File(plugin.getDataFolder() + File.separator + "logs");
		logDir.mkdirs();
		logger = new ArrayList<BmLogger>();
		if (config.getBoolean("General.Logger")) {
		io.sendConsole(io.translate("Logger.Loading"));
			//BlockLogger
			if (logConfig.get("Block.BlockBreak.Enabled")) registerLogger(new BlockBreakLogger());
			if (logConfig.get("Block.BlockBurn.Enabled")) registerLogger(new BlockBurnLogger());
			if (logConfig.get("Block.BlockCanBuild.Enabled")) registerLogger(new BlockCanBuildLogger());
			if (logConfig.get("Block.BlockDamage.Enabled")) registerLogger(new BlockDamageLogger());
			if (logConfig.get("Block.BlockDispense.Enabled")) registerLogger(new BlockDispenseLogger());
			if (logConfig.get("Block.BlockFade.Enabled")) registerLogger(new BlockFadeLogger());
			if (logConfig.get("Block.BlockForm.Enabled")) registerLogger(new BlockFormLogger());
			if (logConfig.get("Block.BlockFromTo.Enabled")) registerLogger(new BlockFromToLogger());
			if (logConfig.get("Block.BlockGrow.Enabled")) registerLogger(new BlockGrowLogger());
			if (logConfig.get("Block.BlockIgnite.Enabled")) registerLogger(new BlockIgniteLogger());
			if (logConfig.get("Block.BlockPhysics.Enabled")) registerLogger(new BlockPhysicsLogger());
			if (logConfig.get("Block.BlockPistonExtend.Enabled")) registerLogger(new BlockPistonExtendLogger());
			if (logConfig.get("Block.BlockPistonRetract.Enabled")) registerLogger(new BlockPistonRetractLogger());
			if (logConfig.get("Block.BlockPlace.Enabled")) registerLogger(new BlockPlaceLogger());
			if (logConfig.get("Block.BlockRedstone.Enabled")) registerLogger(new BlockRedstoneLogger());
			if (logConfig.get("Block.BlockSpread.Enabled")) registerLogger(new BlockSpreadLogger());
			if (logConfig.get("Block.EntityBlockForm.Enabled")) registerLogger(new EntityBlockFormLogger());
			if (logConfig.get("Block.LeavesDecay.Enabled")) registerLogger(new LeavesDecayLogger());
			if (logConfig.get("Block.SignChange.Enabled")) registerLogger(new SignChangeLogger());
		
			//EnchantmentLogger
			if (logConfig.get("Enchantment.EnchantItem.Enabled")) registerLogger(new EnchantItemLogger());
			if (logConfig.get("Enchantment.PrepareItemEnchant.Enabled")) registerLogger(new PrepareItemEnchantLogger());
			io.sendConsole(io.translate("Logger.Loaded"));
		}
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
