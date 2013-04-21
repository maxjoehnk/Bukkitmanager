package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;


public class BlockRedstoneHandler extends BlockHandler {

	public BlockRedstoneHandler(BlockRedstoneLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockRedstone.log"), arg1logger, LoggerConfiguration.get("Block.BlockRedstone.File"), LoggerConfiguration.get("Block.BlockRedstone.Database"));
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
