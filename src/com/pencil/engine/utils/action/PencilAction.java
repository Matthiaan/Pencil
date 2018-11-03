package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;

/**
 * @author Matthias Kovacic
 * Represents any Pencil-related action taken by any player.
 */
public abstract interface PencilAction {

    /**
     * Enum that contains every type of action possible.
     */
    enum ActionType {
        COMMAND,
        OPERATION,
        SELECTION,
        INTERFACE
    }

    /**
     * @return The type of action the action is.
     */
    abstract ActionType getActionType();

    /**
     * @return Whether or not the action is undoable.
     */
    abstract boolean isUndoable();

    /**
     * Undo the action if the action is undoable.
     *
     * @param player for which the action should be undone.
     */
    abstract void undo(Player player);

}
