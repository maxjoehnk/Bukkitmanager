package org.efreak1996.Bukkitmanager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmConfiguration;


public class BmFakepluginsManager {

	private static BmConfiguration config;
	
    protected BmFakepluginsManager() {
        config = new BmConfiguration();
    }

    public static boolean execute(CommandSender sender) {
        if (!sender.hasPermission("bukkit.command.plugins")) return true;
        if (sender.hasPermission("bm.fakeplugins")) sender.sendMessage("Plugins " + getFakePluginList());
        else sender.sendMessage("Plugins " + getPluginList());
        return true;
    }

    private static String getPluginList() {
        StringBuilder pluginList = new StringBuilder();
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();

        for (Plugin plugin : plugins) {
            if (pluginList.length() > 0) {
                pluginList.append(ChatColor.WHITE);
                pluginList.append(", ");
            }

            pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            pluginList.append(plugin.getDescription().getName());
        }
        
        return "(" + plugins.length + "): " + pluginList.toString();
    }
    
    private static String getFakePluginList() {
        StringBuilder pluginList = new StringBuilder();
        int pluginCount = 0;
        List<String> fakePlugins = config.getStringList("Fakepluginlist.Fakes");
        for (String plugin : fakePlugins) {
        	pluginCount++;
            if (pluginList.length() > 0) {
                pluginList.append(ChatColor.WHITE);
                pluginList.append(", ");
            }
            pluginList.append(ChatColor.GREEN);
            pluginList.append(plugin);
        }
        
        List<String> hiddenPlugins = config.getStringList("Fakepluginlist.Hidden");
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        if (hiddenPlugins.contains("*")) {
            for (Plugin plugin : plugins) {
            	if (hiddenPlugins.contains("--" + plugin.getDescription().getName())) {
                	pluginCount++;
            		if (pluginList.length() > 0) {
            			pluginList.append(ChatColor.WHITE);
            			pluginList.append(", ");
            		}
            		pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            		pluginList.append(plugin.getDescription().getName());
            	}
            }
        }else {
            for (Plugin plugin : plugins) {
        		if (!hiddenPlugins.contains(plugin.getDescription().getName())) {
                	pluginCount++;
                	if (pluginList.length() > 0) {
                        pluginList.append(ChatColor.WHITE);
                        pluginList.append(", ");
                    }
                    pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
                    pluginList.append(plugin.getDescription().getName());
        		}
        	}
        }

        return "(" + pluginCount + "): " + pluginList.toString();
    }
}
