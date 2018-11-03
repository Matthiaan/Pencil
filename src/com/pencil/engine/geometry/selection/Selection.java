package com.pencil.engine.geometry.selection;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.util.ArrayList;

/**
 * @author Matthias Kovacic
 * Represents a list of Vectors located inside a certain shape.
 */
public abstract interface Selection {

    /**
     * @see Vector
     *
     * @return The native minimum vector of the selection.
     */
    Vector getNativeMinimumVector();

    /**
     * @see Vector
     *
     * @return The native maximum vector of the selection.
     */
    Vector getNativeMaximumVector();

    /**
     * @return The native minimum vector as a location.
     */
    Location getMinimum();

    /**
     * @return The native maximum vector as a location.
     */
    Location getMaximum();

    /**
     * @return The world the vector is located in.
     */
    @Nullable
    World getWorld();

    /**
     * @return The amount of blocks that are in the selection.
     */
    int getBlocks();

    /**
     * @return The width of the selection.
     */
    int getWidth();

    /**
     * @return The height of the selection.
     */
    int getHeight();

    /**
     * @return The length of the selection.
     */
    int getLength();

    /**
     * Check if the given vector is located inside the selection.
     *
     * @param other The vector that needs to be checked.
     * @return True if the vector is located inside the selection.
     */
    boolean contains(Vector other);

    /**
     * @return All the vectors located inside the selection.
     */
    ArrayList<Vector> getVectors();

    /**
     * @return All the vectors located on the walls of the selection.
     */
    MultiSelection getWalls();

    /**
     * Convert any vector located inside the selection to a location.
     *
     * @param world The world the location should be in. (Thus this can span over multiple worlds!)
     * @param other The vector that needs to be converted.
     * @return The given vector as a location.
     */
    Location asLocation(World world, Vector other);

}
