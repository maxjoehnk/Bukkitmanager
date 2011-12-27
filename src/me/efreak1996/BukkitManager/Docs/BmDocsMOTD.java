package me.efreak1996.BukkitManager.Docs;

public class BmDocsMOTD {
	
	protected String file;
	protected String group;
	
	public BmDocsMOTD(String file, String group){
		this.file = file;
		this.group = group;
	}
	
	public String getFile(){
		return file;
	}
	
	public String getGroup(){
		return group;
	}
}
