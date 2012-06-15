package com.efreak1996.BukkitManager.Swing.Local.StatusBar;

import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.Swing.Local.GuiObject;
import com.jidesoft.status.LabelStatusBarItem;

public class OnlinePlayerStatusBarItem extends LabelStatusBarItem implements GuiObject {

	private static final long serialVersionUID = 5823792569798957357L;
	
	public OnlinePlayerStatusBarItem() {
		super();
		setText(BmPlugin.getPlugin().getServer().getOnlinePlayers().length + "/" + BmPlugin.getPlugin().getServer().getMaxPlayers());
	}

	@Override
	public void update() {
		setText(BmPlugin.getPlugin().getServer().getOnlinePlayers().length + "/" + BmPlugin.getPlugin().getServer().getMaxPlayers());
	}
}
