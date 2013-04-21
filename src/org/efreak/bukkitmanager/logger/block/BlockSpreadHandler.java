package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;


public class BlockSpreadHandler extends BlockHandler {

	public BlockSpreadHandler(BlockSpreadLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockSpread.log"), arg1logger, LoggerConfiguration.get("Block.BlockSpread.File"), LoggerConfiguration.get("Block.BlockSpread.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockSpread");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockSpreadEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
