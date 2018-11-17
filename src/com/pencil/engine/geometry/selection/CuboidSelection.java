package com.pencil.engine.geometry.selection;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class CuboidSelection implements Selection {

    private Vector min, max;
    private World world;

    public CuboidSelection(Vector min, Vector max) {
        this.min = min;
        this.max = max;
        this.world = null;
    }

    public CuboidSelection(Vector min, Vector max, World world) {
        this.min = min;
        this.max = max;
        this.world = world;
    }

    @Override
    public Vector getNativeMinimumVector() {
        return min;
    }

    @Override
    public Vector getNativeMaximumVector() {
        return max;
    }

    @Override
    public Location getMinimum() {
        return asLocation(world, min);
    }

    @Override
    public Location getMaximum() {
        return asLocation(world, max);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getBlocks() {
        return getWidth() * getHeight() * getLength();
    }

    //Distance over X
    @Override
    public int getWidth() {
        return Math.abs(this.min.getBlockX() - this.max.getBlockX());
    }

    //Distance over Y
    @Override
    public int getHeight() {
        return Math.abs(this.min.getBlockY() - this.max.getBlockY());
    }

    //Distance over Z
    @Override
    public int getLength() {
        return Math.abs(this.min.getBlockZ() - this.max.getBlockZ());
    }

    @Override
    public boolean contains(Vector other) {
        return getVectors().contains(other);
    }

    @Override
    public ArrayList<Vector> getVectors() {
        return ShapeUtils.getCuboid(this, true);
    }

    @Override
    public MultiSelection getWalls() {
        return new MultiSelection(new Selection[] {
                new CuboidSelection(this.min.setX(min.getX()), this.max.setX(min.getX()), getWorld()),
                new CuboidSelection(this.min.setX(max.getX()), this.max.setX(max.getX()), getWorld()),
                new CuboidSelection(this.min.setZ(min.getZ()), this.max.setZ(min.getZ()), getWorld()),
                new CuboidSelection(this.min.setZ(max.getZ()), this.max.setZ(max.getZ()), getWorld())});
    }

    @Override
    public Location asLocation(World world, Vector other) {
        if (contains(other)) {
            return other.toLocation(world);
        } else {
            return null;
        }
    }
}
