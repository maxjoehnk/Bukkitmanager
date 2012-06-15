package com.efreak1996.BukkitManager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.efreak1996.BukkitManager.Swing.BmSwing;
import com.efreak1996.BukkitManager.Swing.Local.SettingsGui;
import java.awt.Component;
import javax.swing.Box;

public class FileMenu extends JMenu {
		
	JMenuItem settingsItem;
	JMenuItem exitItem;
	
	public FileMenu() {
		super("File");
		
		
		
		//Settings Item
		settingsItem = new JMenuItem("Settings");
		settingsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingsGui().initialize();
			}
		});
		add(settingsItem);
		
		//Placeholder
		Component verticalStrut = Box.createVerticalStrut(5);
		add(verticalStrut);
		
		//Exit Item
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BmSwing.closeLocalGui();
			}
		});
		add(exitItem);
	}
	
}
