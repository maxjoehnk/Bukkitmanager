package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockRedstoneHandler extends BmBlockHandler {

	public BlockRedstoneHandler(BlockRedstoneLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockRedstone.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockRedstone.File"), BmLoggerConfiguration.get("Block.BlockRedstone.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockRedstone");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockRedstoneEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
