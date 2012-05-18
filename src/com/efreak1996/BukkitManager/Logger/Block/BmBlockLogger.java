package com.efreak1996.BukkitManager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;

import com.efreak1996.BukkitManager.Logger.BmLogger;


public abstract class BmBlockLogger extends BmLogger {

	public BmBlockLogger(String event) {
		super(event + "Logger", "Block");
	}
		

	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockFade(BlockFadeEvent event) {
		if (config.getBoolean("Logger.Block.BlockFade")) {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("NewState", event.getNewState());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockForm(BlockFormEvent event) {
		if (config.getBoolean("Logger.Block.BlockForm")) {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("NewState", event.getNewState());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockFromTo(BlockFromToEvent event) {
		if (config.getBoolean("Logger.Block.BlockFromTo")) {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("Face", event.getFace());
			values.put("ToBlock", event.getToBlock());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockGrow(BlockGrowEvent event) {
		if (config.getBoolean("Logger.Block.BlockGrow"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("NewState", event.getNewState());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (config.getBoolean("Logger.Block.BlockIgnite"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("IgniteCause", event.getCause());
			values.put("Player", event.getPlayer());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		if (config.getBoolean("Logger.Block.BlockPhysics"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("ChangedType", event.getChangedType());
			values.put("ChangedTypeId", event.getChangedTypeId());
			info(values);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		if (config.getBoolean("Logger.Block.BlockPistonExtend"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("Blocks", event.getBlocks());
			values.put("Direction", event.getDirection());
			values.put("Length", event.getLength());
			values.put("Sticky", event.isSticky());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		if (config.getBoolean("Logger.Block.BlockPistonRetract"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("RetractLocation", event.getRetractLocation());
			values.put("Sticky", event.isSticky());
			values.put("Direction", event.getDirection());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (config.getBoolean("Logger.Block.BlockPlace"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("Player", event.getPlayer());
			values.put("BlockAgainst", event.getBlockAgainst());
			values.put("BlockPlaced", event.getBlockPlaced());
			values.put("BlockReplacedState", event.getBlockReplacedState());
			values.put("ItemInHand", event.getItemInHand());
			values.put("canBuild", event.canBuild());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockRedstone(BlockRedstoneEvent event) {
		if (config.getBoolean("Logger.Block.BlockRedstone"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("NewCurrent", event.getNewCurrent());
			values.put("OldCurrent", event.getOldCurrent());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockSpread(BlockSpreadEvent event) {
		if (config.getBoolean("Logger.Block.BlockSpread"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("NewState", event.getNewState());
			values.put("Source", event.getSource());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBlockForm(EntityBlockFormEvent event) {
		if (config.getBoolean("Logger.Block.EntityBlockForm"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("NewState", event.getNewState());
			values.put("Entity", event.getEntity());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (config.getBoolean("Logger.Block.LeavesDecay"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			info(values);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event) {
		if (config.getBoolean("Logger.Block.SignChange"))  {
			HashMap<String, Object> values = new HashMap<String, Object>();
			values.put("EventName", event.getEventName());
			values.put("Block", event.getBlock());
			values.put("Cancelled", event.isCancelled());
			values.put("Lines", event.getLines());
			values.put("Player", event.getPlayer());
			info(values);
		}
	}
}
