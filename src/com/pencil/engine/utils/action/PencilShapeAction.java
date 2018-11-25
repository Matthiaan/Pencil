package com.pencil.engine.utils.action;

import com.pencil.engine.utils.player.PencilPlayer;
import org.bukkit.entity.Player;

public class PencilShapeAction implements PencilAction {

    private PencilPlayer.ShapeRequest request;
    private int ID;

    public PencilShapeAction(PencilPlayer.ShapeRequest request) {
        this.request = request;
    }

    public PencilPlayer.ShapeRequest getRequest() {
        return request;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPERATION;
    }

    @Override
    public void undo(Player player) {
        //TODO: Create Undo method
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
