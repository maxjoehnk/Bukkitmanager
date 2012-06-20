package org.efreak1996.Bukkitmanager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Component;
import javax.swing.Box;

import org.bukkit.Bukkit;

public class ServerActionsMenu extends JMenu {
			
	JMenuItem stopServerItem;
	JMenuItem restartServerItem;
	JMenuItem reloadServerItem;
	JMenuItem saveAllItem;
	JMenuItem saveOnItem;
	JMenuItem saveOffItem;
	
	public ServerActionsMenu() {
		super("Server Actions");
		
		//Stop Server Item
		stopServerItem = new JMenuItem("Stop Server");
		stopServerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bukkit.shutdown();
			}
		});
		add(stopServerItem);

		//Restart Server Item
		restartServerItem = new JMenuItem("Restart Server");
		restartServerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		restartServerItem.setEnabled(false);
		add(restartServerItem);
		
		//Reload Server Item
		reloadServerItem = new JMenuItem("Reload Server");
		reloadServerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bukkit.reload();
			}
		});
		add(reloadServerItem);
		
		//Placeholder
		Component verticalStrut1 = Box.createVerticalStrut(5);
		add(verticalStrut1);
		
		//Save-All Item
		saveAllItem = new JMenuItem("Save-All");
		saveAllItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");
			}
		});
		add(saveAllItem);

		//Save-On Item
		saveOnItem = new JMenuItem("Save-On");
		saveOnItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-on");
			}
		});
		add(saveOnItem);
		
		//Save-Off Item
		saveOffItem = new JMenuItem("Save-Off");
		saveOffItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-off");
			}
		});
		add(saveOffItem);

	}
	
}
