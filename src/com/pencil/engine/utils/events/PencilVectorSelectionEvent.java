package com.pencil.engine.utils.events;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilVectorSelectionEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Vector vector;

    public PencilVectorSelectionEvent(Player player, Vector vector) {
        this.player = player;
        this.vector = vector;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector getVector() {
        return vector;
    }

    @Override
    public PencilAction.ActionType getActionType() {
        return PencilAction.ActionType.SELECTION;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
