package com.pencil.engine.utils.events;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilShapeAction;
import com.pencil.engine.utils.player.PencilPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class PencilShapeEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private PencilPlayer.ShapeRequest request;
    private HashMap<Vector, Material> old;

    public PencilShapeEvent(Player player, PencilPlayer.ShapeRequest request, HashMap<Vector, Material> old) {
        this.player = player;
        this.request = request;
        this.old = old;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public PencilPlayer.ShapeRequest getRequest() {
        return request;
    }

    public HashMap<Vector, Material> getOldMaterials() {
        return old;
    }

    @Override
    public PencilAction getAction() {
        return new PencilShapeAction(request, old);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
