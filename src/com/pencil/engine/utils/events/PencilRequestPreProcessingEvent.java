package com.pencil.engine.utils.events;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.request.Request;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilRequestAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class PencilRequestPreProcessingEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Request request;
    private HashMap<Vector, Material> old;

    public PencilRequestPreProcessingEvent(Player player, Request request, HashMap<Vector, Material> old) {
        this.player = player;
        this.request = request;
        this.old = old;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public Request getRequest() {
        return request;
    }

    public HashMap<Vector, Material> getOldMaterials() {
        return old;
    }

    @Override
    public PencilAction getAction() {
        return new PencilRequestAction(request, old);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
