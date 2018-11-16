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

    public enum PositionSetType {
        NONE,
        SINGLE,
        DOUBLE,
        MULTI
    }

    public static PositionSetType getType(ArrayList<Vector> vectors) {
        if (vectors == null || vectors.size() == 0) {
            return PositionSetType.NONE;
        } else if (vectors.size() == 1) {
            return PositionSetType.SINGLE;
        } else if (vectors.size() == 2) {
            return PositionSetType.DOUBLE;
        } else {
            return PositionSetType.MULTI;
        }
    }

    public static ArrayList<Vector> getCuboid(CuboidSelection selection, boolean isFilled) {
        if (isFilled) {
            return getCuboidFilled(selection);
        } else {
            return getCuboidUnfilled(selection);
        }
    }

    private static ArrayList<Vector> getCuboidFilled(CuboidSelection selection) {
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

    private  static ArrayList<Vector> getCuboidUnfilled(CuboidSelection selection) {
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

    public static ArrayList<Vector> getPyramid(CuboidSelection selection, boolean isFilled) {
        if (isFilled) {
            return getPyramidFilled(selection);
        } else {
            return getPyramidUnfilled(selection);
        }
    }

    public static ArrayList<Vector> getPyramidFilled(CuboidSelection selection) {
        Vector min = selection.getNativeMinimumVector();
        Vector max = selection.getNativeMaximumVector();
        Vector minCalc = new Vector(max.getX(), min.getY(), max.getZ());

        ArrayList<Vector> vectors = new ArrayList<>();

        //Pyramids are generated using CuboidSelections!
        CuboidSelection preSelection = new CuboidSelection(min, minCalc);
        ArrayList<CuboidSelection> selections = getPyramidSelections(preSelection);

        vectors.addAll(preSelection.getVectors());

        for (CuboidSelection cSelection : selections) {
            vectors.addAll(cSelection.getVectors());
        }

        return vectors;
    }

    private static ArrayList<Vector> getPyramidUnfilled(CuboidSelection selection) {
        //TODO: Make this, now just return a filled selection
        return getPyramidUnfilled(selection);
    }

    private static ArrayList<CuboidSelection> getPyramidSelections(CuboidSelection selection) {
        Vector min = selection.getNativeMinimumVector();
        Vector max = selection.getNativeMaximumVector();
        Vector newMin = min;
        Vector newMax = max;
        ArrayList<CuboidSelection> selections = new ArrayList<>();

        selections.add(selection);

        int i = selection.getWidth();

        //Expr ==> i = (i - 2)
        while (i >= 1) {
            if ((i == 1) || (i == 2)) {
                //TODO: Fix this!

                continue;
            } else {
                int x = max.getBlockX();
                int z = max.getBlockZ();

                newMin = new Vector(
                     recalculatePyramidCoordinate(newMin.getBlockX(), x),
                     0,
                     recalculatePyramidCoordinate(newMin.getBlockZ(), z)
                );

                newMax = new Vector(
                        reverse(recalculatePyramidCoordinate(newMax.getBlockX(), x)),
                        0,
                        reverse(recalculatePyramidCoordinate(newMax.getBlockZ(), z))
                );

                selections.add(new CuboidSelection(newMin, newMax));
            }

            i = i - 2;
        }

        return selections;
    }

    private static int recalculatePyramidCoordinate(int i, int p) {
        if (i < 0) {
            if (p < 0) {
                return i - 1;
            } else {
                return i + 1;
            }
        } else {
            if (p < 0) {
                return i - 1;
            } else {
                return i + 1;
            }
        }
    }

    private static int reverse(int i) {
        return -i;
    }

    //TODO: Find an algorithm
    public static ArrayList<Vector> getPolygonDivisionFilled(PolygonSelection selection) {
        ArrayList<Vector> vertices = selection.getVertices();

        return vertices;
    }

}
