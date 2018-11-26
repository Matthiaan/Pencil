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

    private static HashMap<Player, ArrayList<Voxel>> voxels = new HashMap<>();

    public static ArrayList<Vector> getPreRenderedFootage(PencilPlayer.ShapeRequest request) {
        Selection selection = request.getSelection();

        switch (request.getType()) {
            case CUBE:
            case CUBOID:
                if (selection instanceof VectorSelection) {
                    return ShapeUtils.getCuboid(new CuboidSelection(
                                    ((VectorSelection) selection).getNativeMaximumVector(),
                                    ((VectorSelection) selection).getNativeMaximumVector().add(request.getScale())),
                            request.isFilled());
                } else if (selection instanceof CuboidSelection) {
                    return ShapeUtils.getCuboid((CuboidSelection) selection, request.isFilled());
                }
            case PYRAMID:
                if (selection instanceof VectorSelection) {
                    return ShapeUtils.getPyramid(new CuboidSelection(
                            ((VectorSelection) selection).getNativeMaximumVector(),
                            ((VectorSelection) selection).getNativeMaximumVector().add(request.getScale())),
                            request.isFilled());
                } else if (selection instanceof CuboidSelection) {
                    return ShapeUtils.getPyramid((CuboidSelection) selection, request.isFilled());
                }
            case SPHERE:
            case ELLIPSOID:
                if (selection instanceof VectorSelection) {
                    return ShapeUtils.getEllipsoid((VectorSelection) selection, request.getScale(), request.isFilled());
                }
            case PRISM:
            case CYLINDER:
                return null;
        }

        return null;
    }

    public static boolean render(PencilShapePreProcessingEvent event) {
        Player player = event.getPlayer();
        PencilPlayer.ShapeRequest request = event.getRequest();

        if (render(player,
                request.getSelection(),
                request.getType(),
                request.getScale(),
                request.isFilled(),
                request.getMaterial())) {
            Bukkit.getServer().getPluginManager().callEvent(new PencilShapeEvent(player, request, event.getOld()));

            return true;
        } else {
            return false;
        }
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
                    return drawCuboid(player, selection, scale, isFilled, material, player.getWorld());
                case PYRAMID:
                    return drawPyramid(player, selection, scale, isFilled, material, player.getWorld());
                case SPHERE:
                case ELLIPSOID:
                    return drawSphere(player, selection, scale, isFilled, material, player.getWorld());
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
     * @param player The player that performs the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawCuboid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);
            ArrayList<Vector> vectors = ShapeUtils.getCuboid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material, (i == (vectors.size() - 1)));
            }

            return true;
        } else if (selection.getType() == Selection.SelectionType.CUBOID) {
            ArrayList<Vector> vectors = ShapeUtils.getCuboid((CuboidSelection) selection, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material, (i == (vectors.size() - 1)));
            }

            return true;
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
     * @param player The player that performs the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawPyramid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);
            ArrayList<Vector> vectors = ShapeUtils.getPyramid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material, (i == (vectors.size() - 1)));
            }

            return true;
        } else if (selection.getType() == Selection.SelectionType.CUBOID) {
            ArrayList<Vector> vectors = ShapeUtils.getPyramid((CuboidSelection) selection, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material, (i == (vectors.size() - 1)));
            }

            return true;
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static boolean drawPrism(Player player) {
        //TODO: Make this

        return false;
    }

    /**
     * Method to draw a sphere/ellipsoid!
     *
     * @param player The player that performs the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws ShapeProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawSphere(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws ShapeProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            ArrayList<Vector> vectors = ShapeUtils.getEllipsoid(vSelection, scale, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material, (i == (vectors.size() - 1)));
            }

            return true;
        } else {
            throw new ShapeProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static boolean drawCylinder(Player player) {
        return false;
    }

    private static void drawVoxel(Player player, World world, Vector vector, Material material, boolean isFinished) {
        queueVoxel(player, new Voxel(vector, material, world), isFinished);
    }

    private static void queueVoxel(Player player, Voxel voxel, boolean isFinished) {
        if (!voxels.containsKey(player)) {
            voxels.put(player, new ArrayList<>());
        }

        voxels.get(player).add(voxel);

        if (isFinished) {
            DrawEngine.draw(voxels.get(player));
        }
    }

}
