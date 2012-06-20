package org.efreak1996.Bukkitmanager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import org.efreak1996.Bukkitmanager.Swing.BmSwing;
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
				if (playerListItem.isSelected()) BmSwing.getLocalMainGui().show("PlayerList");
				else BmSwing.getLocalMainGui().hide("PlayerList");
			}
		});
		add(playerListItem);
		
		//Online Playerlist Item
		onlinePlayerListItem = new JCheckBoxMenuItem("Online Player List");
		onlinePlayerListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (onlinePlayerListItem.isSelected()) BmSwing.getLocalMainGui().show("OnlinePlayerList");
				else BmSwing.getLocalMainGui().hide("OnlinePlayerList");
			}
		});
		add(onlinePlayerListItem);
		
		//Offline Playerlist Item
		offlinePlayerListItem = new JCheckBoxMenuItem("Offline Player List");
		offlinePlayerListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (offlinePlayerListItem.isSelected()) BmSwing.getLocalMainGui().show("OfflinePlayerList");
				else BmSwing.getLocalMainGui().hide("OfflinePlayerList");
			}
		});
		add(offlinePlayerListItem);
		
		//Pluginlist Item
		pluginListItem = new JCheckBoxMenuItem("Plugin List");
		pluginListItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pluginListItem.isSelected()) BmSwing.getLocalMainGui().show("PluginList");
				else BmSwing.getLocalMainGui().hide("PluginList");
			}
		});
		add(pluginListItem);
		
		//Console Item
		consoleItem = new JCheckBoxMenuItem("Console");
		consoleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (consoleItem.isSelected()) BmSwing.getLocalMainGui().show("Console");
				else BmSwing.getLocalMainGui().hide("Console");
		}
		});
		add(consoleItem);
	}

	@Override
	public void update() {
		if (BmSwing.getLocalMainGui().isVisible("PlayerList")) playerListItem.setSelected(true);
		else playerListItem.setSelected(false);
		if (BmSwing.getLocalMainGui().isVisible("OnlinePlayerList")) onlinePlayerListItem.setSelected(true);
		else onlinePlayerListItem.setSelected(false);
		if (BmSwing.getLocalMainGui().isVisible("OfflinePlayerList")) offlinePlayerListItem.setSelected(true);
		else offlinePlayerListItem.setSelected(false);
		if (BmSwing.getLocalMainGui().isVisible("Console")) consoleItem.setSelected(true);
		else consoleItem.setSelected(false);
		if (BmSwing.getLocalMainGui().isVisible("PluginList")) pluginListItem.setSelected(true);
		else pluginListItem.setSelected(false);	}
}
