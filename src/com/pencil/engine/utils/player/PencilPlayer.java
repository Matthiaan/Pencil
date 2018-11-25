package com.pencil.engine.utils.player;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilShapeEvent;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import com.pencil.engine.utils.utilities.ToolUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PencilPlayer {

    public enum SelectionMode {
        NA, VECTOR, DOUBLE, MULTI
    }

    private Player player;
    private PencilHistory history;
    private SelectionMode mode;

    private ShapeRequest shapeRequest;
    private Selection selection;
    private ToolUtils.ToolType toolType;

    public PencilPlayer(Player player) {
        this.player = player;

        history = new PencilHistory(player.getUniqueId());
        mode = SelectionMode.NA;
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

    public void setShapeRequest(ShapeUtils.ShapeType type) {
        shapeRequest = new ShapeRequest(player);
        shapeRequest.setType(type);
    }

    public ShapeRequest getCurrentRequest() {
        return shapeRequest;
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

    public class ShapeRequest {

        private Player owner;
        private Selection selection;
        private ShapeUtils.ShapeType type;
        private Vector scale;
        private Material material;

        private boolean filled;

        public ShapeRequest(Player owner) {
            this.owner = owner;
            this.selection = null;
            this.type = null;
            this.scale = null;
            this.material = null;

            filled = true;
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

        public void isApplicableMaterial(Material material, boolean inform) {
            setMaterial(material);

            if (inform) {
                owner.closeInventory();
                owner.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SHAPE_CREATION.getMessage(),
                        MessageService.MessageType.INFO, false));

                if (Pencil.getSelectionManager().hasSelection(Pencil.getPlayerService().getPlayer(player))) {
                    setSelection(Pencil.getSelectionManager().get(Pencil.getPlayerService().getPlayer(player)));
                }

                Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(owner, this));
            }
        }
    }
}
