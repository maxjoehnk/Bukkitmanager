package com.efreak1996.BukkitManager.Swing.Local.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.Swing.Local.GuiObject;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.grid.JideTable;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.swing.JideScrollPane;

public class PlayerListFrame extends DockableFrame implements GuiObject {

	private static JideTable playerList;
	private static DefaultTableModel model;
	public static Object[][] tabledata = {};
	private static String[] columnNames = {"Playername", "Online"};
	private static LabelStatusBarItem playerLabel;
	private static StatusBar statusBar;
	
	public PlayerListFrame() {
		setKey("PlayerList");
		setPreferredSize(new Dimension(200, 200));
        getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        getContext().setInitIndex(1);
        setTitle("Player List");
        setTabTitle("Player List");
        setToolTipText("Shows all Players with some Infos");
        playerList = new JideTable();
        model = new DefaultTableModel(tabledata, columnNames) {
        	private static final long serialVersionUID = -3014451759943467258L;
		};
		playerList.setModel(model);
        JScrollPane pane = new JideScrollPane(playerList);
        pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane);
        
        statusBar = new StatusBar();
        playerLabel = new LabelStatusBarItem();
        playerLabel.setText("0 Player(s) online!");
        statusBar.add(playerLabel);
        add(statusBar, BorderLayout.AFTER_LAST_LINE);
	}
	
	public void updateTable() {
		model.fireTableDataChanged();
		model.fireTableStructureChanged();
	}
	
	public void updateModel() {
        model = new DefaultTableModel(tabledata, columnNames) {};
        playerList.setModel(model);
		updateTable();
	}
	
	public void updateVars() {
		Object[][] newTabledata = new Object[model.getRowCount()][model.getColumnCount()];
		for (int rows = 0; rows < model.getRowCount(); rows++) {
			for (int columns = 0; columns < model.getColumnCount(); columns++) newTabledata[rows][columns] = model.getValueAt(rows, columns);
		}
		String[] newColumnNames = new String[model.getColumnCount()];
		for (int columns = 0; columns < model.getColumnCount(); columns++) newColumnNames[columns] = model.getColumnName(columns);
		tabledata = newTabledata;
		columnNames = newColumnNames;
		updateTable();
	}
	
	public void addPlayer(Object[] infos) {
		model.addRow(infos);
		updateTable();
	}
	
	public void removePlayer(String name) {
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(name)) model.removeRow(i);
		}
		updateTable();
	}

	@Override
	public void update() {
		Server server = BmPlugin.getPlugin().getServer();
		OfflinePlayer[] players = server.getOfflinePlayers();
		Object[][] newTabledata = new Object[players.length][model.getColumnCount()];
		for (int player = 0; player < players.length; player++) {
			for (int columns = 0; columns < model.getColumnCount(); columns++) {
				if (model.getColumnName(columns).equalsIgnoreCase("playername")) newTabledata[player][columns] = players[player].getName();
				else if (model.getColumnName(columns).equalsIgnoreCase("online")) newTabledata[player][columns] = players[player].isOnline();
			}
		}
		tabledata = newTabledata;
        updateModel();
		playerLabel.setText(server.getOnlinePlayers().length + " Player(s) online!");
	}
}
