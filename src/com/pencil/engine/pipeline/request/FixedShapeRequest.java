package com.pencil.engine.pipeline.request;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilRequestPreProcessingEvent;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FixedShapeRequest implements Request {

    private Player owner;
    private Selection selection;
    private ShapeUtils.ShapeType type;
    private Vector scale;
    private Material material;

    private boolean filled;

    public FixedShapeRequest(Player owner) {
        this.owner = owner;
        this.selection = null;
        this.type = null;
        this.scale = null;
        this.material = null;

        filled = true;
    }

    public Player getOwner() {
        return owner;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public ShapeUtils.ShapeType getType() {
        return type;
    }

    public void setType(ShapeUtils.ShapeType type) {
        this.type = type;
    }

    public Vector getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void perform(Material material, boolean inform, boolean isAir) {
        if (isAir) {
            setMaterial(Material.AIR);
        } else {
            setMaterial(material);
        }

        if (inform) {
            owner.closeInventory();
            owner.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_REQUEST_PRE_PROCESSING.getMessage(),
                    MessageService.MessageType.INFO, false));

            if (Pencil.getSelectionManager().hasSelection(Pencil.getPlayerService().getPlayer(owner))) {
                setSelection(Pencil.getSelectionManager().get(Pencil.getPlayerService().getPlayer(owner)));
            }

            try {
                Pencil.getEventService().queueEvent(new PencilRequestPreProcessingEvent(owner.getPlayer(), this,
                        ShapeUtils.getMaterialsInRegion(owner.getPlayer().getWorld(), RenderEngine.getPreRenderedFootage(this))));
            } catch (NullPointerException ex) {
                owner.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_HISTORY_AVAILABLE.getMessage(),
                        MessageService.MessageType.WARNING, false));
            }
        }
    }

}
