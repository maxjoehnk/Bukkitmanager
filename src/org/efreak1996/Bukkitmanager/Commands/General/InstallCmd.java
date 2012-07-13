package org.efreak1996.Bukkitmanager.Commands.General;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;

import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class InstallCmd extends Command {
	
	public InstallCmd() {
		super("install", "Install", new ArrayList<String>(), CommandCategory.GENERAL);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (0 + length)) io.sendFewArgs(sender, "/bm install");
		else if (args.length > (0 + length)) io.sendManyArgs(sender, "/bm install");
		else {
			if (has(sender, "bm.install")) io.createConversation(sender, "Bukkitmanager Installation", new WelcomePrompt());
		}
		return true;
	}
}

class WelcomePrompt extends MessagePrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Welcome to the Installationguide of Bukkitmanager.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return new IntroductionPrompt();
	}
}

class IntroductionPrompt extends MessagePrompt {
	
	@Override
	public String getPromptText(ConversationContext context) {
		return "This Wizard will help you to configure Bukkitmanager for the first time.\nEverything is pre-configured so you don't have to change, but you can:)";
	}
	
	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return new GeneralPrompt();
	} 
}

class GeneralPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Lets start with the configuration of the General Stuff of Bukkitmanager: (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new AliasPrompt();
		else return new IOPrompt();
	}
	
}

class AliasPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should the Commandaliases be enabled or not? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		Configuration config = new Configuration();
		if (input) {
			config.set("General.Aliases.Bukkit", true);
			config.set("General.Aliases.Plugin", true);
			config.set("General.Aliases.Player", true);
			config.set("General.Aliases.Language", true);
		}else {
			config.set("General.Aliases.Bukkit", false);
			config.set("General.Aliases.Plugin", false);
			config.set("General.Aliases.Player", false);
			config.set("General.Aliases.Language", false);
		}
		return new PermissionsPrompt();
	}
}

class PermissionsPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should a Permissionssytem be used or may only OP's use the commands? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		Configuration config = new Configuration();
		if (input) {
			config.set("General.Use-Permissions", true);
		}else {
			config.set("General.Use-Permissions", false);
		}
		if (Permissions.usePerms == true && Bukkitmanager.getPluginManager().getPlugin("Vault") != null) return new VaultPrompt();
		else if (Permissions.usePerms == false) return new SuperPermsPrompt();
		else return new StatisticsPrompt();
	}
}

class VaultPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should Vault be used instead of a Permissionssytem? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		Configuration config = new Configuration();
		if (input) {
			config.set("General.Use-Permissions", true);
		}else {
			config.set("General.Use-Permissions", false);
		}
		return new StatisticsPrompt();
	}
}

class SuperPermsPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should the use of the Bukkitpermissions-API be forced? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		new Configuration().set("General.Use-Permissions", input);
		return new StatisticsPrompt();
	}
}

class StatisticsPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should usagestatistics be send? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		new Configuration().set("General.Statistics", input);
		return new IOPrompt();
	}
}

class IOPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Input/Ouput of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new AutomessagePrompt();
	}
}

class IOShowPrefixPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Should the Prefix be shown before messages send by Bukkitmanager? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		new Configuration().set("IO.Show-Prefix", input);
		return new IOPrefixPrompt();
	}
}

class IOPrefixPrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Please enter the Prefix: (default: &4[Bukkitmanager])";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (!input.equalsIgnoreCase("default")) new Configuration().set("IO.Prefix", input);
		return new AutomessagePrompt();
	}
}

class AutomessagePrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Automessagefeature of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new AutosavePrompt();
	}
}

class AutosavePrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Autosavefeature of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new AutobackupPrompt();
	}
}

class AutobackupPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Autobackupfeature of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new DatabasePrompt();
	}
}

class DatabasePrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Database of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new DBSystemPrompt();
		else return new CustomMessagePrompt();
	}
}

class DBSystemPrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Which Databasesytem should be used? (SQLite, MySQL, H2)";
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (input.equalsIgnoreCase("sqlite")) return new DBSQLitePrompt();
		else if (input.equalsIgnoreCase("mysql")) return new DBMySQLPrompt();
		else if (input.equalsIgnoreCase("h2")) return new DBH2Prompt();
		else return new DBSystemPrompt();
	}
}

class DBSQLitePrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "How should the Database File be called? (default: database.db)";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Configuration config = new Configuration();
		config.set("Database.System", "SQLite");
		config.set("Database.File", input);
		return new CustomMessagePrompt();
	}
}

class DBMySQLPrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "How should the Database File be called? (default: database.db)";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		new Configuration().set("Database.File", input);
		return new CustomMessagePrompt();
	}
}

class DBH2Prompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "How should the Database File be called? (default: database.db)";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		new Configuration().set("Database.File", input);
		return new CustomMessagePrompt();
	}
}

class CustomMessagePrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Custommessagefeature of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new FakepluginlistPrompt();
	}
}

class FakepluginlistPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Now let's configure the Fakepluginlistfeature of Bukkitmanager! (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new IOShowPrefixPrompt();
		else return new FinishedPrompt();
	}
}

class FinishedPrompt extends BooleanPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Finish! Do you wanna change something or apply the changes? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
		if (input) return new ChooseTopicPrompt();
		else {
			new Configuration().save();
			Bukkitmanager.reload();
			return Prompt.END_OF_CONVERSATION;
		}
	}
}

class ChooseTopicPrompt extends StringPrompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Please choose a Topic:";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		return null;
	}
}