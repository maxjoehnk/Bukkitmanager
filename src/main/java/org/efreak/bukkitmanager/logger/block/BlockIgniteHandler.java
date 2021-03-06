package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;


public class BlockIgniteHandler extends BlockHandler {

	public BlockIgniteHandler(BlockIgniteLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockIgnite.log"), arg1logger, LoggerConfiguration.get("Block.BlockIgnite.File"), LoggerConfiguration.get("Block.BlockIgnite.Database"));
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
