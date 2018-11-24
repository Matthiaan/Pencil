package com.pencil.engine.utils.events;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilNonUndoableAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PencilHotbarEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ItemStack item;

    public PencilHotbarEvent(Player player, ItemStack item) {
        this.player = player;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public PencilAction getAction() {
        return new PencilNonUndoableAction(PencilAction.ActionType.INTERFACE, Pencil.getActionManager().getNextID(Pencil.getPlayerService().getPlayer(player)));
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
