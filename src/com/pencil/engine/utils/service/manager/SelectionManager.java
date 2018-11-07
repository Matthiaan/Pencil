package com.pencil.engine.utils.service.manager;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.PolygonSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectionManager {

    private HashMap<PencilPlayer, Selection> selections = new HashMap<>();

    public void add(PencilPlayer player, Selection selection) {
        reset(player);

        selections.put(player, selection);
    }

    public void add(PencilPlayer player, ArrayList<Vector> positions) {
        Selection selection;

        if (positions.size() == 1) {
            selection = new VectorSelection(positions.get(0));
        } else if (positions.size() == 2) {
            selection = new CuboidSelection(positions.get(0), positions.get(1));
        } else {
            selection = new PolygonSelection(PencilPlayer.FillMode.REGULAR, positions);
        }

        add(player, selection);
    }

    public Selection get(PencilPlayer player, boolean reset) {
        if (selections.get(player) != null) {
            Selection selection = selections.get(player);

            if (reset) {
                this.reset(player);
            }

            return selection;
        } else {
            throw new IllegalStateException("This player doesn't have any saved selection!");
        }
    }

    public void reset(PencilPlayer player) {
        if (selections.get(player) != null) {
            selections.remove(player);
        }
    }

    public boolean hasSelection(PencilPlayer player) {
        return selections.containsKey(player);
    }

}
