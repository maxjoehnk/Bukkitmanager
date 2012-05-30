package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockIgniteHandler extends BmBlockHandler {

	public BlockIgniteHandler(BlockIgniteLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockIgnite.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockIgnite.File"), BmLoggerConfiguration.get("Block.BlockIgnite.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockIgnite");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockIgniteEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
