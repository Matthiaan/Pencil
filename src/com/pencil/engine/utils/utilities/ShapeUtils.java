package com.pencil.engine.utils.utilities;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.MultiSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;

import java.util.ArrayList;

public class ShapeUtils {

    public static ArrayList<Vector> getCuboidFilled(Vector min, Vector max) {
        ArrayList<Vector> vectors = new ArrayList<>();

        for (int x = Math.max(max.getBlockX(), min.getBlockX()); x >= Math.min(min.getBlockX(), max.getBlockX()); x--) {
            for (int y = Math.max(max.getBlockY(), min.getBlockY()); y >= Math.min(min.getBlockY(), max.getBlockY()); y--) {
                for (int z = Math.max(max.getBlockZ(), min.getBlockZ()); z >= Math.min(min.getBlockZ(), max.getBlockZ()); z--) {
                    vectors.add(new Vector(x, y, z));
                }
            }
        }

        return vectors;
    }

    public static ArrayList<Vector> getCuboidUnfilled(Vector min, Vector max) {
        ArrayList<Vector> vectors = new ArrayList<>();
        CuboidSelection temporary = new CuboidSelection(min, max, null);
        MultiSelection walls = temporary.getWalls();

        for (Selection selection : walls.getSelections()) {
            Vector nMin = selection.getNativeMinimumVector();
            Vector nMax = selection.getNativeMaximumVector();

            for (int x = Math.max(nMax.getBlockX(), nMin.getBlockX()); x >= Math.min(nMin.getBlockX(), nMax.getBlockX()); x--) {
                for (int y = Math.max(nMax.getBlockY(), nMin.getBlockY()); y >= Math.min(nMin.getBlockY(), nMax.getBlockY()); y--) {
                    for (int z = Math.max(nMax.getBlockZ(), nMin.getBlockZ()); z >= Math.min(nMin.getBlockZ(), nMax.getBlockZ()); z--) {
                        vectors.add(new Vector(x, y, z));
                    }
                }
            }
        }

        return vectors;
    }

    //TODO: Make a separate selection type for this. Currently it uses CuboidSelection to store a minimum and maximum value, but CANNOT be used AS the polygon!
    public static CuboidSelection calculate(ArrayList<Vector> vectors) {
        Vector currentMin = vectors.get(0);
        Vector currentMax = vectors.get(vectors.size() - 1);

        for (Vector vector : vectors) {
            if (vector.getBlockY() < currentMin.getBlockY()) {
                currentMin = vector;
            } else if (vector.getBlockY() > currentMax.getBlockY()) {
                currentMax = vector;
            }
        }

        return new CuboidSelection(currentMin, currentMax);
    }

}
