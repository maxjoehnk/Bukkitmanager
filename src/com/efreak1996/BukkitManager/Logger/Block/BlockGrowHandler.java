package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockGrowHandler extends BmBlockHandler {

	public BlockGrowHandler(BlockGrowLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockGrow.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockGrow.File"), BmLoggerConfiguration.get("Block.BlockGrow.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockGrow");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockGrowEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
