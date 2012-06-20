package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.BmLoggerConfiguration;


public class SignChangeHandler extends BmBlockHandler {

	public SignChangeHandler(SignChangeLogger arg1logger) {
		super(new File("Block" + File.separator + "SignChange.log"), arg1logger, BmLoggerConfiguration.get("Block.SignChange.File"), BmLoggerConfiguration.get("Block.SignChange.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.SignChange");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_SignChangeEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
