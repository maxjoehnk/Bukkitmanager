package org.efreak1996.Bukkitmanager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import org.efreak1996.Bukkitmanager.Swing.Swing;
import org.efreak1996.Bukkitmanager.Swing.Local.GuiObject;


public class WindowsMenu extends JMenu implements GuiObject {
		
	JCheckBoxMenuItem playerListItem;
	JCheckBoxMenuItem onlinePlayerListItem;
	JCheckBoxMenuItem offlinePlayerListItem;
	JCheckBoxMenuItem pluginListItem;
	JCheckBoxMenuItem consoleItem;
	
	public WindowsMenu() {
		super("Windows");
		//Playerlist Item
		playerListItem = new JCheckBoxMenuItem("Player List");
		playerListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerListItem.isSelected()) Swing.getLocalMainGui().show("PlayerList");
				else Swing.getLocalMainGui().hide("PlayerList");
			}
		});
		add(playerListItem);
		
		//Online Playerlist Item
		onlinePlayerListItem = new JCheckBoxMenuItem("Online Player List");
		onlinePlayerListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (onlinePlayerListItem.isSelected()) Swing.getLocalMainGui().show("OnlinePlayerList");
				else Swing.getLocalMainGui().hide("OnlinePlayerList");
			}
		});
		add(onlinePlayerListItem);
		
		//Offline Playerlist Item
		offlinePlayerListItem = new JCheckBoxMenuItem("Offline Player List");
		offlinePlayerListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (offlinePlayerListItem.isSelected()) Swing.getLocalMainGui().show("OfflinePlayerList");
				else Swing.getLocalMainGui().hide("OfflinePlayerList");
			}
		});
		add(offlinePlayerListItem);
		
		//Pluginlist Item
		pluginListItem = new JCheckBoxMenuItem("Plugin List");
		pluginListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pluginListItem.isSelected()) Swing.getLocalMainGui().show("PluginList");
				else Swing.getLocalMainGui().hide("PluginList");
			}
		});
		add(pluginListItem);
		
		//Console Item
		consoleItem = new JCheckBoxMenuItem("Console");
		consoleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (consoleItem.isSelected()) Swing.getLocalMainGui().show("Console");
				else Swing.getLocalMainGui().hide("Console");
		}
		});
		add(consoleItem);
	}

	@Override
	public void update() {
		if (Swing.getLocalMainGui().isVisible("PlayerList")) playerListItem.setSelected(true);
		else playerListItem.setSelected(false);
		if (Swing.getLocalMainGui().isVisible("OnlinePlayerList")) onlinePlayerListItem.setSelected(true);
		else onlinePlayerListItem.setSelected(false);
		if (Swing.getLocalMainGui().isVisible("OfflinePlayerList")) offlinePlayerListItem.setSelected(true);
		else offlinePlayerListItem.setSelected(false);
		if (Swing.getLocalMainGui().isVisible("Console")) consoleItem.setSelected(true);
		else consoleItem.setSelected(false);
		if (Swing.getLocalMainGui().isVisible("PluginList")) pluginListItem.setSelected(true);
		else pluginListItem.setSelected(false);	}
}
