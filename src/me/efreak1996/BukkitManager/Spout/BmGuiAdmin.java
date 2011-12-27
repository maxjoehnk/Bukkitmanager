package me.efreak1996.BukkitManager.Spout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class BmGuiAdmin {

	public static void show(Player p) {
		splayer = (SpoutPlayer)p;
	    GenericPopup gui = new GenericPopup();
	    header();
	    general();
	    headerLabel = new GenericLabel("BukkitManager Admin Gui - General");
	    headerLabel.setAlign(WidgetAnchor.TOP_CENTER);
	    headerLabel.setAnchor(WidgetAnchor.TOP_CENTER);
	    headerLabel.shiftYPos(4);
	    headerLabel.setFixed(false);
	    headerLabel.setAuto(true);
	    gui.attachWidget(null, headerLabel);
	    gui.attachWidget(null,  headerContainer);
	    gui.attachWidget(null, generalContainer);
	    splayer.getMainScreen().attachPopupScreen(gui);
	}
	
	public static void header(GenericButton button, String tip) {
		button.setWidth(75);
		button.setHeight(14);
		button.setAuto(true);
		button.setAnchor(WidgetAnchor.TOP_LEFT);
		button.shiftYPos(14);
		button.setAuto(true);
		button.setTooltip(tip);
		}
	public static void header() {
		GenericButton general = new GenericButton("General");
		GenericButton worldEdit = new GenericButton("WorldEdit");
	    GenericButton worldGuard = new GenericButton("WorldGuard");
	    GenericButton commandBook = new GenericButton("CommandBook");
	    GenericButton commandHelper = new GenericButton("CommandHelper");
	    GenericButton craftBook = new GenericButton("CraftBook");
	    GenericButton lwc = new GenericButton("LWC");
	    GenericButton iConomy = new GenericButton("iConomy");
	    GenericButton permissions = new GenericButton("Permissions");
	    GenericButton spout = new GenericButton("Spout");
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null) {            
			worldEdit.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) {            
			worldGuard.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("CommandBook") == null) {            
			commandBook.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("CommandHelper") == null) {            
			commandHelper.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("CraftBookCommon") == null) {            
			craftBook.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("LWC") == null) {            
			lwc.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("iConomy") == null) {            
			iConomy.setEnabled(false);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit") == null) {            
			permissions.setEnabled(false);
		}
		general.setFocus(true);
	    header(worldEdit, "Functions of WorldEdit");
	    worldEdit.shiftYPos(14);
	    header(worldGuard, "Functions of WorldGuard");
	    worldGuard.shiftXPos(75);
	    worldGuard.shiftYPos(14);
	    header(commandBook, "Functions of CommandBook");
	    commandBook.shiftXPos(150);
	    commandBook.shiftYPos(14);
	    header(commandHelper, "Functions of CommandHelper");
	    commandHelper.shiftXPos(225);
	    commandHelper.shiftYPos(14);
	    header(craftBook, "Functions of CraftBook");
	    craftBook.shiftXPos(300);
	    craftBook.shiftYPos(14);
	    header(general, "The internal Functions");
	    header(lwc, "Functions of LWC");
	    lwc.shiftXPos(75);
	    header(iConomy, "Functions of iConomy");
	    iConomy.shiftXPos(150);
	    header(permissions, "Functions of PermissionsBukkit");
	    permissions.shiftXPos(225);
	    header(spout, "Functions of Spout");
	    spout.shiftXPos(300);
	    headerContainer = new GenericContainer();
	    headerContainer.addChildren(worldEdit, worldGuard, commandBook, commandHelper, craftBook, lwc, iConomy, permissions, general, spout);
	    headerContainer.setAuto(true);
	}
	
	public static void general() {
		//headerLabel.setText("BukkitManager Admin Gui - General");
		GenericButton armor = new GenericButton("Armor Bar: ON");
		GenericButton bubble = new GenericButton("Bubble Bar: ON");
		GenericButton health = new GenericButton("Health Bar: ON");
		general(armor, "Enable/Disable The Armor Bar");
		general(bubble, "Enable/Disable The Bubble Bar");
		bubble.shiftYPos(14);
		general(health, "Enable/Disable The Health Bar");
		health.shiftYPos(28);
		generalContainer = new GenericContainer();
		generalContainer.addChildren(armor, bubble, health);
		generalContainer.setAuto(true);
		generalContainer.setAnchor(WidgetAnchor.CENTER_CENTER);
		//updateGUI();
	}
	public static void general(GenericButton button, String tip) {
		button.setWidth(80);
		button.setHeight(14);
		button.setAuto(true);
		button.setAnchor(WidgetAnchor.CENTER_CENTER);
		button.shiftYPos(14);
		button.setAuto(true);
		button.setTooltip(tip);
		}
	
	public static void permissions() {
		headerLabel.setText("BukkitManager Admin Gui - Permissions");
		splayer.sendMessage("");
	}
	
	static GenericContainer headerContainer;
	static GenericContainer generalContainer;
	static GenericLabel headerLabel;
	public static SpoutPlayer splayer;

}
