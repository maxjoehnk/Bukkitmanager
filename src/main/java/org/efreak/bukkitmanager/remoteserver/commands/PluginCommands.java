package org.efreak.bukkitmanager.remoteserver.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.remoteserver.RemoteCommand;
import org.efreak.bukkitmanager.remoteserver.RemoteCommandHandler;
import org.efreak.bukkitmanager.remoteserver.ReturnCodes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluginCommands extends RemoteCommandHandler {

	@RemoteCommand(name="disableplugin")
	public String disablePlugin(JSONObject args) throws JSONException {
		String pluginName = "";
		if (args.has("plugin")) pluginName = args.getString("plugin");
		else return ReturnCodes.echo400();
		Plugin plugin = PluginManager.getPlugin(pluginName);
		if (plugin == null) return ReturnCodes.echo404();
		PluginManager.disablePlugin(plugin);
		return ReturnCodes.echo200();
	}
	
	@RemoteCommand(name="enableplugin")
	public String enablePlugin(JSONObject args) throws JSONException {
		String pluginName = "";
		if (args.has("plugin")) pluginName = args.getString("plugin");
		else return ReturnCodes.echo400();
		Plugin plugin = PluginManager.getPlugin(pluginName);
		if (plugin == null) return ReturnCodes.echo404();
		PluginManager.enablePlugin(plugin);
		return ReturnCodes.echo200();
	}
	
	@RemoteCommand(name="restartplugin")
	public String restartPlugin(JSONObject args) throws JSONException {
		String pluginName = "";
		if (args.has("plugin")) pluginName = args.getString("plugin");
		else return ReturnCodes.echo400();
		Plugin plugin = PluginManager.getPlugin(pluginName);
		if (plugin == null) return ReturnCodes.echo404();
		PluginManager.restartPlugin(plugin);
		return ReturnCodes.echo200();
	}
	
	@RemoteCommand(name="pluginlist")
	public String pluginList() {
		JSONArray json = new JSONArray();
		try {
			json.put(new JSONObject().put("updates", PluginManager.getPluginUpdateCount()).put("plugins", PluginManager.getPlugins().length).toString());
		}catch (JSONException e) {
			if (config.getDebug()) e.printStackTrace();
			return ReturnCodes.echo500();
		}
		for (Plugin plugin : PluginManager.getPlugins()) {
			try {
				List<String[]> permissions = new ArrayList<String[]>();
				for (Permission permission : plugin.getDescription().getPermissions()) {
					String[] perm = new String[2];
					perm[0] = permission.getName();
					perm[1] = permission.getDescription();
					permissions.add(perm);
				}
				String object = new JSONObject()
					.put("name", plugin.getName())
					.put("version", plugin.getDescription().getVersion())
					.put("enabled", plugin.isEnabled())
					.put("desc", plugin.getDescription().getDescription())
					.put("authors", plugin.getDescription().getAuthors())
					.put("update", !PluginManager.checkPlugin(plugin))
					.put("website", plugin.getDescription().getWebsite())
					.put("softdepend", plugin.getDescription().getSoftDepend())
					.put("depend", plugin.getDescription().getDepend())
					.put("permissions", permissions)
					.toString();
				json.put(object);
			} catch (JSONException e) {
				if (config.getDebug()) e.printStackTrace();
				return ReturnCodes.echo500();
			}
		}
		return json.toString();
	}
}
