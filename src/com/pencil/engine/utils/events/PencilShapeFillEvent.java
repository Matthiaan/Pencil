package com.pencil.engine.utils.events;

import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PencilShapeFillEvent extends Event implements PencilEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ShapeUtils.ShapeType type;
    private Selection selection;
    private Material material;

    public PencilShapeFillEvent(Player player, ShapeUtils.ShapeType type, Selection selection) {
        this.player = player;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public PencilAction getAction() {
        return null;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
