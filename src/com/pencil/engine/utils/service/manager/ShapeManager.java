package com.pencil.engine.utils.service.manager;

import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.utilities.ShapeUtils;

import java.util.HashMap;

public class ShapeManager {

    private HashMap<PencilPlayer, ShapeUtils.ShapeType> types = new HashMap<>();

    public void add(PencilPlayer player, ShapeUtils.ShapeType type) {
        reset(player);

        types.put(player, type);
    }

    public ShapeUtils.ShapeType get(PencilPlayer player, boolean reset) {
        if (types.get(player) != null) {
            ShapeUtils.ShapeType type = types.get(player);

            if (reset) {
                this.reset(player);
            }

            return type;
        } else {
            throw new IllegalStateException("This player doesn't have any saved selection!");
        }
    }

    public void reset(PencilPlayer player) {
        if (types.get(player) != null) {
            types.remove(player);
        }
    }

    public boolean hasType(PencilPlayer player) {
        return types.containsKey(player);
    }
}
