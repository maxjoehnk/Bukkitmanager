package com.efreak1996.BukkitManager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeLogger extends BmBlockLogger {
	
	public SignChangeLogger() {
		super("SignChange");
	}	

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Lines", event.getLines());
		values.put("Player", event.getPlayer());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new SignChangeHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
