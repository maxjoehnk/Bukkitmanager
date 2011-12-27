package me.efreak1996.BukkitManager.Docs;

public class BmDocsOnlineFiles {

	protected long time;
	protected String URL;

	public BmDocsOnlineFiles(long time, String URL){
		this.time = time;
		this.URL = URL;
	}
	
	public long getMs(){
		return time;
	}
	
	public String getURL(){
		return URL;
	}
}
