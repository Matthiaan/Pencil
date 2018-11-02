package com.pencil.engine.utils.action;

import com.pencil.engine.command.PencilCommand;

public class PencilCommandAction implements PencilAction {

    private PencilCommand command;

    public PencilCommandAction(PencilCommand command) {
        this.command = command;
    }

    @Override
    public ActionType getActionType() {
        return null;
    }

    public PencilCommand getCommand() {
        return command;
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
