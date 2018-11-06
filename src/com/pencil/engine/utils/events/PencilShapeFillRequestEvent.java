package com.pencil.engine.utils.events;

import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilNonUndoableAction;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Optional;

public class PencilShapeFillRequestEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ShapeUtils.ShapeType type;
    private Selection selection;
    private Optional<PencilEvent> previous;

    public PencilShapeFillRequestEvent(Player player, ShapeUtils.ShapeType type, Selection selection, Optional<PencilEvent> previous) {
        this.player = player;
        this.type = type;
        this.selection = selection;
        this.previous = previous;
    }

    public Player getPlayer() {
        return player;
    }

    public ShapeUtils.ShapeType getType() {
        return type;
    }

    public Selection getSelection() {
        return selection;
    }

    public Optional<PencilEvent> getPrevious() {
        return previous;
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