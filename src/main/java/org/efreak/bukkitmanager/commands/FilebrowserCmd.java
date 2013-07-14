package org.efreak.bukkitmanager.commands;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.efreak.bukkitmanager.util.FileHelper;

public class FilebrowserCmd extends CommandHandler {

	public static HashMap<String, File> paths;
	public static HashMap<String, FileCommand> cmds;

	static {
		paths = new HashMap<String, File>();
		cmds = new HashMap<String, FileCommand>();
		registerCommand("cd", new cd());
		registerCommand("ls", new ls());
		registerCommand("exit", new exit());
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		return null;
	}

	@Command(label = "filebrowser", alias = false, hideHelp = true, usage = "filebrowser", permission = "bm.filebrowser")
	public boolean filebrowserCommand(CommandSender sender, String[] args) {
		paths.put(sender.getName(), FileHelper.getBukkitDir());
		io.createConversation(sender, sender.getName() + ".filebrowser", new StartPrompt().init(sender.getName()));
		return true;
	}

	public static void registerCommand(String cmd, FileCommand prompt) {
		cmds.put(cmd, prompt);
	}

}

class StartPrompt extends MessagePrompt {

	String id;
	
	public Prompt init(String id) {
		this.id = id;
		return this;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		context.setSessionData("ID", id);
		Conversable conversable = context.getForWhom();
		conversable.sendRawMessage("Welcome to the Filebrowser of Bukkitmanager.");
		conversable.sendRawMessage("The Filebrowser is using the same commands as on the most Unix OS.");
		return "For more help type in help";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return new CommandQuery();
	}
	
}

class CommandQuery extends StringPrompt {

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		String cmd = input.split(" ")[0];
		context.setSessionData("input", input);
		return FilebrowserCmd.cmds.get(cmd);
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "Current path: " + FilebrowserCmd.paths.get(context.getSessionData("ID")).toString();
	}
	
}

abstract class FileCommand extends MessagePrompt {

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return new CommandQuery();
	}
	
}

class cd extends FileCommand {

	@Override
	public String getPromptText(ConversationContext context) {
		String input = context.getSessionData("input").toString();
		if (input.equals("cd")) return "";
		else {
			String[] args = input.substring(input.indexOf(" ")).split(" ");
			if (args.length > 1) return "";
			else {
				String arg = args[0];
				File oldDir = FilebrowserCmd.paths.get(context.getSessionData("ID"));
				File newDir;
				if (arg.startsWith(File.pathSeparator)) newDir = new File(arg);
				else newDir = new File(oldDir, arg);
				if (!newDir.isDirectory()) return newDir.toString() + ": is no Directory!";
				else if (!newDir.exists()) return newDir.toString() + ": File or Directory not found!";
				else {
					FilebrowserCmd.paths.put(context.getSessionData("ID").toString(), newDir);
					return "Changed path";
				}
			}
		}
	}
}

class ls extends FileCommand {

	@Override
	public String getPromptText(ConversationContext context) {
		String input = context.getSessionData("input").toString();
		if (input.equals("ls")) {
			File[] files = FilebrowserCmd.paths.get(context.getSessionData("ID")).listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) context.getForWhom().sendRawMessage(ChatColor.BLUE + file.getName());
				else context.getForWhom().sendRawMessage(ChatColor.WHITE + file.getName());
			}
		}else {
			//String[] args = input.substring(input.indexOf(" ")).split(" ");
		}
		return null;
	}
}

class exit extends FileCommand {

	@Override
	public String getPromptText(ConversationContext context) {
		return null;
	}
	
	@Override
	public Prompt getNextPrompt(ConversationContext context) {
		return Prompt.END_OF_CONVERSATION;
	}
	
}