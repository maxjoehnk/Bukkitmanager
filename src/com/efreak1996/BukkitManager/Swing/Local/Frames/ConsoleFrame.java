package com.efreak1996.BukkitManager.Swing.Local.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.LogRecord;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.efreak1996.BukkitManager.Swing.Local.ConsoleOutputHandler;
import com.efreak1996.BukkitManager.Swing.Local.GuiObject;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.swing.JideScrollPane;

public class ConsoleFrame extends DockableFrame implements GuiObject {

	private static JTextPane output;
	private static JTextField input;
	private static JButton perform;
	
	public ConsoleFrame() {
		setKey("Console");
		setPreferredSize(new Dimension(750, 200));
		getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
		getContext().setInitIndex(1);
		setTitle("Console");
		setTabTitle("Console");
		setToolTipText("Shows the Console Output and allows you to perform Commands");
		output = new JTextPane();
		output.setEditable(false);
		JScrollPane pane = new JideScrollPane(output);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setAutoscrolls(true);
		pane.setWheelScrollingEnabled(true);
		add(pane);
		input = new JTextField();
		input.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
	            if(input.getText().isEmpty()) {
	            	input.setText("");
	                return;
	            }
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), input.getText());
	            input.setText("");
			}
		});
		add(input, BorderLayout.AFTER_LAST_LINE);
	}

	@Override
	public void update() {
		List<LogRecord> logs = ConsoleOutputHandler.logs;
		StringBuilder newOutput = new StringBuilder();
		for (int i = 0; i < logs.size(); i++) newOutput.append(parseLog(logs.get(i)) + "\n");
		output.setText(newOutput.toString());
	}
	
	private String parseLog(LogRecord record) {
		return "[" + record.getLevel().toString() + "] " + record.getMessage();
	}
}
