package org.efreak1996.Bukkitmanager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Component;
import javax.swing.Box;

import org.efreak1996.Bukkitmanager.Swing.Swing;
import org.efreak1996.Bukkitmanager.Swing.Local.SettingsGui;

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
				Swing.closeLocalGui();
			}
		});
		add(exitItem);
	}
	
}
