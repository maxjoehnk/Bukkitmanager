package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.BmLoggerConfiguration;


public class EntityBlockFormHandler extends BmBlockHandler {

	public EntityBlockFormHandler(EntityBlockFormLogger arg1logger) {
		super(new File("Block" + File.separator + "EntityBlockForm.log"), arg1logger, BmLoggerConfiguration.get("Block.EntityBlockForm.File"), BmLoggerConfiguration.get("Block.EntityBlockForm.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.EntityBlockForm");
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_EntityBlockFormEvent", "time, block, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
