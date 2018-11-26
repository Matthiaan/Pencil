package com.pencil.engine.utils.events;

import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilHistoryAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilHistoryEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private PencilAction action;
    private Player player;

    public PencilHistoryEvent(PencilAction action, Player player) {
        this.action = action;
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public PencilAction getActualAction() {
        return action;
    }

    @Override
    public PencilAction getAction() {
        return new PencilHistoryAction(action, action.getID());
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
