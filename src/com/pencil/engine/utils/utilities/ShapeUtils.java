package com.pencil.engine.utils.utilities;

import com.pencil.engine.geometry.selection.*;
import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static PositionSetType getType(Selection selection) {
        if (selection == null) {
            return PositionSetType.NONE;
        } else if (selection instanceof VectorSelection) {
            return PositionSetType.SINGLE;
        } else if (selection instanceof CuboidSelection) {
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

    public static ArrayList<Vector> getEllipsoid(VectorSelection selection, Vector scale, boolean filled) {
        ArrayList<Vector> vectors = new ArrayList<>();
        Vector origin = selection.getNativeMinimumVector();

        double radiusX = scale.getBlockX();
        double radiusY = scale.getBlockY();
        double radiusZ = scale.getBlockZ();

        final double invRadiusX = 1 / radiusX;
        final double invRadiusY = 1 / radiusY;
        final double invRadiusZ = 1 / radiusZ;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusY = (int) Math.ceil(radiusY);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX: for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ:
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = (xn * xn) + (yn * yn) + (zn * zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }

                    if (!filled) {
                        if ((nextXn * nextXn) + (yn * yn) + (zn * zn) <= 1 && (xn * xn) + (nextYn * nextYn) + (zn * zn) <= 1 && (xn * xn) + (yn * yn) + (nextZn * nextZn) <= 1) {
                            continue;
                        }
                    }

                    vectors.add(new Vector(origin.add(x, y, z)));
                    vectors.add(new Vector(origin.add(-x, y, z)));
                    vectors.add(new Vector(origin.add(x, -y, z)));
                    vectors.add(new Vector(origin.add(x, y, -z)));
                    vectors.add(new Vector(origin.add(-x, -y, z)));
                    vectors.add(new Vector(origin.add(x, -y, -z)));
                    vectors.add(new Vector(origin.add(-x, y, -z)));
                    vectors.add(new Vector(origin.add(-x, -y, -z)));
                }
            }
        }

        if (filled) {
            vectors.add(origin);
        }

        return vectors;
    }

    public static ArrayList<Vector> getCylinder(VectorSelection selection, Vector scale, boolean filled) {
        ArrayList<Vector> vectors = new ArrayList<>();
        Vector origin = selection.getNativeMinimumVector();

        double radiusX = scale.getBlockX();
        double height = scale.getBlockY();
        double radiusZ = scale.getBlockZ();

        radiusX += 0.5;
        radiusZ += 0.5;

        if (height == 0) {
            return null;
        } else if (height < 0) {
            height = -height;
            origin = origin.subtract(0, height, 0);
        }

        if (origin.getBlockY() < 0) {
            origin = origin.setY(0);
        } else if (origin.getBlockY() + height - 1 > 256) {
            height = 256 - origin.getBlockY() + 1;
        }

        final double invRadiusX = 1 / radiusX;
        final double invRadiusZ = 1 / radiusZ;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX: for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextZn = 0;
            forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
                final double zn = nextZn;
                nextZn = (z + 1) * invRadiusZ;

                double distanceSq = (xn * xn) + (zn * zn);
                if (distanceSq > 1) {
                    if (z == 0) {
                        break forX;
                    }
                    break forZ;
                }

                if (!filled) {
                    if ((nextXn * nextXn) + (z * z) <= 1 && (x * x) + (nextZn * nextZn) <= 1) {
                        continue;
                    }
                }

                for (int y = 0; y < height; ++y) {
                    vectors.add(new Vector(origin.add(x, y, z)));
                    vectors.add(new Vector(origin.add(-x, y, z)));
                    vectors.add(new Vector(origin.add(x, y, -z)));
                    vectors.add(new Vector(origin.add(-x, y, -z)));
                }
            }
        }

        if (filled) {
            vectors.add(origin);
        }

        return vectors;
    }

    //TODO: Find an algorithm
    public static ArrayList<Vector> getPolygonDivisionFilled(PolygonSelection selection) {
        ArrayList<Vector> vertices = selection.getVertices();

        return vertices;
    }

    public static HashMap<Vector, Material> getMaterialsInRegion(World world, ArrayList<Vector> vectors) {
        HashMap<Vector, Material> materials = new HashMap<>();

        for (Vector vector : vectors) {
            materials.put(vector, world.getBlockAt(vector.toLocation(world)).getType());
        }

        return materials;
    }

    public static HashMap<Vector, Material> getMaterialsInOffsetRegion(World world, Vector vector, ArrayList<Vector> offsets) {
        HashMap<Vector, Material> materials = new HashMap<>();

        for (Vector offset : offsets) {
            Vector finalVector = vector.add(offset);

            materials.put(finalVector, world.getBlockAt(finalVector.toLocation(world)).getType());
        }

        return materials;
    }

}
