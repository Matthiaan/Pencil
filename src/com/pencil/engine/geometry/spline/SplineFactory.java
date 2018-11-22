package com.pencil.engine.geometry.spline;

import com.pencil.engine.geometry.vector.Vector;

import java.util.ArrayList;

/**
 * @author Matthias Kovacic
 *
 * This class will take care of Spline Generation
 */
public class SplineFactory {

    public enum SplineType {
        PATH,
        LINEAR,
        SMOOTH
    }

    private static ArrayList<Vector> factorize(ArrayList<Vector> positions, SplineType type) {
        switch (type) {
            case PATH:
            case LINEAR:
            case SMOOTH:
        }

        return null;
    }

}
