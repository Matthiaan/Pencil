package com.pencil.engine.geometry.math;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.Clipboard;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.Material;

import java.util.HashMap;

public class SelectionMath {

    public static void copyToClipboard(PencilPlayer player) {
        Selection selection = Pencil.getSelectionManager().get(player);
        HashMap<Vector, Material> offsets = new HashMap<>();

        Vector point = selection.getVectors().get(0);

        for (Vector vector : selection.getVectors()) {
            Vector offset = Vector.getOffset(point, vector);

            offsets.put(offset, player.getPlayer().getWorld().getBlockAt(offset.toLocation(player.getPlayer().getWorld())).getType());
        }

        player.updateClipboard(offsets);
        player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.SELECTION_COPIED.getMessage(),
                MessageService.MessageType.INFO, false));
    }

    public static void pasteFromClipboard(PencilPlayer player) {
        Clipboard clipboard = player.getClipboard();
        Selection selection = Pencil.getSelectionManager().get(player);

        if (!(selection instanceof VectorSelection)) {
            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_RIGHT_SELECTION.getMessage(),
                    MessageService.MessageType.WARNING, false));
            player.getPlayer().sendMessage(MessageService.formatMessage("To paste a selection, only one position must be selected!",
                    MessageService.MessageType.INFO, false));

            return;
        }

        Vector pastePoint = selection.getNativeMinimumVector();
        HashMap<Vector, Material> offsets = clipboard.getClipped();

        //These offsets are re-calculated when they need to be drawn!
        //This may brake all of Pencil!

        player.setOperationRequest(pastePoint);
        player.getOperationRequest().setOffsets(offsets);
        player.getOperationRequest().perform(true);

        player.resetOperationRequest();
        player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.SELECTION_PASTED.getMessage(),
                MessageService.MessageType.INFO, false));
    }

}
