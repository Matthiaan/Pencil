package com.pencil.engine.utils.action;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.entity.Player;

public class PencilVectorAction implements PencilAction {

    private Vector vector;

    public PencilVectorAction(Vector vector) {
        this.vector = vector;
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
    public void undo(Player player) { }

    @Override
    public String toString() {
        return "[ActionType -> " + getActionType().toString() + ",Undoable -> " + isUndoable() + "] - Vector -> " + vector.toString();
    }
}
