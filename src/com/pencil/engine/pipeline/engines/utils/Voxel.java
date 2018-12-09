package com.pencil.engine.pipeline.engines.utils;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.Material;
import org.bukkit.World;

public class Voxel {

    private Vector position;
    private Material material;
    private World world;

    public Voxel(Vector position, Material material, World world) {
        this.position = position;
        this.material = material;
        this.world = world;
    }

    public Vector getPosition() {
        return position;
    }

    public Material getMaterial() {
        return material;
    }

    public World getWorld() {
        return world;
    }
}
