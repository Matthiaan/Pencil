package com.pencil.engine.geometry;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.Material;

import java.util.HashMap;

public class Clipboard {

    private HashMap<Vector, Material> clipped;

    public Clipboard() { }

    public void setClipped(HashMap<Vector, Material> clipped) {
        this.clipped = clipped;
    }

    public HashMap<Vector, Material> getClipped() {
        return clipped;
    }
}
