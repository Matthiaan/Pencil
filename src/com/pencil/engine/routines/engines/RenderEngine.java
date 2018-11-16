package com.pencil.engine.routines.engines;

import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        switch (type) {
            case CUBE:
            case CUBOID:
            case PYRAMID:
            case PRISM:
            case SPHERE:
            case ELLIPSOID:
            case CYLINDER:
        }
    }

    private static void drawCuboid(Player player) {

    }

    private static void drawPyramid(Player player) {

    }

    private static void drawPrism(Player player) {

    }

    private static void drawSphere(Player player) {

    }

    private static void drawCylinder(Player player) {

    }

}
