package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.BmLoggerConfiguration;


public class BlockFormHandler extends BmBlockHandler {

	public BlockFormHandler(BlockFormLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockForm.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockForm.File"), BmLoggerConfiguration.get("Block.BlockForm.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockForm");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockFormEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
