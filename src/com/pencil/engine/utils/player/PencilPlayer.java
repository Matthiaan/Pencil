package com.pencil.engine.utils.player;

import com.pencil.engine.geometry.Clipboard;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.request.FixedOperationRequest;
import com.pencil.engine.pipeline.request.FixedShapeRequest;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import com.pencil.engine.utils.utilities.ToolUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PencilPlayer {

    public enum SelectionMode {
        NA, VECTOR, DOUBLE, MULTI
    }

    private Player player;
    private PencilHistory history;
    private SelectionMode mode;

    private Clipboard clipboard;

    private FixedShapeRequest shapeRequest;
    private FixedOperationRequest operationRequest;

    private Selection selection;
    private ToolUtils.ToolType toolType;

    public PencilPlayer(Player player) {
        this.player = player;

        history = new PencilHistory(player.getUniqueId());
        mode = SelectionMode.NA;

        clipboard = new Clipboard();
    }

    public Player getPlayer() {
        return player;
    }

    public PencilHistory getHistory() {
        return history;
    }

    public SelectionMode getMode() {
        return mode;
    }

    public void setMode(SelectionMode mode) {
        this.mode = mode;
    }

    public void updateClipboard(HashMap<Vector, Material> clipped) {
        clipboard.setClipped(clipped);
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public void setShapeRequest(ShapeUtils.ShapeType type) {
        shapeRequest = null;
        shapeRequest = new FixedShapeRequest(player);
        shapeRequest.setType(type);
    }

    public void setOperationRequest(Vector vector) {
        operationRequest = null;
        operationRequest = new FixedOperationRequest(player);
        operationRequest.setPastePoint(vector);
    }

    public FixedShapeRequest getCurrentShapeRequest() {
        return shapeRequest;
    }

    public FixedOperationRequest getOperationRequest() {
        return operationRequest;
    }

    public void resetShapeRequest() {
        shapeRequest = null;
    }

    public void resetOperationRequest() {
        operationRequest = null;
    }

    public void setSelection(Selection selection) {
        if (selection == null) {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SELECTION_EMPTY.getMessage(),
                    MessageService.MessageType.WARNING, false));
        }

        this.selection = selection;
    }

    public Selection getSelection() {
        return selection;
    }

    public ToolUtils.ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolUtils.ToolType toolType) {
        this.toolType = toolType;
    }
}
