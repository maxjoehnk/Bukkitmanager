package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Logger.LoggerConfiguration;


public class BlockBreakHandler extends BlockHandler {

	public BlockBreakHandler(BlockBreakLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockBreak.log"), arg1logger, LoggerConfiguration.get("Block.BlockBreak.File"), LoggerConfiguration.get("Block.BlockBreak.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockBreak");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockBreakEvent", "time, block, cancelled, player", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "', '" + (Player) values.get("Player") + "'");
	}
}
