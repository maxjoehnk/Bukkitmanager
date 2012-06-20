package org.efreak1996.Bukkitmanager.Swing.Local.StatusBar;

import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.Swing.Local.GuiObject;

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
