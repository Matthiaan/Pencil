package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilRequestPreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.Material;

import java.util.HashMap;

public class ToolUtils {

    public enum ToolType {
        REGULAR, RULER, ANGLER,
    }

    public static void measure(PencilPlayer player, Vector min, Vector max) {
        double distance = min.distance(max);

        player.getPlayer().sendMessage(MessageService.formatMessage("Distance calculated between " + min.toString() + " and " + max.toString() + ".",
                MessageService.MessageType.INFO));
        player.getPlayer().sendMessage(MessageService.formatMessage("Distance = " + (int) distance + " blocks.",
                MessageService.MessageType.INFO));
    }

    public static void addCustomCuboidRequest(PencilPlayer player, Vector scale, Material material, boolean isAir) {
        player.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
        player.getCurrentShapeRequest().setSelection(Pencil.getSelectionManager().get(player));
        player.getCurrentShapeRequest().setScale(scale);
        player.getCurrentShapeRequest().setFilled(true);

        if (isAir) {
            player.getCurrentShapeRequest().setMaterial(Material.AIR);
        } else {
            player.getCurrentShapeRequest().setMaterial(material);
        }

        try {
            HashMap<Vector, Material> map = ShapeUtils.getMaterialsInRegion(player.getPlayer().getWorld(), RenderEngine.getPreRenderedFootage(player.getCurrentShapeRequest()));

            Pencil.getEventService().queueEvent(new PencilRequestPreProcessingEvent(player.getPlayer(), player.getCurrentShapeRequest(), map));
        } catch (NullPointerException ex) {
            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_HISTORY_AVAILABLE.getMessage(),
                    MessageService.MessageType.WARNING));
        }
    }

}
