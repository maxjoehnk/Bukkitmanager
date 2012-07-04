package org.efreak1996.Bukkitmanager.Swing.Local.Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Swing.Local.GuiObject;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.swing.JideScrollPane;

public class OnlinePlayerListFrame extends DockableFrame implements GuiObject {

	private static final long serialVersionUID = 2163375885879319620L;
	private static JTable playerList;
	private static DefaultTableModel model;
	public static Object[][] tabledata = {};
	private static String[] columnNames = {"Playername", "IP"};
	private static LabelStatusBarItem playerLabel;
	private static StatusBar statusBar;
	
	public OnlinePlayerListFrame() {
		setKey("OnlinePlayerList");
		setPreferredSize(new Dimension(200, 200));
        getContext().setInitMode(DockContext.STATE_AUTOHIDE);
        getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        getContext().setInitIndex(1);
        setTitle("Online Player List");
        setTabTitle("Online Player List");
        setToolTipText("Shows all Online Players with some Infos");
        playerList = new JTable();
        model = new DefaultTableModel(tabledata, columnNames) {
			private static final long serialVersionUID = -5355380459192240245L;
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
		Server server = Bukkitmanager.getInstance().getServer();
		Player[] players = server.getOnlinePlayers();
		Object[][] newTabledata = new Object[players.length][model.getColumnCount()];
		for (int onlinePlayers = 0; onlinePlayers < players.length; onlinePlayers++) {
			for (int columns = 0; columns < model.getColumnCount(); columns++) {
				if (model.getColumnName(columns).equalsIgnoreCase("playername")) newTabledata[onlinePlayers][columns] = players[onlinePlayers].getName();
				else if (model.getColumnName(columns).equalsIgnoreCase("ip")) newTabledata[onlinePlayers][columns] = players[onlinePlayers].getAddress().getHostName();
			}
		}
		tabledata = newTabledata;
        updateModel();
		playerLabel.setText(players.length + " Player(s) online!");
	}
}
