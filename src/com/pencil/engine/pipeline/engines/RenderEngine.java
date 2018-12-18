package com.pencil.engine.pipeline.engines;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.pipeline.engines.utils.Voxel;
import com.pencil.engine.pipeline.request.FixedOperationRequest;
import com.pencil.engine.pipeline.request.FixedShapeRequest;
import com.pencil.engine.pipeline.request.Request;
import com.pencil.engine.utils.RequestProcessingException;
import com.pencil.engine.utils.events.PencilRequestEvent;
import com.pencil.engine.utils.events.PencilRequestPreProcessingEvent;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

    public static ArrayList<Vector> getPreRenderedFootage(FixedOperationRequest request) {
        Vector vector = request.getPastePoint();
        Set<Vector> offsets = request.getOffsets().keySet();

        ArrayList<Vector> vectors = new ArrayList<>();

        for (Vector offset : offsets) {
            vectors.add(new Vector(vector).add(offset));
        }

        vectors.add(vector);

        return vectors;
    }

    public static ArrayList<Vector> getPreRenderedFootage(FixedShapeRequest request) {
        Selection selection = request.getSelection();

        switch (request.getType()) {
            case CUBE:
            case CUBOID:
                if (selection.getType() == Selection.SelectionType.VECTOR) {
                    return ShapeUtils.getCuboid(new CuboidSelection(
                                    ((VectorSelection) selection).getNativeMaximumVector(),
                                    ((VectorSelection) selection).getNativeMaximumVector().add(request.getScale())),
                            request.isFilled());
                } else if (selection.getType() == Selection.SelectionType.CUBOID) {
                    return ShapeUtils.getCuboid((CuboidSelection) selection, request.isFilled());
                }
            case SPHERE:
            case ELLIPSOID:
                if (selection.getType() == Selection.SelectionType.VECTOR) {
                    return ShapeUtils.getEllipsoid((VectorSelection) selection, request.getScale(), request.isFilled());
                }
            case CYLINDER:
                if (selection.getType() == Selection.SelectionType.VECTOR) {
                    return ShapeUtils.getCylinder((VectorSelection) selection, request.getScale(), request.isFilled());
                }
            case PRISM:
            case PYRAMID:
                System.out.println("Null Undo Material");

                return null;
        }

        System.out.println("Null Undo Material");

        return null;
    }

    public static boolean render(PencilRequestPreProcessingEvent event) {
        Player player = event.getPlayer();
        Request request = event.getRequest();

        if (request instanceof FixedShapeRequest) {
            FixedShapeRequest fixedShapeRequest = (FixedShapeRequest) request;

            if (render(player,
                    fixedShapeRequest.getSelection(),
                    fixedShapeRequest.getType(),
                    fixedShapeRequest.getScale(),
                    fixedShapeRequest.isFilled(),
                    fixedShapeRequest.getMaterial())) {
                Bukkit.getServer().getPluginManager().callEvent(new PencilRequestEvent(player, fixedShapeRequest, event.getOldMaterials()));

                return true;
            } else {
                return false;
            }
        } else if (request instanceof FixedOperationRequest) {
            FixedOperationRequest fixedOperationRequest = (FixedOperationRequest) request;

            if (render(player, fixedOperationRequest.getPastePoint(), fixedOperationRequest.getOffsets())) {
                Bukkit.getServer().getPluginManager().callEvent(new PencilRequestEvent(player, fixedOperationRequest, event.getOldMaterials()));
            }
        }

        return false;
    }

    /**
     * If the shape is a CUBE, I want to use 2 vectors.
     * If the shape is a CUBOID, I want to use 1 scaling vector.
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
                case SPHERE:
                case ELLIPSOID:
                    return drawSphere(player, selection, scale, isFilled, material, player.getWorld());
                case CYLINDER:
                    drawCylinder(player, selection, scale, isFilled, material, player.getWorld());
                case PRISM:
                    return false;
            }
        } catch (RequestProcessingException ex) {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_REQUEST_PROCESSING_EXCEPTION.getMessage(),
                    MessageService.MessageType.ERROR));
            player.sendMessage(MessageService.formatMessage(ex.getMessage(),
                    MessageService.MessageType.ERROR));
        }

        return false;
    }

    private static boolean render(Player player, Vector vector, HashMap<Vector, Material> offsets) {
        for (Vector offset : offsets.keySet()) {
            drawVoxel(player, player.getWorld(), vector.add(offset), offsets.get(offset));
        }

        return true;
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
     * @throws RequestProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawCuboid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws RequestProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            Vector max = vSelection.getNativeMaximumVector().add(scale);
            ArrayList<Vector> vectors = ShapeUtils.getCuboid(new CuboidSelection(vSelection.getNativeMaximumVector(), max), isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material);
            }

            pushQueue(player);

            return true;
        } else if (selection.getType() == Selection.SelectionType.CUBOID) {
            ArrayList<Vector> vectors = ShapeUtils.getCuboid((CuboidSelection) selection, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material);
            }

            pushQueue(player);

            return true;
        } else {
            throw new RequestProcessingException("This cannot happen! Please contact a(n) operator/developer!");
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
     * @throws RequestProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawPyramid(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws RequestProcessingException {
        return false;
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
     * @throws RequestProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawSphere(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws RequestProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            ArrayList<Vector> vectors = ShapeUtils.getEllipsoid(vSelection, scale, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material);
            }

            pushQueue(player);

            return true;
        } else {
            throw new RequestProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    /**
     * Method to draw a cylinder.
     *
     * @param player The player that performs the operation.
     * @param selection The selection the operation should be performed within.
     * @param scale The optional scale of the operation.
     * @param isFilled Decides whether or not the to be generated shape should be filled or not.
     * @param material The material used to fill the to be generated shape.
     * @param world The world the operation should be performed within.
     * @throws RequestProcessingException Is thrown whenever a selection doesn't match the requirements.
     */
    private static boolean drawCylinder(Player player, Selection selection, Vector scale, boolean isFilled, Material material, World world)
            throws RequestProcessingException {
        if (selection.getType() == Selection.SelectionType.VECTOR) {
            VectorSelection vSelection = (VectorSelection) selection;
            ArrayList<Vector> vectors = ShapeUtils.getCylinder(vSelection, scale, isFilled);

            for (int i = 0; i < vectors.size(); i++) {
                drawVoxel(player, world, vectors.get(i), material);
            }

            pushQueue(player);

            return true;
        } else {
            throw new RequestProcessingException("This cannot happen! Please contact a(n) operator/developer!");
        }
    }

    private static void drawVoxel(Player player, World world, Vector vector, Material material) {
        queueVoxel(player, new Voxel(vector, material, world));
    }

    private static void queueVoxel(Player player, Voxel voxel) {
        if (!voxels.containsKey(player)) {
            voxels.put(player, new ArrayList<>());
        }

        voxels.get(player).add(voxel);
    }

    private static void pushQueue(Player player) {
        DrawEngine.draw(voxels.get(player));
        clearQueue(player);
    }

    private static void clearQueue(Player player) {
        voxels.remove(player);
    }

}
