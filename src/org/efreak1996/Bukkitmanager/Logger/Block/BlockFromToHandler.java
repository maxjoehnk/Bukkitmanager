package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.LoggerConfiguration;


public class BlockFromToHandler extends BlockHandler {

	public BlockFromToHandler(BlockFromToLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockFromTo.log"), arg1logger, LoggerConfiguration.get("Block.BlockFromTo.File"), LoggerConfiguration.get("Block.BlockFromTo.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockFromTo");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockFromToEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
