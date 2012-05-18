package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class BlockDamageHandler extends BmBlockHandler {

	public BlockDamageHandler(BlockDamageLogger arg1logger) {
		super(new File("Block" + File.separator + "BlockDamage.log"), arg1logger, BmLoggerConfiguration.get("Block.BlockDamage.File"), BmLoggerConfiguration.get("Block.BlockDamage.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Block.BlockDamage").replaceAll("%eventname%", (String) values.get("EventName")).replaceAll("%block_type%", ((Block) values.get("Block")).getType().toString())
				.replaceAll("%block_pos_chunk_x%", String.valueOf(((Block) values.get("Block")).getChunk().getX())).replaceAll("%block_pos_chunk_z%", String.valueOf(((Block) values.get("Block")).getChunk().getZ()))
				.replaceAll("%block_pos_x%", String.valueOf(((Block) values.get("Block")).getX())).replaceAll("%block_pos_y%", String.valueOf(((Block) values.get("Block")).getY()))
				.replaceAll("%block_pos_z%", String.valueOf(((Block) values.get("Block")).getZ())).replaceAll("%block_world%", ((Block) values.get("Block")).getWorld().getName())
				.replaceAll("%player_name%", ((Player) values.get("Player")).getName()).replaceAll("%player_gamemode%", ((Player) values.get("Player")).getGameMode().toString())
				.replaceAll("%player_iteminhand%", ((Player) values.get("Player")).getItemInHand().getType().toString());
		return msg;
	}
}
