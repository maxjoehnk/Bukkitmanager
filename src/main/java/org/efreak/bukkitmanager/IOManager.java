package org.efreak.bukkitmanager;

import java.util.Hashtable;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.addons.BukkitmanagerAddon;
import org.efreak.bukkitmanager.util.Translator;

/**
 * 
 * The IOManager Handles all Input and Output
 * 
 * @author efreak
 * 
 */

public class IOManager {

    private static final Configuration config;
    private static final Plugin plugin;
    private static ConversationFactory conversationFactory;
    private static Hashtable<String, Conversation> conversations;
    public static String prefix = ChatColor.DARK_RED + "[Bukkitmanager] "
            + ChatColor.WHITE;
    public static String error = ChatColor.RED + "[Error] " + ChatColor.WHITE;
    public static String warning = ChatColor.YELLOW + "[Warning] "
            + ChatColor.WHITE;
    private static Translator translator;
    private static boolean color = true;

    static {
        plugin = Bukkitmanager.getInstance();
        config = Bukkitmanager.getConfiguration();
    }

    public void init() {
        color = config.getBoolean("IO.ColoredLogs");
        prefix = color(config.getString("IO.Prefix")) + " " + ChatColor.WHITE;
        error = color(config.getString("IO.Error")) + " " + ChatColor.WHITE;
        warning = color(config.getString("IO.Warning")) + " " + ChatColor.WHITE;
        translator = new Translator();
        translator.initialize();
        conversationFactory = new ConversationFactory(
                Bukkitmanager.getInstance());
        conversations = new Hashtable<String, Conversation>();
    }

    public void broadcast(String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin.getServer()
                .broadcastMessage(parseColor(prefix + msg));
        else plugin.getServer().broadcastMessage(parseColor(msg));
    }

    public void broadcast(String msg, boolean showPrefix) {
        if (showPrefix) plugin.getServer().broadcastMessage(
                parseColor(prefix + msg));
        else plugin.getServer().broadcastMessage(parseColor(msg));
    }

    public void broadcast(String msg, String perm) {
        if (config.getBoolean("IO.Show-Prefix")) plugin.getServer().broadcast(
                parseColor(prefix + msg), perm);
        else plugin.getServer().broadcast(parseColor(msg), perm);
    }

    public void broadcast(String msg, String perm, boolean showPrefix) {
        if (showPrefix) plugin.getServer().broadcast(parseColor(prefix + msg),
                perm);
        else plugin.getServer().broadcast(parseColor(msg), perm);
    }

    public void sendConsole(String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin.getServer()
                .getConsoleSender().sendMessage(color(prefix + msg));
        else plugin.getServer().getConsoleSender().sendMessage(color(msg));
    }

    public void sendConsole(String msg, boolean showPrefix) {
        if (showPrefix) plugin.getServer().getConsoleSender()
                .sendMessage(color(prefix + msg));
        else plugin.getServer().getConsoleSender().sendMessage(color(msg));
    }

    public void sendConsoleAddon(BukkitmanagerAddon addon, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin
                .getServer()
                .getConsoleSender()
                .sendMessage(
                        color(prefix + ChatColor.DARK_RED + "["
                                + addon.getAddonName() + "] " + ChatColor.WHITE
                                + msg));
        else plugin.getServer().getConsoleSender().sendMessage(color(msg));
    }

    public void sendConsoleDev(String msg) {
        plugin.getServer().getConsoleSender()
                .sendMessage(color(prefix + "[Dev] " + msg));
    }

    public void sendConsoleDevError(String msg) {
        plugin.getServer()
                .getConsoleSender()
                .sendMessage(
                        color(prefix + "[Dev] " + error + ChatColor.RED + msg));
    }

    public void sendConsoleWarning(String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin.getServer()
                .getConsoleSender()
                .sendMessage(color(prefix + warning + ChatColor.YELLOW + msg));
        else plugin.getServer().getConsoleSender()
                .sendMessage(color(warning + msg));
    }

    public void sendConsoleWarning(String msg, boolean showPrefix) {
        if (showPrefix) plugin.getServer().getConsoleSender()
                .sendMessage(color(prefix + warning + ChatColor.YELLOW + msg));
        else plugin.getServer().getConsoleSender()
                .sendMessage(color(warning + msg));
    }

