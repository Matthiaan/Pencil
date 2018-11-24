package com.pencil.engine.utils.events;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilShapeAction;
import com.pencil.engine.utils.player.PencilPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilShapeEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private PencilPlayer.ShapeRequest request;

    public PencilShapeEvent(Player player, PencilPlayer.ShapeRequest request) {
        this.player = player;
        this.request = request;
    }

    public Player getPlayer() {
        return player;
    }

    public PencilPlayer.ShapeRequest getRequest() {
        return request;
    }

    @Override
    public PencilAction getAction() {
        return new PencilShapeAction(request, Pencil.getActionManager().getNextID(Pencil.getPlayerService().getPlayer(player)));
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
