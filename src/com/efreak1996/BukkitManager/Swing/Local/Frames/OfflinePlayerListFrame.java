package com.efreak1996.BukkitManager.Swing.Local.Frames;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.Swing.Local.GuiObject;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.grid.JideTable;
import com.jidesoft.swing.JideScrollPane;

public class OfflinePlayerListFrame extends DockableFrame implements GuiObject {

	private static JideTable playerList;
	private static DefaultTableModel model;
	public static Object[][] tabledata = {};
	private static String[] columnNames = {"Playername"};
	
	public OfflinePlayerListFrame() {
		setKey("OfflinePlayerList");
		setPreferredSize(new Dimension(200, 200));
        getContext().setInitMode(DockContext.STATE_AUTOHIDE);
        getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        getContext().setInitIndex(1);
        setTitle("Offline Player List");
        setTabTitle("Offline Player List");
        setToolTipText("Shows all Offline Players with some Infos");
        playerList = new JideTable();
        model = new DefaultTableModel(tabledata, columnNames) {
        	private static final long serialVersionUID = -3014451759943467258L;
		};
		playerList.setModel(model);
        JScrollPane pane = new JideScrollPane(playerList);
        pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane);
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
			if (!players[player].isOnline()) {
				for (int columns = 0; columns < model.getColumnCount(); columns++) {
					if (model.getColumnName(columns).equalsIgnoreCase("playername")) newTabledata[player][columns] = players[player].getName();
				}
			}
		}
		tabledata = newTabledata;
        updateModel();
	}
}
