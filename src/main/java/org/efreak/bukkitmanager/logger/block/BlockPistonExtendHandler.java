package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;


public class BlockPistonExtendHandler extends BlockHandler {

	public BlockPistonExtendHandler(BlockPistonExtendLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockPistonExtend.log"), arg1logger, LoggerConfiguration.get("Block.BlockPistonExtend.File"), LoggerConfiguration.get("Block.BlockPistonExtend.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockPistonExtend");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockPistonExtendEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
