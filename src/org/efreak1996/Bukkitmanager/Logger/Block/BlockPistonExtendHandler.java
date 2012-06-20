package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.BmLoggerConfiguration;


public class BlockPistonExtendHandler extends BmBlockHandler {

	public BlockPistonExtendHandler(BlockPistonExtendLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockPistonExtend.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockPistonExtend.File"), BmLoggerConfiguration.get("Block.BlockPistonExtend.Database"));
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
