package org.efreak.bukkitmanager.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.help.HelpManager;

import com.turt2live.metrics.EMetrics;
import com.turt2live.metrics.tracker.BasicTracker;

public abstract class Command {
	
	protected String label;
	protected String helpNode;
	protected String helpPerm;
	protected List<String> args;
	protected CommandCategory category;
	protected BasicTracker tracker;

	protected static final IOManager io;
	protected static final Configuration config;
	protected static final Database db;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
		db = Bukkitmanager.getDb();
	}
	
	public Command(String arg1Label, String arg2HelpNode, String arg3HelpPermission, List<String> arg4Args, CommandCategory arg5Category) {
		//super(arg5Category.toString() + "." + arg1Label);
		label = arg1Label;
		helpNode = arg2HelpNode;
		helpPerm = arg3HelpPermission;
		args = arg4Args;
		category = arg5Category;
		HelpManager.registerCommand(this);
		tracker = EMetrics.createBasicTracker("Total Command Usage", category.toString().toLowerCase() + " " + label.toLowerCase());
	}
	
	public abstract boolean execute(CommandSender sender, String[] args);
	
	protected boolean has(CommandSender sender, String perm) {
		return Permissions.has(sender, perm, "/bm " + category.toString().toLowerCase() + " " + label);
	}
	
	public CommandCategory getCategory() {
		return category;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<String> getArgs() {
		return args;
	}
	
	public String getHelpNode() {
		return helpNode;
	}
	
	public String getHelpPermission() {
		return helpPerm;
	}
	
	public BasicTracker getTracker() {
		return tracker;
	}
}
