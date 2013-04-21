package org.efreak.bukkitmanager.tutorials;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.IOManager;

public abstract class TutorialAction {
	
	private boolean sendMsg;
	private String msg;
	private static IOManager io;
	
	static {
		io = Bukkitmanager.getIOManager();
	}
	
	public TutorialAction(String content, boolean sendMsg) {
		
	}
	
	public void sendMsg(CommandSender sender) {
		io.send(sender, msg);
	}
}