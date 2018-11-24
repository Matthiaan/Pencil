package com.pencil.engine.utils.action;

import com.pencil.engine.command.PencilCommand;
import org.bukkit.entity.Player;

public class PencilCommandAction implements PencilAction {

    private PencilCommand command;
    private int ID;

    public PencilCommandAction(PencilCommand command, int ID) {
        this.command = command;
        this.ID = ID;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.COMMAND;
    }

    public PencilCommand getCommand() {
        return command;
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public void undo(Player player) {

    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "[ActionType -> " + getActionType().toString() + ",Undoable -> " + isUndoable() + "] - Command -> " + command.getName();
    }
}
