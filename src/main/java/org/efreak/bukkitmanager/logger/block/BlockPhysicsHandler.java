package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;

public class BlockPhysicsHandler extends BlockHandler {

    public BlockPhysicsHandler(BlockPhysicsLogger arg1logger) {
        super(new File("Block" + File.separator + "BlockPhysics.log"),
                arg1logger, LoggerConfiguration.get("Block.BlockPhysics.File"),
                LoggerConfiguration.get("Block.BlockPhysics.Database"));
    }

    public String logFile(HashMap<String, Object> values) {
        String msg = io.translate("Logger.Block.BlockPhysics");
        return msg;
    }

    @Override
    public void logDb(HashMap<String, Object> values) {
        db.log("Log_BlockPhysicsEvent",
                "time, block, cancelled",
                "'" + new Date().toGMTString() + "', '"
                        + (Block) values.get("Block") + "', '"
                        + (Boolean) values.get("Cancelled") + "'");
    }
}
