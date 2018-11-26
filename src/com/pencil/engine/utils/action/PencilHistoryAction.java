package com.pencil.engine.utils.action;

import org.bukkit.entity.Player;

public class PencilHistoryAction implements PencilAction {

    private PencilAction undoneAction;
    private int ID;

    public PencilHistoryAction(PencilAction undoneAction, int ID) {
        this.undoneAction = undoneAction;
        this.ID = ID;
    }

    public PencilAction getUndoneAction() {
        return undoneAction;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.HISTORY;
    }

    @Override
    public void undo(Player player) {
        //TODO: Implement this!
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    //TODO: Make this undoable (So actually -> redoable)
    @Override
    public boolean isUndoable() {
        return false;
    }
}
