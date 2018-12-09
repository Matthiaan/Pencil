package com.pencil.engine.utils.action;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.request.FixedShapeRequest;
import com.pencil.engine.pipeline.request.Request;
import com.pencil.engine.utils.events.PencilHistoryEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PencilRequestAction implements PencilAction {

    private Request request;
    private HashMap<Vector, Material> old;
    private int ID;

    public PencilRequestAction(Request request, HashMap<Vector, Material> old) {
        this.request = request;
        this.old = old;
    }

    public Request getRequest() {
        return request;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPERATION;
    }

    @Override
    public void undo(Player player) {
        for (Vector vector : old.keySet()) {
            player.getWorld().getBlockAt(vector.toLocation(player.getWorld())).setType(old.get(vector));
        }

        Pencil.getEventService().queueEvent(new PencilHistoryEvent(this, player));
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
