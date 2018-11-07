package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;

public class PencilShapeAction implements PencilAction {

    @Override
    public ActionType getActionType() {
        return ActionType.OPERATION;
    }

    @Override
    public void undo(Player player) {

    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
