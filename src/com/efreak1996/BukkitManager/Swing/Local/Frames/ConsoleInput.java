package com.efreak1996.BukkitManager.Swing.Local.Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.bukkit.Bukkit;

public class ConsoleInput extends JTextField {
	
	public ConsoleInput() {
		super();
		addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
	            if(getText().isEmpty()) {
	                setText("");
	                return;
	            }
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getText());
			}
		});
	}
	
	
}
