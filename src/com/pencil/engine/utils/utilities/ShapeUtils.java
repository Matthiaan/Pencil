package com.pencil.engine.utils.utilities;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.MultiSelection;
import com.pencil.engine.geometry.selection.PolygonSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.vector.Vector;

import java.util.ArrayList;

public class ShapeUtils {

    public enum ShapeType {
        //Rectangular Shapes
        CUBOID,
        CUBE,
        PYRAMID,
        PRISM,

        //Spherical Shapes
        SPHERE,
        ELLIPSOID,
        CYLINDER
    }

    public static ArrayList<Vector> getCuboidFilled(CuboidSelection selection) {
        Vector min = selection.getNativeMinimumVector();
        Vector max = selection.getNativeMaximumVector();

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

    public static ArrayList<Vector> getCuboidUnfilled(CuboidSelection selection) {
        Vector min = selection.getNativeMinimumVector();
        Vector max = selection.getNativeMaximumVector();

        ArrayList<Vector> vectors = new ArrayList<>();
        CuboidSelection temporary = new CuboidSelection(min, max, null);
        MultiSelection walls = temporary.getWalls();

        for (Selection s : walls.getSelections()) {
            Vector nMin = s.getNativeMinimumVector();
            Vector nMax = s.getNativeMaximumVector();

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

    //TODO: See how I can implement this!
    public static ArrayList<Vector> getPolygonDivisionFilled(PolygonSelection selection) {
        ArrayList<Vector> vertices = selection.getVertices();

        return vertices;
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
