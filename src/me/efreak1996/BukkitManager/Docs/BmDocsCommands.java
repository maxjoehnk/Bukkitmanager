package me.efreak1996.BukkitManager.Docs;

public class BmDocsCommands {
	
	protected String command;
	protected String file;
	protected String group;

	public BmDocsCommands(String command, String file, String group){
		this.command = command;
		this.file = file;
		this.group = group;
	}
	
	public String getCommand(){
		return command;
	}
	
	public String getFile(){
		return file;
	}
	
	public String getGroup(){
		return group;
	}
}
