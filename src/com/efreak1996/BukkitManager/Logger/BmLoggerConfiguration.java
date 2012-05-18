package com.efreak1996.BukkitManager.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmPlugin;

public class BmLoggerConfiguration {

	private static File configFile;
	private static YamlConfiguration configYaml;
	private static BmConfiguration config;
	
	public void initialize() {
		config = new BmConfiguration();
		configFile = new File(BmPlugin.getPlugin().getDataFolder(), "logger.yml");
		configYaml = new YamlConfiguration();
		try {
			if (!configFile.exists()) configFile.createNewFile();
			configYaml.load(configFile);
			addContent();
			configYaml.save(configFile);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void addContent() {
		//Block Events
		addContent("Block", "BlockBreak");
		addContent("Block", "BlockBurn");
		addContent("Block", "BlockCanBuild");
		addContent("Block", "BlockDamage");
		addContent("Block", "BlockDispense");
		addContent("Block", "BlockFade");
		addContent("Block", "BlockForm");
		addContent("Block", "BlockFromTo");
		addContent("Block", "BlockGrow");
		addContent("Block", "BlockIgnite");
		addContent("Block", "BlockPhysics");
		addContent("Block", "BlockPistonExtend");
		addContent("Block", "BlockPistonRetract");
		addContent("Block", "BlockPlace");
		addContent("Block", "BlockRedstone");
		addContent("Block", "BlockSpread");
		addContent("Block", "EntityBlockForm");
		addContent("Block", "LeavesDecay");
		addContent("Block", "SignChange");
		//Enchantment Events
		addContent("Enchantment", "EnchantItem");
		addContent("Enchantment", "PrepareItemEnchant");
		//Entity Events
		
	}
	
	private void addContent(String eventType, String event) {
		if (configYaml.get(eventType + "." + event + ".Enabled") == null) configYaml.set(eventType + "." + event + ".Enabled", false);
		if (configYaml.get(eventType + "." + event + ".File") == null) configYaml.set(eventType + "." + event + ".File", false);
		if (configYaml.get(eventType + "." + event + ".Database") == null) configYaml.set(eventType + "." + event + ".Database", true);
	}
	
	public static boolean get(String key) {
		return configYaml.getBoolean(key);
	}
}
