package com.pencil.engine.pipeline.request;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilRequestPreProcessingEvent;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FixedOperationRequest implements Request {

    private enum OperationType {
        SELECION_PASTE,
    }

    private Player owner;
    private HashMap<Vector, Material> offsets;
    private Vector pastePoint;

    public FixedOperationRequest(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public HashMap<Vector, Material> getOffsets() {
        return offsets;
    }

    public void setOffsets(HashMap<Vector, Material> offsets) {
        this.offsets = offsets;
    }

    public Vector getPastePoint() {
        return pastePoint;
    }

    public void setPastePoint(Vector pastePoint) {
        this.pastePoint = pastePoint;
    }

    public void perform(boolean inform) {
        if (inform) {
            owner.closeInventory();
            owner.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_REQUEST_PRE_PROCESSING.getMessage(),
                    MessageService.MessageType.INFO));

            try {
                Pencil.getEventService().queueEvent(new PencilRequestPreProcessingEvent(owner.getPlayer(), this,
                        ShapeUtils.getMaterialsInOffsetRegion(owner.getPlayer().getWorld(), pastePoint, RenderEngine.getPreRenderedFootage(this))));
            } catch (NullPointerException ex) {
                owner.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_HISTORY_AVAILABLE.getMessage(),
                        MessageService.MessageType.WARNING));
            }
        }
    }
}
