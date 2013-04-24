package org.efreak.bukkitmanager.tutorials.actions;

import org.bukkit.block.Block;
import org.efreak.bukkitmanager.tutorials.TutorialAction;

public class BlockBreakAction extends TutorialAction {

    private Block block;

    public BlockBreakAction(String content, boolean sendMsg) {
        super(content, sendMsg);
    }

}
