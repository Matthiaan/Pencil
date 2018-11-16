package com.pencil.engine.routines.engines;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.PolygonSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.ShapeProcessingException;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
 *
 * Also a bit of memory management is done through here!
 */
public class RenderEngine {

    public static void render(PencilShapePreProcessingEvent event) {
        Player player = event.getPlayer();
        PencilPlayer.ShapeRequest request = event.getRequest();

        render(player,
                request.getSelection(),
                request.getType(),
                request.getScale(),
                request.isFilled(),
                request.getMaterial());
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
    private static void render(Player player, Selection selection, ShapeUtils.ShapeType type, Vector scale, boolean isFilled, Material material) {
        try {
            switch (type) {
                case CUBE:
                case CUBOID:
                    drawCuboid(player, selection, scale, isFilled, material, player.getWorld());
                case PYRAMID:
                    drawPyramid(player, selection, scale, isFilled, material, player.getWorld());
                case PRISM:
                case SPHERE:
                case ELLIPSOID:
                case CYLINDER:
                    break;
            }
        } catch (ShapeProcessingException ex) {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SHAPE_PROCESSING_EXCEPTION.getMessage(),
                    MessageService.MessageType.ERROR, true));
        }
    }

    /**
     * Method to draw a cuboid.
     *
     * Note: We don't check for "PolygonSelection" since this is automatically
     * converted to a CuboidSelection in the Shape Selection process!
     *
     * @param player The player that executes the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static void drawCuboid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection instanceof VectorSelection) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);

            for (Vector vector : ShapeUtils.getCuboid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled)) {
                drawVoxel(world, vector, material);
            }
        } else if (selection instanceof CuboidSelection) {
            for (Vector vector : ShapeUtils.getCuboid((CuboidSelection) selection, isFilled)) {
                drawVoxel(world, vector, material);
            }
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
     * @param player The player that executes the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static void drawPyramid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection instanceof VectorSelection) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);

            for (Vector vector : ShapeUtils.getPyramid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled)) {
                drawVoxel(world, vector, material);
            }
        } else if (selection instanceof CuboidSelection) {
            for (Vector vector : ShapeUtils.getPyramid((CuboidSelection) selection, isFilled)) {
                drawVoxel(world, vector, material);
            }
        } else if (selection == null) {
            throw new ShapeProcessingException("Null selection!");
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static void drawPrism(Player player) {

    }

    private static void drawSphere(Player player) {

    }

    private static void drawCylinder(Player player) {

    }

    private static void drawVoxel(World world, Vector vector, Material material) {
        world.getBlockAt(vector.toLocation(world)).setType(material);
    }

}
