package org.efreak1996.Bukkitmanager;

import java.io.File;

import org.efreak1996.Bukkitmanager.Util.Downloader;

public class LibraryManager {

	private static Downloader downloader;

	public static File libFolder = new File(Bukkitmanager.getInstance().getDataFolder(), "lib");
	public static File APACHE_COMMONS_IO = new File(libFolder, "commons-io.jar");
	public static File JIDE_ACTION = new File(libFolder, "jide_action.jar");
	public static File JIDE_COMMON = new File(libFolder, "jide_common.jar");
	public static File JIDE_DOCK = new File(libFolder, "jide_dock.jar");
	public static File JIDE_CHARTS = new File(libFolder, "jide_charts.jar");
	public static File JIDE_COMPONENTS = new File(libFolder, "jide_components.jar");
	public static File JIDE_DATA = new File(libFolder, "jide_data.jar");
	public static File JIDE_DIALOGS = new File(libFolder, "jide_dialogs.jar");
	public static File JIDE_EDITOR = new File(libFolder, "jide_editor.jar");
	public static File JIDE_GRIDS = new File(libFolder, "jide_grids.jar");
	public static File JIDE_PIVOT = new File(libFolder, "jide_pivot.jar");
	public static File JIDE_SHORTCUT = new File(libFolder, "jide_shortcut.jar");
	public static File JSOUP = new File(libFolder, "jsoup.jar");
	public static File H2 = new File(libFolder, "h2.jar");
	
	public void initialize() {
		downloader = new Downloader();
		downloader.initialize();
		libFolder.mkdirs();
		checkLibrarys();
	}
	
	public static void checkLibrarys() {

	}
}
