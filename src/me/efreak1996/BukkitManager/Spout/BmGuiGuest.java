package me.efreak1996.BukkitManager.Spout;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class BmGuiGuest {

	public static void show(Player p) {
		SpoutPlayer splayer = (SpoutPlayer)p;
	    GenericPopup gui = new GenericPopup();
	    header();
	    gui.attachWidget(null,  headerContainer);
	    splayer.getMainScreen().attachPopupScreen(gui);
	}
	
	public static void header(GenericButton button, String tip, WidgetAnchor anchor, int shift) {
		button.setWidth(75);
		button.setHeight(14);
		button.setAuto(true);
		button.setAnchor(anchor);
		button.shiftYPos(shift);
		button.setAuto(true);
		button.setTooltip(tip);
		}
	public static void header() {
		headerContainer = new GenericContainer();
		GenericButton spawn = new GenericButton("Spawn");
		header(spawn, "Teleports you to spawn", WidgetAnchor.TOP_CENTER, 14);
	    headerContainer.addChildren(spawn);
	    headerContainer.setAuto(true);
	}
	
	static GenericContainer headerContainer;
	public static GenericLabel headerLabel;

}
