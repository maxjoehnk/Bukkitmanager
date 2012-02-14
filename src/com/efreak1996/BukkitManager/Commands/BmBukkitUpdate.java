package com.efreak1996.BukkitManager.Commands;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmOutput;
import com.efreak1996.BukkitManager.wget;

public class BmBukkitUpdate {
	
	static File updateFolder = Bukkit.getServer().getUpdateFolderFile().getAbsoluteFile();
	static BmOutput out = new BmOutput();
	static String oldVersion = Bukkit.getVersion().split("-b")[1].split("jnks")[0];
	private static BmConfiguration config = new BmConfiguration();

	public static void initialize() {
		if (config.Config.getBoolean("Bukkit.CheckUpdate.on-start")) {
			out.sendConsole("Checking for new Bukkit RC Build...");
			String newVersion = fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
			if (newVersion.equalsIgnoreCase(oldVersion)) out.sendConsole(ChatColor.GREEN + "Server is running latest RC Build!");
			else {
				out.sendConsole(ChatColor.RED + "There is a new RC Build available! (" + newVersion + ")");
				out.sendConsole(ChatColor.RED + "To update please run bm bukkit update rc!");
			}
		}
	}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args)
	{
		if (args.length >3) {
			p.sendMessage(ChatColor.RED + "Too many Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm bukkit update [rc|stable|build]");
		} else {
			if (args.length == 2) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendPlayer(p, "Server is running latest RC Build!");
				else {
					out.sendPlayer(p, "There is a new RC Build available! (" + newVersion + ")");
						out.sendPlayer(p, "Downloading new craftbukkit.jar to " + updateFolder.toString());
						if (wget.fetch(true, "http://ci.bukkit.org/job/dev-craftbukkit/Recommended/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder)) out.sendPlayer(p, "Successfully downloaded to " + updateFolder);
						else out.sendPlayer(p, "Error downloading File to " + updateFolder);
				}
			} else if (args[2].equalsIgnoreCase("rc")) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendPlayer(p, "Server is running latest RC Build!");
				else {
					out.sendPlayer(p, "There is a new RC Build available! (" + newVersion + ")");
						out.sendPlayer(p, "Downloading new craftbukkit.jar to " + updateFolder.toString());
						if (wget.fetch(true, "http://ci.bukkit.org/job/dev-craftbukkit/Recommended/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder)) out.sendPlayer(p, "Successfully downloaded to " + updateFolder);
						else out.sendPlayer(p, "Error downloading File to " + updateFolder);
				}
			} else if (args[2].equalsIgnoreCase("stable")) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendPlayer(p, "Server is running latest stable Build!");
				else {
					out.sendPlayer(p, "There is a new stable Build available! (" + newVersion + ")");
						out.sendPlayer(p, "Downloading new craftbukkit.jar to " + updateFolder.toString());
						if (wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder)) out.sendPlayer(p, "Successfully downloaded to " + updateFolder);
						else out.sendPlayer(p, "Error downloading File to " + updateFolder);
				}
			}else {
					out.sendConsole("Downloading new craftbukkit.jar to " + updateFolder.toString());
					if (wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/" + args[2] + "/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/" + args[2] + "/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder)) out.sendPlayer(p, "Successfully downloaded to " + updateFolder);
					else out.sendPlayer(p, "Error downloading File to " + updateFolder);
			}
		}
    }
	public static void console(ConsoleCommandSender c, String[] args)
	{
		if (args.length >3) {
			c.sendMessage(ChatColor.RED + "Too many Arguments.");
			c.sendMessage(ChatColor.RED + "Usage: bm bukkit update [rc|stable|build]");
		} else {
			//int oldVersion = Integer.parseInt(Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
			if (args.length == 2) {
				//int newVersion = Integer.parseInt(fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0]);
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendConsole("Server is running latest RC Build!");
				else {
					out.sendConsole("There is a new RC Build available! (" + newVersion + ")");
					/*out.sendConsole("Do you wanna update? [y/n]");
					String input = "";
					try {
						byte buffer[] = new byte[80];
						int read;
						read = System.in.read(buffer, 0, 80);
						input = new String(buffer, 0, read);
					} catch (IOException e) {
					e.printStackTrace();
					}
					if (input.equalsIgnoreCase("j") || input.equalsIgnoreCase("y")) {*/
						out.sendConsole("Downloading new craftbukkit.jar to " + updateFolder.toString());
						wget.fetch(false, "http://ci.bukkit.org/job/dev-craftbukkit/Recommended/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder);
					//}else out.sendConsole(ChatColor.RED + "Update of Bukkit aborted!");
				}
			} else if (args[2].equalsIgnoreCase("rc")) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendConsole("Server is running latest RC Build!");
				else {
					out.sendConsole("There is a new RC Build available! (" + newVersion + ")");
						out.sendConsole("Downloading new craftbukkit.jar to " + updateFolder.toString());
						wget.fetch(false, "http://ci.bukkit.org/job/dev-craftbukkit/Recommended/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-craftbukkit/Recommended/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder);
				}
			} else if (args[2].equalsIgnoreCase("stable")) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendConsole("Server is running latest stable Build!");
				else {
					out.sendConsole("There is a new stable Build available! (" + newVersion + ")");
						out.sendConsole("Downloading new craftbukkit.jar to " + updateFolder.toString());
						wget.fetch(false, "http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder);
				}
			}else {
					out.sendConsole("Downloading new craftbukkit.jar to " + updateFolder.toString());
					wget.fetch(false, "http://ci.bukkit.org/job/dev-CraftBukkit/" + args[2] + "/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/" + args[2] + "/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder);
			}
		}
    }

	public static String fetchPage(String url) {
		  StringBuilder sb = new StringBuilder();
		  try {
			  Scanner scanner = new Scanner(new URL(url).openStream());
			  while (scanner.hasNextLine()) 
				  sb.append(scanner.nextLine() + "\n");
			  scanner.close();
		  } catch (MalformedURLException e) {
			  	if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		  } catch (IOException e) {
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		  }
		  return sb.toString();
	}
}