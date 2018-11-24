package com.pencil.engine.utils.action;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.entity.Player;

public class PencilVectorAction implements PencilAction {

    private Vector vector;
    private int ID;

    public PencilVectorAction(Vector vector, int ID) {
        this.vector = vector;
        this.ID = ID;
    }

    public PencilAction.ActionType getActionType() {
        return ActionType.SELECTION;
    }

    public Vector getVector() {
        return vector;
    }

    @Override
    public boolean isUndoable() {
        return true;
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
        return "[ActionType -> " + getActionType().toString() + ",Undoable -> " + isUndoable() + "] - Vector -> " + vector.toString();
    }
}
