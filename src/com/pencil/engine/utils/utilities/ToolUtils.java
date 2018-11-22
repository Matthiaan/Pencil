package com.pencil.engine.utils.utilities;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;

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

}
