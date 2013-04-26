package org.efreak.bukkitmanager.remoteserver;

public class ReturnCodes {

	public static String echo400() {
		return "{\"Result\":\"400\"}";
	}
	
	public static String echo401() {
		return "{\"Result\":\"401\"}";
	}
	
	public static String echo404() {
		return "{\"Result\":\"404\"}";
	}

	public static String echo405() {
		return "{\"Result\":\"405\"}";
	}

	public static String echo500() {
		return "{\"Result\":\"500\"}";
	}
	
	public static String echo200() {
		return "{\"Result\":\"200\"}";
	}
}
