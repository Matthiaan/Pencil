package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.routines.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.Material;

public class ToolUtils {

    public enum ToolType {
        REGULAR, RULER, ANGLER,
    }

    public static void measure(PencilPlayer player, Vector min, Vector max) {
        double distance = min.distance(max);

        player.getPlayer().sendMessage(MessageService.formatMessage("Distance calculated between " + min.toString() + " and " + max.toString() + ".",
                MessageService.MessageType.INFO, false));
        player.getPlayer().sendMessage(MessageService.formatMessage("Distance = " + (int) distance + " blocks.",
                MessageService.MessageType.INFO, false));
    }

    public static void addCustomRequest(PencilPlayer player, ShapeUtils.ShapeType type, Selection selection, Vector scale, Material material, boolean filled) {
        player.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
        player.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(player));
        player.getCurrentRequest().setScale(new Vector(0, 0, 0));
        player.getCurrentRequest().setMaterial(material);
        player.getCurrentRequest().setFilled(true);

        try {
            Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player.getPlayer(), player.getCurrentRequest(),
                    ShapeUtils.getOldMaterials(player.getPlayer().getWorld(), RenderEngine.getPreRenderedFootage(player.getCurrentRequest()))));
        } catch (NullPointerException ex) {
            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_HISTORY_AVAILABLE.getMessage(),
                    MessageService.MessageType.WARNING, false));
        }
    }

}
