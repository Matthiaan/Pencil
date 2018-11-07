package com.pencil.engine.utils.events.shape;

import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilShapeAction;
import com.pencil.engine.utils.events.PencilEvent;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilPreShapeCreationEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Selection selection;
    private ShapeUtils.ShapeType type;
    private Vector scale;
    private Material material;

    public PencilPreShapeCreationEvent(Player player, Selection selection, ShapeUtils.ShapeType type, Vector scale, Material material) {
        this.player = player;
        this.selection = selection;
        this.type = type;
        this.scale = scale;
        this.material = material;
    }

    public Player getPlayer() {
        return player;
    }

    public Selection getSelection() {
        return selection;
    }

    public ShapeUtils.ShapeType getType() {
        return type;
    }

    public Vector getScale() {
        return scale;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public PencilAction getAction() {
        return new PencilShapeAction();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
