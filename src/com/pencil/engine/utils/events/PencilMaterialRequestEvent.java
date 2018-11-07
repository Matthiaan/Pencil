package com.pencil.engine.utils.events;

import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilNonUndoableAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilMaterialRequestEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Material material;
    private PencilEvent event;

    public PencilMaterialRequestEvent(Player player, Material material, PencilEvent event) {
        this.player = player;
        this.material = material;
        this.event = event;
    }

    public Player getPlayer() {
        return player;
    }

    public Material getMaterial() {
        return material;
    }

    public PencilEvent getEvent() {
        return event;
    }

    @Override
    public PencilAction getAction() {
        return new PencilNonUndoableAction(PencilAction.ActionType.INTERFACE);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
