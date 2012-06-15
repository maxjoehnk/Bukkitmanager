package com.efreak1996.BukkitManager.Swing.Local;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.jidesoft.dialog.PageList;

public class SettingsGui {

	PageList pages;
	
	public SettingsGui() {
		
	}
	
	public void initialize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					initGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initGui() {
		
	}
	
}
