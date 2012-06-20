package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.BmLoggerConfiguration;


public class BlockFadeHandler extends BmBlockHandler {

	public BlockFadeHandler(BlockFadeLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockFade.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockFade.File"), BmLoggerConfiguration.get("Block.BlockFade.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockFade");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_BlockFadeEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
