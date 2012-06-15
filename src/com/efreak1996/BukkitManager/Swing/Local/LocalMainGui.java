package com.efreak1996.BukkitManager.Swing.Local;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.Swing.BmSwing;
import com.efreak1996.BukkitManager.Swing.Local.Frames.*;
import com.efreak1996.BukkitManager.Swing.Local.MenuBar.*;
import com.efreak1996.BukkitManager.Swing.Local.StatusBar.*;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.status.ResizeStatusBarItem;
import com.jidesoft.status.StatusBar;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

public class LocalMainGui extends DefaultDockableHolder implements GuiObject{
	
	private static BmConfiguration config;
	JMenuBar menuBar;
	FileMenu fileMenu;
	HelpMenu helpMenu;
	WindowsMenu windowsMenu;
	ServerActionsMenu serverActionsMenu;
	
	//Statusbar
	StatusBar statusBar;
	OnlinePlayerStatusBarItem onlinePlayerStatusBarItem;
	
	//Frames
	private static PlayerListFrame playerList;
	private static OnlinePlayerListFrame onlinePlayerList;
	private static OfflinePlayerListFrame offlinePlayerList;
	private static ConsoleFrame console;
	private static PluginListFrame pluginList;
	
	public LocalMainGui(String title) {
		super(title);
	}
	
	public LocalMainGui() {
		config = new BmConfiguration();
	}
	
	public void initialize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					initGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initGui() {
		//Setup Window
		setIconImage(Toolkit.getDefaultToolkit().getImage(LocalMainGui.class.getResource("/img/swing/icon.png")));
		setTitle("Bukkitmanager Gui");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setLayout(new BorderLayout(0, 0));
		
		//Setup Menubar
		menuBar = new JMenuBar();
		fileMenu = new FileMenu();
		menuBar.add(fileMenu);
		serverActionsMenu = new ServerActionsMenu();
		menuBar.add(serverActionsMenu);
		windowsMenu = new WindowsMenu();
		BmSwing.addLocalGuiObject(windowsMenu);
		menuBar.add(windowsMenu);
		helpMenu = new HelpMenu();
		menuBar.add(helpMenu);
		getContentPane().add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
		
		//Setup Statusbar
		statusBar = new StatusBar();
		onlinePlayerStatusBarItem = new OnlinePlayerStatusBarItem();
		BmSwing.addLocalGuiObject(onlinePlayerStatusBarItem);
		statusBar.add(onlinePlayerStatusBarItem);
		statusBar.add(new ResizeStatusBarItem(), BorderLayout.LINE_END);
		getContentPane().add(statusBar, BorderLayout.AFTER_LAST_LINE);
		
		//Setup DockingManager
		getDockingManager().setXmlFormat(true);
		getDockingManager().setProfileKey(System.getProperty("user.name"));
		getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);
		getDockingManager().beginLoadLayoutData();
		getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);
		
		//Add Frames
		//Playerlist
		playerList = new PlayerListFrame();
		getDockingManager().addFrame(playerList);
		BmSwing.addLocalGuiObject(playerList);
		//Online Playerlist
		onlinePlayerList = new OnlinePlayerListFrame();
		getDockingManager().addFrame(onlinePlayerList);
		BmSwing.addLocalGuiObject(onlinePlayerList);
		//Offline Playerlist
		offlinePlayerList = new OfflinePlayerListFrame();
		getDockingManager().addFrame(offlinePlayerList);
		BmSwing.addLocalGuiObject(offlinePlayerList);
		//Console
		console = new ConsoleFrame();
		getDockingManager().addFrame(console);
		BmSwing.addLocalGuiObject(console);
		//Pluginlist
		pluginList = new PluginListFrame();
		getDockingManager().addFrame(pluginList);
		BmSwing.addLocalGuiObject(pluginList);
				
		//Setup Workspace
		getDockingManager().getWorkspace().setAcceptDockableFrame(true);
		getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);
		
		//Load Layout
		getDockingManager().loadLayoutData();
	}
	
	public PlayerListFrame getPlayerListFrame() {
		return playerList;
	}
	
	public OnlinePlayerListFrame getOnlinePlayerListFrame() {
		return onlinePlayerList;
	}
	
	public OfflinePlayerListFrame getOfflinePlayerListFrame() {
		return offlinePlayerList;
	}

	public void open() {
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
	}

	public void hide(String key) {
		getDockingManager().hideFrame(key);
	}
	
	public void show(String key) {
		getDockingManager().showFrame(key);
	}
	
	public void notify(String key) {
		getDockingManager().notifyFrame(key);
	}
	
	public void denotify(String key) {
		getDockingManager().denotifyFrame(key);
	}
	
	public boolean isVisible(String key) {
		return getDockingManager().getFrame(key).isVisible();
	}
	
	@Override
	public void update() {}
}
