package com.pencil.engine.utils.action;

public abstract interface PencilAction {

    enum ActionType {
        COMMAND,
        OPERATION,
        SELECTION,
        INTERFACE
    }

    abstract boolean isUndoable();

}
