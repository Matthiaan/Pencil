package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;

public class PencilNonUndoableAction implements PencilAction {

    private ActionType type;

    public PencilNonUndoableAction(ActionType type) {
        this.type = type;
    }

    @Override
    public ActionType getActionType() {
        return type;
    }

    @Override
    public void undo(Player player) { }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public String toString() {
        return "[ActionType -> " + getActionType().toString() + ",Undoable -> " + isUndoable() + "] - This is an undoable action!";
    }
}
