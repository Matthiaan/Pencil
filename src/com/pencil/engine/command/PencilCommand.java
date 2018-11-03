package com.pencil.engine.command;

import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public abstract class PencilCommand implements PencilAction {

    /**
     * Main method where the command gets executed.
     *
     * @param player The player that has send the command.
     * @param args The remaining arguments for the command.
     */
    public abstract void onCommand(Player player, String[] args);

    /**
     * @return The name of the command.
     */
    public abstract String getName();

    /**
     * @return The arguments of the command.
     */
    public abstract String getArgs();

    /**
     * @return The description of what the command does.
     */
    public abstract String getDescription();

    /**
     * @return The permission needed in order to execute the command.
     */
    public abstract Permission getPermission();

    /**
     * @see com.pencil.engine.utils.action.PencilAction.ActionType
     *
     * @return The type of action a command is. In this case it will always be COMMAND.
     */
    public ActionType getActionType() {
        return ActionType.COMMAND;
    }

    /**
     * @see PencilAction
     *
     * @return Whether or not the action is undoable. In this case, a command is never undoable.
     */
    @Override
    public boolean isUndoable() {
        return false;
    }

    /**
     * This method isn't used on commands because commands aren't undoable.
     * @see PencilAction
     *
     * @param player For which the action must be undone.
     */
    @Override
    public void undo(Player player) {}
}
