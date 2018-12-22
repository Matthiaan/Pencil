package com.pencil.engine.geometry.math;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.Clipboard;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashMap;

public class SelectionMath {

    public static void copyToClipboard(PencilPlayer player) {
        World world = player.getPlayer().getWorld();
        Selection selection = Pencil.getSelectionManager().get(player);
        HashMap<Vector, Material> offsets = new HashMap<>();

        Vector point = selection.getNativeMinimumVector();

        for (Vector vector : selection.getVectors()) {
            Vector offset = Vector.getOffset(point, vector);

            offsets.put(offset, world.getBlockAt(vector.toLocation(world)).getType());
        }

        player.updateClipboard(offsets);
        player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.SELECTION_COPIED.getMessage(),
                MessageService.MessageType.INFO));
    }

    public static void pasteFromClipboard(PencilPlayer player) {
        Clipboard clipboard = player.getClipboard();
        Selection selection = Pencil.getSelectionManager().get(player);

        if (!(selection instanceof VectorSelection)) {
            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_RIGHT_SELECTION.getMessage(),
                    MessageService.MessageType.WARNING));
            player.getPlayer().sendMessage(MessageService.formatMessage("To paste a selection, only a single position must be selected!",
                    MessageService.MessageType.INFO));

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
                MessageService.MessageType.INFO));
    }

    //TODO: Optimize this method drastically!
    public static void rotateClipboard(PencilPlayer player, double angle) {
        Clipboard clipboard = player.getClipboard();

        if (clipboard.getClipped() == null) {
            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.CLIPBOARD_EMPTY.getMessage(),
                    MessageService.MessageType.WARNING));

            return;
        }

        HashMap<Vector, Material> offsets = clipboard.getClipped();
        HashMap<Vector, Material> rotatedOffsets = new HashMap<>();

        if (angle == 180) {
            for (Vector vector : offsets.keySet()) {
                rotatedOffsets.put(new Vector(-vector.getBlockX(), -vector.getBlockY(), -vector.getBlockZ()), offsets.get(vector));
            }

            player.getClipboard().setClipped(rotatedOffsets);
        } else if (angle == 90) {
            for (Vector vector : offsets.keySet()) {
                if (vector.getBlockX() == 0) {
                    if (vector.getBlockZ() != 0) {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockZ(),
                                vector.getBlockY(),
                                0
                        ), offsets.get(vector));
                    } else {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockX(),
                                vector.getBlockY(),
                                vector.getBlockZ()
                        ), offsets.get(vector));
                    }
                } else if (vector.getBlockZ() == 0) {
                    if (vector.getBlockX() != 0) {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockZ(),
                                vector.getBlockY(),
                                0
                        ), offsets.get(vector));
                    } else {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockX(),
                                vector.getBlockY(),
                                vector.getBlockZ()
                        ), offsets.get(vector));
                    }
                }

                if (vector.getBlockX() > 0) {
                    if (vector.getBlockZ() > 0) {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockX(),
                                vector.getBlockY(),
                                -vector.getBlockZ()
                        ), offsets.get(vector));
                    } else if (vector.getBlockZ() < 0) {
                        rotatedOffsets.put(new Vector(
                                -vector.getBlockX(),
                                vector.getBlockY(),
                                vector.getBlockZ()
                        ), offsets.get(vector));
                    }
                } else if (vector.getBlockX() < 0) {
                    if (vector.getBlockZ() > 0) {
                        rotatedOffsets.put(new Vector(
                                -vector.getBlockX(),
                                vector.getBlockY(),
                                vector.getBlockZ()
                        ), offsets.get(vector));
                    } else if (vector.getBlockZ() < 0) {
                        rotatedOffsets.put(new Vector(
                                vector.getBlockX(),
                                vector.getBlockY(),
                                -vector.getBlockZ()
                        ), offsets.get(vector));
                    }
                }
            }

            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.SELECTION_PASTED.getMessage(),
                    MessageService.MessageType.INFO));
        } else {
            player.getPlayer().sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                    MessageService.MessageType.WARNING));
        }
    }

}
