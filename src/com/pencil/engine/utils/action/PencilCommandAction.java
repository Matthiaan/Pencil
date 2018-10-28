package com.pencil.engine.utils.action;

import com.pencil.engine.command.PencilCommand;

public abstract class PencilCommandAction implements PencilAction {

    private PencilCommand command;

    public PencilCommandAction(PencilCommand command) {
        this.command = command;
    }

    public PencilAction.ActionType getActionType() {
        return PencilAction.ActionType.COMMAND;
    }

    public PencilCommand getCommand() {
        return command;
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
