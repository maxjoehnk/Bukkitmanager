package org.efreak.bukkitmanager;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Permissions {

	private static PermissionManager pex = null;
	private static net.milkbowl.vault.permission.Permission vault = null;
	//private static de.bananaco.bpermissions.imp.Permissions bPermissions = null;
	private static GroupManager groupManager = null;
	private static IOManager io;
	private static Configuration config;
	public static boolean usePerms;
	private static boolean forceSuper;
	private static String permSystem;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	public void init() {
		usePerms = config.getBoolean("General.Permissions.Use-Permissions");
		forceSuper = config.getBoolean("General.Permissions.Force-SuperPerms");
		if (forceSuper) {
			io.sendConsole(io.translate("Permissions.Permissions.ForceSuper"));
			return;
		}
		if (usePerms) {
			if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
				if (config.getBoolean("General.Permissions.Use-Vault")) {
					RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
					if (permissionProvider != null) {
						vault = permissionProvider.getProvider();
						permSystem = "Vault";
						io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "Vault"));
					}
		        }
			}else if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit") != null) {
				permSystem = "PermissionsBukkit";
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "PermissionsBukkit"));
			}else if (Bukkit.getServer().getPluginManager().getPlugin("GroupManager") != null) {
				permSystem = "GroupManager";
				groupManager = ((GroupManager) Bukkit.getServer().getPluginManager().getPlugin("GroupManager"));
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "GroupManager"));
			}else if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
				pex = PermissionsEx.getPermissionManager();
				permSystem = "PermissionsEx";
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "PermissionsEx"));
			}else if (Bukkit.getServer().getPluginManager().getPlugin("bPermissions") != null) {
				//bPermissions = (de.bananaco.bpermissions.imp.Permissions) Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
				permSystem = "bPermissions";
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "bPermissions"));
			}else if (Bukkit.getServer().getPluginManager().getPlugin("zPermissions") != null) {
				permSystem = "zPermissions";
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "zPermissions"));
			}else if (Bukkit.getServer().getPluginManager().getPlugin("DroxPerms") != null) {
				permSystem = "DroxPerms";
				io.sendConsole(io.translate("Permissions.Found").replaceAll("%perms%", "DroxPerms"));
			}else {
				io.sendConsoleWarning(io.translate("Permissions.NoPerms"));	
				io.sendConsole(io.translate("Permissions.OP"));
				usePerms = false;
        	}
		}else io.sendConsole(io.translate("Permissions.OP"));
	}
		
	public static boolean has(CommandSender sender, String perm, String cmd) {
		return has(sender, perm, cmd, config.getBoolean("General.Permissions.Log"));
	}
	
	public static boolean has(CommandSender sender, String perm, String cmd, boolean log) {
		if (sender instanceof ConsoleCommandSender) return true;
		if (sender.isOp()) return true;
		boolean hasPerm = false;
		if (!usePerms) hasPerm = sender.isOp();
		else {
			if (forceSuper) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("Vault")) hasPerm = vault.has(sender, perm);
			else if (permSystem.equalsIgnoreCase("GroupManager")) hasPerm = groupManager.getWorldsHolder().getWorldPermissions(((Player) sender)).has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("PermissionsBukkit")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("PermissionsEx")) hasPerm = pex.has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("bPermissions")) hasPerm = de.bananaco.bpermissions.imp.Permissions.hasPermission((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("zPermissions")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("DroxPerms")) hasPerm = sender.hasPermission(perm);
		}
		if (hasPerm == false) {
			io.send(sender, io.translate("Command.NoPerm.Player"));
			if (log) io.sendConsoleWarning(io.translate("Command.NoPerm.Console").replaceAll("%cmd%", cmd).replaceAll("%player%", sender.getName()));
		}
		return hasPerm;
	}
	
	public static boolean has(CommandSender sender, String perm) {
		if (sender instanceof ConsoleCommandSender) return true;
		if (sender.isOp()) return true;
		boolean hasPerm = false;
		if (!usePerms) hasPerm = sender.isOp();
		else {
			if (forceSuper) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("Vault")) hasPerm = vault.has(sender, perm);
			else if (permSystem.equalsIgnoreCase("GroupManager")) hasPerm = groupManager.getWorldsHolder().getWorldPermissions(((Player) sender)).has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("PermissionsBukkit")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("PermissionsEx")) hasPerm = pex.has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("bPermissions")) hasPerm = de.bananaco.bpermissions.imp.Permissions.hasPermission((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("zPermissions")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("DroxPerms")) hasPerm = sender.hasPermission(perm);
		}
		return hasPerm;
	}
	
}
