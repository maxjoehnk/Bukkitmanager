package org.efreak.bukkitmanager.pluginmanager;

import java.net.URL;

import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.Downloader;

public class File {

	private String title;
	private URL link;
	private URL downloadLink = null;
	
	public File(String title, URL link) {
		this.title = title;
		this.link = link;
	}
	
	public String getName() {
		return title;
	}
	
	public void getDownloadLink() {
		
	}
	
	public java.io.File download() {
		if (downloadLink == null) getDownloadLink();
		return Downloader.fetch(false, downloadLink.toString(), downloadLink.toString().split("/")[downloadLink.toString().split("/").length - 1], PluginManager.getUpdateFolder());
	}
	
}
