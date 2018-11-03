package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;

public abstract interface PencilAction {

    enum ActionType {
        COMMAND,
        OPERATION,
        SELECTION,
        INTERFACE
    }

    abstract boolean isUndoable();

    abstract ActionType getActionType();

    abstract void undo(Player player);

}
