package me.efreak1996.BukkitManager.Commands;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import me.efreak1996.BukkitManager.BmOutput;
import me.efreak1996.BukkitManager.wget;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmBukkitUpdate {
	
	static File updateFolder = Bukkit.getServer().getUpdateFolderFile().getAbsoluteFile();
	static BmOutput out = new BmOutput();

	public static void initialize() {}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args)
	{
		if (args.length >3) {
			p.sendMessage(ChatColor.RED + "Too many Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm bukkit update [rc|stable]");
		} else {
		//if ((args[1].equalsIgnoreCase("stable")) && args.length == 2) wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/artifact/target/craftbukkit-0.0.1-SNAPSHOT.jar", "craftbukkit-0.0.1-SNAPSHOT.jar", "lib/updates/bukkit/");
		//else if (((args[1].equalsIgnoreCase("rc")) && args.length == 2) || args.length == 1) wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/promotion/latest/Recommended/artifact/target/craftbukkit-0.0.1-SNAPSHOT.jar", "craftbukkit-0.0.1-SNAPSHOT.jar", "lib/updates/bukkit/");	
		}
    }
	public static void console(ConsoleCommandSender c, String[] args)
	{
		if (args.length >3) {
			c.sendMessage(ChatColor.RED + "Too many Arguments.");
			c.sendMessage(ChatColor.RED + "Usage: /bm bukkit update [rc|stable]");
		} else {
			//int oldVersion = Integer.parseInt(Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
			String oldVersion = Bukkit.getVersion().split("-b")[1].split("jnks")[0];
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
			} else if (args[2].equalsIgnoreCase("stable")) {
				String newVersion = fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("<title>")[1].split("</title>")[0].split("#")[1].split(" ")[0];
				if (newVersion.equalsIgnoreCase(oldVersion)) out.sendConsole("Server is running latest stable Build!");
				else {
					out.sendConsole("There is a new stable Build available! (" + newVersion + ")");
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
						wget.fetch(false, "http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/artifact/target/craftbukkit" + fetchPage("http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/").split("artifact/target/craftbukkit")[1].split(".jar")[0] + ".jar", "craftbukkit.jar", updateFolder);
						
					//}else out.sendConsole(ChatColor.RED + "Update of Bukkit aborted!");
				}
			}
		}
		/*if (args.length >3) {
			c.sendMessage(ChatColor.RED + "Too many Arguments.");
			c.sendMessage(ChatColor.RED + "Usage: /bm bukkit update [rc|stable]");
		} else {

		//if ((args[1].equalsIgnoreCase("stable")) && args.length == 2) wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/lastStableBuild/artifact/target/craftbukkit-0.0.1-SNAPSHOT.jar", "craftbukkit-0.0.1-SNAPSHOT.jar", "lib/updates/bukkit/");
		//else if (((args[1].equalsIgnoreCase("rc")) && args.length == 2) || args.length == 1) wget.fetch(true, "http://ci.bukkit.org/job/dev-CraftBukkit/promotion/latest/Recommended/artifact/target/craftbukkit-0.0.1-SNAPSHOT.jar", "craftbukkit-0.0.1-SNAPSHOT.jar", "lib/updates/bukkit/");	
		}*/
    }

	public static String fetchPage(String url) {
		  StringBuilder sb = new StringBuilder();
		  try {
		    Scanner scanner = new Scanner(new URL(url).openStream());
		    while (scanner.hasNextLine()) {
		      sb.append(scanner.nextLine() + "\n");
		    }
		    scanner.close();
		  } catch (MalformedURLException e) {
		    e.printStackTrace();
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		  return sb.toString();
	}
}