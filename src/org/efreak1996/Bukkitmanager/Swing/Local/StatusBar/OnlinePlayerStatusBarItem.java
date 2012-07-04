package org.efreak1996.Bukkitmanager.Swing.Local.StatusBar;

import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Swing.Local.GuiObject;

import com.jidesoft.status.LabelStatusBarItem;

public class OnlinePlayerStatusBarItem extends LabelStatusBarItem implements GuiObject {

	private static final long serialVersionUID = 5823792569798957357L;
	
	public OnlinePlayerStatusBarItem() {
		super();
		setText(Bukkitmanager.getInstance().getServer().getOnlinePlayers().length + "/" + Bukkitmanager.getInstance().getServer().getMaxPlayers());
	}

	@Override
	public void update() {
		setText(Bukkitmanager.getInstance().getServer().getOnlinePlayers().length + "/" + Bukkitmanager.getInstance().getServer().getMaxPlayers());
	}
}

