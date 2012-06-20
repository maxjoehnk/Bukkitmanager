package org.efreak1996.Bukkitmanager.Swing.Local.Frames;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.PluginManager.PluginManager;
import org.efreak1996.Bukkitmanager.Swing.Local.GuiObject;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.grid.JideTable;
import com.jidesoft.swing.JideScrollPane;

public class PluginListFrame extends DockableFrame implements GuiObject {

	private static JideTable pluginList;
	private static DefaultTableModel model;
	public static Object[][] tabledata = {};
	private static String[] columnNames = {"Pluginname", "Version", "Enabled", "Description"};
	
	public PluginListFrame() {
		setKey("PluginList");
		setPreferredSize(new Dimension(200, 200));
        getContext().setInitMode(DockContext.STATE_HIDDEN);
        getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
        getContext().setInitIndex(1);
        setTitle("Plugin List");
        setTabTitle("Plugin List");
        setToolTipText("Shows all Plugins with some Infos");
        pluginList = new JideTable();
        model = new DefaultTableModel(tabledata, columnNames) {
		};
		pluginList.setModel(model);
        JScrollPane pane = new JideScrollPane(pluginList);
        pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane);
	}
	
	public void updateTable() {
		model.fireTableDataChanged();
		model.fireTableStructureChanged();
	}
	
	public void updateModel() {
        model = new DefaultTableModel(tabledata, columnNames) {};
        pluginList.setModel(model);
		updateTable();
	}
	
	public void addPlugin(Object[] infos) {
		model.addRow(infos);
		updateTable();
	}
	
	public void removePlugin(String name) {
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(name)) model.removeRow(i);
		}
		updateTable();
	}

	@Override
	public void update() {
		PluginManager pm = BmPlugin.getPluginManager();
		Plugin[] plugins = pm.getPlugins();
		Object[][] newTabledata = new Object[plugins.length][model.getColumnCount()];
		for (int i = 0; i < plugins.length; i++) {
			for (int columns = 0; columns < model.getColumnCount(); columns++) {
				if (model.getColumnName(columns).equalsIgnoreCase("pluginname")) newTabledata[i][columns] = plugins[i].getName();
				else if (model.getColumnName(columns).equalsIgnoreCase("version")) newTabledata[i][columns] = plugins[i].getDescription().getVersion();
				else if (model.getColumnName(columns).equalsIgnoreCase("enabled")) newTabledata[i][columns] = plugins[i].isEnabled();
				else if (model.getColumnName(columns).equalsIgnoreCase("description")) newTabledata[i][columns] = plugins[i].getDescription().getDescription();
			}
		}
		tabledata = newTabledata;
        updateModel();
	}
}