    public void sendConsoleAddonWarning(BukkitmanagerAddon addon, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin
                .getServer()
                .getConsoleSender()
                .sendMessage(
                        color(prefix + ChatColor.DARK_RED + "["
                                + addon.getAddonName() + "] " + warning
                                + ChatColor.YELLOW + msg));
        else plugin.getServer().getConsoleSender().sendMessage(color(msg));
    }

    public void sendConsoleError(String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin.getServer()
                .getConsoleSender()
                .sendMessage(color(prefix + error + ChatColor.RED + msg));
        else plugin.getServer().getConsoleSender()
                .sendMessage(color(error + msg));
    }

    public void sendConsoleError(String msg, boolean showPrefix) {
        if (showPrefix) plugin.getServer().getConsoleSender()
                .sendMessage(color(prefix + error + ChatColor.RED + msg));
        else plugin.getServer().getConsoleSender()
                .sendMessage(color(error + msg));
    }

    public void sendConsoleAddonError(BukkitmanagerAddon addon, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) plugin
                .getServer()
                .getConsoleSender()
                .sendMessage(
                        color(prefix + ChatColor.DARK_RED + "["
                                + addon.getAddonName() + "] " + error
                                + ChatColor.RED + msg));
        else plugin.getServer().getConsoleSender().sendMessage(color(msg));
    }

    public void send(CommandSender sender, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) sender
                .sendMessage(parseColor(prefix + msg));
        else sender.sendMessage(parseColor(msg));
    }

    public void send(CommandSender sender, String msg, boolean showPrefix) {
        if (showPrefix) sender.sendMessage(parseColor(prefix + msg));
        else sender.sendMessage(parseColor(msg));
    }

    public void sendHeader(CommandSender sender, String title) {
        sender.sendMessage(parseColor(formatHeader(title)));
    }

    public void sendTranslation(CommandSender sender, String key) {
        if (config.getBoolean("IO.Show-Prefix")) sender
                .sendMessage(parseColor(prefix + translate(key)));
        else sender.sendMessage(parseColor(translate(key)));
    }

    public void sendTranslation(CommandSender sender, String key,
            boolean showPrefix) {
        if (showPrefix) sender.sendMessage(parseColor(prefix + translate(key)));
        else sender.sendMessage(parseColor(translate(key)));
    }

    public void sendWarning(CommandSender sender, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) sender
                .sendMessage(parseColor(prefix + warning + ChatColor.YELLOW
                        + msg));
        else sender.sendMessage(parseColor(warning + msg));
    }

    public void sendWarning(CommandSender sender, String msg, boolean showPrefix) {
        if (showPrefix) sender.sendMessage(parseColor(prefix + warning
                + ChatColor.YELLOW + msg));
        else sender.sendMessage(parseColor(warning + msg));
    }

    public void sendError(CommandSender sender, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) sender
                .sendMessage(parseColor(prefix + error + ChatColor.RED + msg));
        else sender.sendMessage(parseColor(error + msg));
    }

    public void sendError(CommandSender sender, String msg, boolean showPrefix) {
        if (showPrefix) sender.sendMessage(parseColor(prefix + error
                + ChatColor.RED + msg));
        else sender.sendMessage(parseColor(warning + msg));
    }

    public void sendFewArgs(CommandSender sender, String usage) {
        if (config.getBoolean("IO.Show-Prefix")) {
            sender.sendMessage(parseColor(prefix + translate("Command.FewArgs")));
            sender.sendMessage(parseColor(prefix
                    + translate("Command.Usage").replaceAll("%usage%", usage)));
        } else {
            sender.sendMessage(parseColor(translate("Command.FewArgs")));
            sender.sendMessage(parseColor(translate("Command.Usage")
                    .replaceAll("%usage%", usage)));
        }
    }

    public void sendFewArgs(CommandSender sender, String usage,
            boolean showPrefix) {
        if (showPrefix) {
            sender.sendMessage(parseColor(prefix + translate("Command.FewArgs")));
            sender.sendMessage(parseColor(prefix
                    + translate("Command.Usage").replaceAll("%usage%", usage)));
        } else {
            sender.sendMessage(parseColor(translate("Command.FewArgs")));
            sender.sendMessage(parseColor(translate("Command.Usage")
                    .replaceAll("%usage%", usage)));
        }
    }

    public void sendManyArgs(CommandSender sender, String usage) {
        if (config.getBoolean("IO.Show-Prefix")) {
            sender.sendMessage(parseColor(prefix
                    + translate("Command.ManyArgs")));
            sender.sendMessage(parseColor(prefix
                    + translate("Command.Usage").replaceAll("%usage%", usage)));
        } else {
            sender.sendMessage(parseColor(translate("Command.ManyArgs")));
            sender.sendMessage(parseColor(translate("Command.Usage")
                    .replaceAll("%usage%", usage)));
        }
    }

    public void sendManyArgs(CommandSender sender, String usage,
            boolean showPrefix) {
        if (showPrefix) {
            sender.sendMessage(parseColor(prefix
                    + translate("Command.ManyArgs")));
            sender.sendMessage(parseColor(prefix
                    + translate("Command.Usage").replaceAll("%usage%", usage)));
        } else {
            sender.sendMessage(parseColor(translate("Command.ManyArgs")));
            sender.sendMessage(parseColor(translate("Command.Usage")
                    .replaceAll("%usage%", usage)));
        }
    }

    public void sendConversable(Conversable conversable, String msg) {
        if (config.getBoolean("IO.Show-Prefix")) conversable
                .sendRawMessage(parseColor(prefix + msg));
        else conversable.sendRawMessage(parseColor(msg));
    }

    public void sendConversable(Conversable conversable, String msg,
            boolean showPrefix) {
        if (showPrefix) conversable.sendRawMessage(parseColor(prefix + msg));
        else conversable.sendRawMessage(parseColor(msg));
    }

    public void createConversation(CommandSender sender, String name,
            Prompt prompt) {
        conversationFactory.withEscapeSequence("/quit");
        conversationFactory.withFirstPrompt(prompt);
        conversationFactory.withTimeout(10);
        conversations.put(name,
                conversationFactory.buildConversation((Conversable) sender));
        conversations.get(name).begin();
    }

    public void createConversation(CommandSender sender, String name,
            Prompt prompt, String escapeSequence) {
        conversationFactory.withEscapeSequence(escapeSequence);
        conversationFactory.withFirstPrompt(prompt);
        conversationFactory.withTimeout(10);
        conversations.put(name,
                conversationFactory.buildConversation((Conversable) sender));
        conversations.get(name).begin();
    }

    public void createConversation(CommandSender sender, String name,
            Prompt prompt, int timeout) {
        conversationFactory.withEscapeSequence("/quit");
        conversationFactory.withFirstPrompt(prompt);
        conversationFactory.withTimeout(timeout);
        conversations.put(name,
                conversationFactory.buildConversation((Conversable) sender));
        conversations.get(name).begin();
    }

    public void createConversation(CommandSender sender, String name,
            Prompt prompt, String escapeSequence, int timeout) {
        conversationFactory.withEscapeSequence(escapeSequence);
        conversationFactory.withFirstPrompt(prompt);
        conversationFactory.withTimeout(timeout);
        conversations.put(name,
                conversationFactory.buildConversation((Conversable) sender));
        conversations.get(name).begin();
    }

    public Conversation getConversation(String name) {
        return conversations.get(name);
    }

    public String translate(String key) {
        return translator.getKey(key);
    }

    public Translator getTranslator() {
        return translator;
    }

    public String color(String msg) {
        if (color) return parseColor(msg);
        else return remColor(msg);
    }

    public static String parseColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String remColor(String message) {
        return ChatColor.stripColor(message);
    }

    public static String formatHeader(String title) {
        int length = (55 - title.length()) / 2 - 1;
        String lines = "";
        for (int i = 0; i < length; i++)
            lines += "-";
        if (title.length() + 2 * lines.length() != 53) title = "&e-" + lines
                + " &f" + title + " &e" + lines;
        else title = "&e" + lines + " &f" + title + " &e" + lines;
        return title;
    }
}
