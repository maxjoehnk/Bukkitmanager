package com.efreak1996.BukkitManager.Swing.Local.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.efreak1996.BukkitManager.Swing.Local.AboutDialog;
import java.awt.Component;
import javax.swing.Box;

public class HelpMenu extends JMenu {
			
	JMenuItem helpItem;
	JMenuItem searchItem;
	JMenuItem dynamicHelpItem;
	JMenuItem aboutItem;
	JMenuItem welcomeItem;
	
	public HelpMenu() {
		super("Help");
		
		//Welcome Item
		welcomeItem = new JMenuItem("Welcome");
		welcomeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(welcomeItem);
		
		//Placeholder
		Component verticalStrut1 = Box.createVerticalStrut(5);
		add(verticalStrut1);
		
		//Help Item
		helpItem = new JMenuItem("Help");
		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(helpItem);
		
		//Search Item
		searchItem = new JMenuItem("Search");
		searchItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(searchItem);
		
		//Dynamic Help Item
		dynamicHelpItem = new JMenuItem("Dynamic Help");
		dynamicHelpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(dynamicHelpItem);
		
		//Placeholder
		Component verticalStrut2 = Box.createVerticalStrut(5);
		add(verticalStrut2);
		
		//About Item
		aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog dialog = new AboutDialog();
				dialog.setVisible(true);
			}
		});
		add(aboutItem);
	}
	
}
