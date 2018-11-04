package com.pencil.engine.geometry.selection;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class PolygonSelection implements Selection {

    //This class does have a FillMode because we want to know whether to use a regular fill or a division fill!
    private PencilPlayer.FillMode mode;
    private ArrayList<Vector> vertices;
    private World world;

    public PolygonSelection(PencilPlayer.FillMode mode, ArrayList<Vector> vertices) {
        this.mode = mode;
        this.vertices = vertices;
    }

    public PolygonSelection(PencilPlayer.FillMode mode, ArrayList<Vector> vertices, World world) {
        this.mode = mode;
        this.vertices = vertices;
        this.world = world;
    }

    public ArrayList<Vector> getVertices() {
        return vertices;
    }

    @Override
    public Vector getNativeMinimumVector() {
        Vector current = Vector.MAX;

        for (Vector vertex : vertices) {
            if (vertex.getBlockY() <= current.getBlockY()) {
                current = vertex;
            }
        }

        return current;
    }

    @Override
    public Vector getNativeMaximumVector() {
        Vector current = Vector.MIN;

        for (Vector vertex : vertices) {
            if (vertex.getBlockY() >= current.getBlockY()) {
                current = vertex;
            }
        }

        return current;
    }

    @Override
    public Location getMinimum() {
        return asLocation(world, getNativeMinimumVector());
    }

    @Override
    public Location getMaximum() {
        return asLocation(world, getNativeMaximumVector());
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getBlocks() {
        return 0;
    }

    //TODO: Optimize
    @Override
    public int getWidth() {
        //Min = max and max = min so we can easily adjust the values!
        Vector min = null;
        Vector max = null;

        for (Vector vertex : vertices) {
            if (min == null) {
                min = vertex;
            }

            if (max == null) {
                max = vertex;
            }

            if (vertex.getBlockX() <= min.getBlockX()) {
                min = vertex;
            } else if (vertex.getBlockX() >= max.getBlockX()) {
                max = vertex;
            }
        }

        return (int) Math.floor(min.distance(max));
    }

    //TODO: Optimize
    @Override
    public int getHeight() {
        Vector min = null;
        Vector max = null;

        for (Vector vertex : vertices) {
            if (min == null) {
                min = vertex;
            }

            if (max == null) {
                max = vertex;
            }

            if (vertex.getBlockY() <= min.getBlockY()) {
                min = vertex;
            } else if (vertex.getBlockY() >= max.getBlockY()) {
                max = vertex;
            }
        }

        return (int) Math.floor(min.distance(max));
    }

    //TODO: Optimize
    @Override
    public int getLength() {
        //Min = max and max = min so we can easily adjust the values!
        Vector min = null;
        Vector max = null;

        for (Vector vertex : vertices) {
            if (min == null) {
                min = vertex;
            }

            if (max == null) {
                max = vertex;
            }

            if (vertex.getBlockZ() <= min.getBlockZ()) {
                min = vertex;
            } else if (vertex.getBlockZ() >= max.getBlockZ()) {
                max = vertex;
            }
        }

        return (int) Math.floor(min.distance(max));
    }

    @Override
    public boolean contains(Vector other) {
        return false;
    }

    @Override
    public ArrayList<Vector> getVectors() {
        switch (mode) {
            case REGULAR:
                break;
            case DIVIDE:
                return ShapeUtils.getPolygonDivisionFilled(this);
        }

        return null;
    }

    @Override
    public MultiSelection getWalls() {
        return null;
    }

    @Override
    public Location asLocation(World world, Vector other) {
        return null;
    }
}
