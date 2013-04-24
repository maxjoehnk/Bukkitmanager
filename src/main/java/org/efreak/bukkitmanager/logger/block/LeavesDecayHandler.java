package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;

public class LeavesDecayHandler extends BlockHandler {

    public LeavesDecayHandler(LeavesDecayLogger arg1logger) {
        super(new File("Block" + File.separator + "LeavesDecay.log"),
                arg1logger, LoggerConfiguration.get("Block.LeavesDecay.File"),
                LoggerConfiguration.get("Block.LeavesDecay.Database"));
    }

    public String logFile(HashMap<String, Object> values) {
        String msg = io.translate("Logger.Block.LeavesDecay");
        return msg;
    }

    @Override
    public void logDb(HashMap<String, Object> values) {
        db.log("Log_LeavesDecayEvent",
                "time, block, cancelled",
                "'" + new Date().toGMTString() + "', '"
                        + (Block) values.get("Block") + "', '"
                        + (Boolean) values.get("Cancelled") + "'");
    }
}
