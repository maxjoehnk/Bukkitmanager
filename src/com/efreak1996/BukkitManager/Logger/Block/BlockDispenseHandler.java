package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockDispenseHandler extends BmBlockHandler {

	public BlockDispenseHandler(BlockDispenseLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockDispense.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockDispense.File"), BmLoggerConfiguration.get("Block.BlockDispense.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockDispense");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockDispenseEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
