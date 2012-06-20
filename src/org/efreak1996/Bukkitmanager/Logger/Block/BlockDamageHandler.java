package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.LoggerConfiguration;


public class BlockDamageHandler extends BlockHandler {

	public BlockDamageHandler(BlockDamageLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockDamage.log"), arg1logger, LoggerConfiguration.get("Block.BlockDamage.File"), LoggerConfiguration.get("Block.BlockDamage.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockDamage");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockDamageEvent", "time, block, cancelled, player, instaBreak, itemInHand", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");				
	}
}
