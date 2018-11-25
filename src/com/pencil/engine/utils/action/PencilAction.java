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
        INTERFACE,
        INITIALIZED
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

    /**
     * Sets the ID of the action.
     *
     * @param id the ID of the action.
     */
    abstract void setID(int id);

    /**
     * Get the id of the current action.
     *
     * @return the id of the action.
     */
    abstract int getID();

}
