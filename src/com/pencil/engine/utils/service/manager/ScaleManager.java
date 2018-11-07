package com.pencil.engine.utils.service.manager;

import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.HashMap;

public class ScaleManager {

    private HashMap<PencilPlayer, Vector> vectors = new HashMap<>();

    public void add(PencilPlayer player, Vector vector) {
        reset(player);

        vectors.put(player, vector);
    }

    public void set(PencilPlayer player, int value) {
        //Calculating the next not-defined value
        if (vectors.get(player) != null) {
            Vector vector = vectors.get(player);

            if (vector.getX() == 0) {
                vector.setX(value);
            } else if (vector.getY() == 0) {
                vector.setY(value);
            } else if (vector.getZ() == 0) {
                vector.setZ(value);
            } else {
                throw new IllegalStateException("This scaling vector already has 3 parameters that differ from 0!");
            }
        }
    }

    public Vector get(PencilPlayer player, boolean reset) {
        if (vectors.get(player) != null) {
            Vector vector = vectors.get(player);

            if (reset) {
                this.reset(player);
            }

            return vector;
        } else {
            throw new IllegalStateException("This player doesn't have any saved scaling!");
        }
    }

    public void reset(PencilPlayer player) {
        if (vectors.get(player) != null) {
            vectors.remove(player);
        }
    }

}
