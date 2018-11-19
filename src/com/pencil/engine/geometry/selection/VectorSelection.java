package com.pencil.engine.geometry.selection;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class VectorSelection implements Selection {

    private Vector vector;
    private World world;

    public VectorSelection(Vector vector) {
        this.vector = vector;
        this.world = null;
    }

    public VectorSelection(Vector vector, World world) {
        this.vector = vector;
        this.world = world;
    }

    @Override
    public Vector getNativeMinimumVector() {
        return vector;
    }

    @Override
    public Vector getNativeMaximumVector() {
        return vector;
    }

    @Override
    public Location getMinimum() {
        return asLocation(world, vector);
    }

    @Override
    public Location getMaximum() {
        return asLocation(world, vector);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getBlocks() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public boolean contains(Vector other) {
        return other == vector;
    }

    @Override
    public ArrayList<Vector> getVectors() {
        return new ArrayList<>(vector.toArray());
    }

    @Override
    public MultiSelection getWalls() {
        return null;
    }

    private Location asLocation(World world) {
        return vector.toLocation(world);
    }

    @Override
    public Location asLocation(World world, Vector other) {
        if (other == vector) {
            return asLocation(world);
        } else {
            return null;
        }
    }

    @Override
    public SelectionType getType() {
        return SelectionType.VECTOR;
    }
}
