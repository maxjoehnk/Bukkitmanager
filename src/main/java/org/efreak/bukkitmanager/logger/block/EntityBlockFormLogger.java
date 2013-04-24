package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockFormLogger extends BlockLogger {

    public EntityBlockFormLogger() {
        super("EntityBlockForm");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("EventName", event.getEventName());
        values.put("Block", event.getBlock());
        values.put("Cancelled", event.isCancelled());
        values.put("NewState", event.getNewState());
        values.put("Entity", event.getEntity());
        info(values);
    }

    @Override
    public void setupLogger() {
        initialize(new EntityBlockFormHandler(this));
        fileLogging = handler.fileLogging;
        dbLogging = handler.dbLogging;
    }
}
