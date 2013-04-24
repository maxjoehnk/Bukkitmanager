package org.efreak.bukkitmanager.logger.block;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.logger.LoggerConfiguration;

public class BlockBurnHandler extends BlockHandler {

    public BlockBurnHandler(BlockBurnLogger arg1logger) {
        super(new File("Block" + File.separator + "BlockBurn.log"), arg1logger,
                LoggerConfiguration.get("Block.BlockBurn.File"),
                LoggerConfiguration.get("Block.BlockBurn.Database"));
    }

    public String logFile(HashMap<String, Object> values) {
        String msg = io.translate("Logger.Block.BlockBurn");
        return msg;
    }

    @Override
    public void logDb(HashMap<String, Object> values) {
        db.log("Log_BlockBurnEvent",
                "time, block, cancelled",
                "'" + new Date().toGMTString() + "', '"
                        + (Block) values.get("Block") + "', '"
                        + (Boolean) values.get("Cancelled") + "'");
    }
}
