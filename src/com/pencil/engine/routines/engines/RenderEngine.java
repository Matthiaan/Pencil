package com.pencil.engine.routines.engines;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.routines.engines.utils.Voxel;
import com.pencil.engine.utils.ShapeProcessingException;
import com.pencil.engine.utils.events.PencilShapeEvent;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Matthias Kovacic
 *
 * The render engine will be taking care of "rendering" all
 * the blocks in the world, with other words, plotting the
 * necessary blocks into the world.
 *
 * Certain algorithms are used to first place
 * non-resource intensive blocks (such as water), and
 * placing these blocks in a certain order of operation
 * so that the server won't lag.
 */
public class RenderEngine {

    private static HashMap<Player, ArrayList<Voxel>> voxels;

    public static boolean render(PencilShapePreProcessingEvent event) {
        Player player = event.getPlayer();
        PencilPlayer.ShapeRequest request = event.getRequest();

        if (render(player,
                request.getSelection(),
                request.getType(),
                request.getScale(),
                request.isFilled(),
                request.getMaterial())) {
            Bukkit.getServer().getPluginManager().callEvent(new PencilShapeEvent(player, request));

            return true;
        } else {
            return false;
        }
    }

    public static boolean render(Player player, Selection selection, Material material) {
        return render(player, selection, ShapeUtils.ShapeType.CUBOID, new Vector(0, 0, 0), true, material);
    }

    /**
     * If the shape is a CUBE, I want to use 2 vectors.
     * If the shape is a CUBOID, I want to use 1 scaling vector.
     * //TODO: Fix Filled/Unfilled
     *
     * @param player The player that performs the generation.
     * @param selection The vectors that are the retrieved from the players selection.
     * @param type The type of shape that needs to be generated.
     * @param scale The width, height and length of the to be generated shape.
     * @param material The material that has to be used for shape.
     */
    private static boolean render(Player player, Selection selection, ShapeUtils.ShapeType type, Vector scale, boolean isFilled, Material material) {
        try {
            switch (type) {
                case CUBE:
                case CUBOID:
                    return drawCuboid(selection, scale, isFilled, material, player.getWorld());
                case PYRAMID:
                    return drawPyramid(selection, scale, isFilled, material, player.getWorld());
                case SPHERE:
                case ELLIPSOID:
                    return drawSphere(selection, scale, isFilled, material, player.getWorld());
                case CYLINDER:
                case PRISM:
                    return false;
            }
        } catch (ShapeProcessingException ex) {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SHAPE_PROCESSING_EXCEPTION.getMessage(),
                    MessageService.MessageType.ERROR, true));
            player.sendMessage(MessageService.formatMessage(ex.getMessage(),
                    MessageService.MessageType.ERROR, false));
        }

        return false;
    }

    /**
     * Method to draw a cuboid.
     *
     * Note: We don't check for "PolygonSelection" since this is automatically
     * converted to a CuboidSelection in the Shape Selection process!
     *
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawCuboid(Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection instanceof VectorSelection) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);

            for (Vector vector : ShapeUtils.getCuboid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled)) {
                drawVoxel(world, vector, material);
            }

            return true;
        } else if (selection instanceof CuboidSelection) {
            for (Vector vector : ShapeUtils.getCuboid((CuboidSelection) selection, isFilled)) {
                drawVoxel(world, vector, material);
            }

            return true;
        } else if (selection == null) {
            throw new ShapeProcessingException("Null selection!");
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    /**
     * Method to draw a pyramid.
     *
     * Note: We don't check for "PolygonSelection" since this is automatically
     * converted to a CuboidSelection in the Shape Selection process!
     *
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawPyramid(Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection instanceof VectorSelection) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);

            for (Vector vector : ShapeUtils.getPyramid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled)) {
                drawVoxel(world, vector, material);
            }

            return true;
        } else if (selection instanceof CuboidSelection) {
            for (Vector vector : ShapeUtils.getPyramid((CuboidSelection) selection, isFilled)) {
                drawVoxel(world, vector, material);
            }

            return true;
        } else if (selection == null) {
            throw new ShapeProcessingException("Null selection!");
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static boolean drawPrism(Player player) {
        //TODO: Make this

        return false;
    }

    /**
     * Method to draw a sphere!
     *
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawSphere(Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection instanceof VectorSelection) {
            VectorSelection vSelection = (VectorSelection) selection;

            for (Vector vector : ShapeUtils.getEllipsoid(vSelection, scale, isFilled)) {
                drawVoxel(world, vector, material);
            }

            return true;
        } else if (selection instanceof CuboidSelection) {
            throw new ShapeProcessingException("Shapes can't be made using CuboidSelections");
        } else if (selection == null) {
            throw new ShapeProcessingException("Null selection!");
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static boolean drawCylinder(Player player) {
        return false;
    }

    private static void drawVoxel(World world, Vector vector, Material material) {
        world.getBlockAt(vector.toLocation(world)).setType(material);
    }

    private static void queueVoxel() {
        //TODO: -> DrawEngine
    }

}
