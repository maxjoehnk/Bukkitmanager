package org.efreak1996.Bukkitmanager;

import net.milkbowl.vault.permission.Permission;

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
	private static Permission vault = null;
	private static de.bananaco.bpermissions.imp.Permissions bPermissions = null;
	private static GroupManager groupManager = null;
	private static IOManager io;
	private static Configuration config;
	private static boolean usePerms;
	private static boolean forceSuper;
	private static String permSystem;
	
	public void initialize() {
		io = new IOManager();
		config = new Configuration();
		usePerms = config.getBoolean("General.Use-Permissions");
		forceSuper = config.getBoolean("General.Force-SuperPerms");
		if (forceSuper) {
			io.sendConsole(io.translate("Permissions.ForceSuper"));
			return;
		}
		if (usePerms) {
			if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
				if (config.getBoolean("General.Use-Vault")) {
					RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
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
				bPermissions = (de.bananaco.bpermissions.imp.Permissions) Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
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
	
	public void shutdown() {}
	
	public static boolean has(CommandSender sender, String perm) {
		return has(sender, perm, true);
	}
	
	public static boolean has(CommandSender sender, String perm, boolean log) {
		if (sender instanceof ConsoleCommandSender) return true;
		if (forceSuper) return sender.hasPermission(perm);
		boolean hasPerm = false;
		if (!(usePerms)) hasPerm = sender.isOp();
		else {
			if (permSystem.equalsIgnoreCase("Vault")) hasPerm = vault.has(sender, perm);
			else if (permSystem.equalsIgnoreCase("GroupManager")) hasPerm = groupManager.getWorldsHolder().getWorldPermissions(((Player) sender)).has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("PermissionsBukkit")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("PermissionsEx")) hasPerm = pex.has((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("bPermissions")) hasPerm = bPermissions.hasPermission((Player) sender, perm);
			else if (permSystem.equalsIgnoreCase("zPermissions")) hasPerm = sender.hasPermission(perm);
			else if (permSystem.equalsIgnoreCase("DroxPerms")) hasPerm = sender.hasPermission(perm);
		}
		if (hasPerm == false) {
			io.send(sender, io.translate("Command.NoPerm.Player"));
			if (log) io.sendConsoleWarning(io.translate("Command.NoPerm.Console").replaceAll("%cmd%", perm.replaceAll(".", " ")).replaceAll("%player%", sender.getName()));
		}
		return hasPerm;
	}
	
}
