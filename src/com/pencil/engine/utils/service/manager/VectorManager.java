package com.pencil.engine.utils.service.manager;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.PolygonSelection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.ArrayList;

public class VectorManager extends Manager {

    private String name = "vector_manager";

    public VectorManager() {

    }

    @Override
    public void run() {}

    @Override
    public String getName() {
        return name;
    }

    public void addVector(PencilPlayer player, Vector vector) {
        Pencil.getSelectionManager().update(player, new VectorSelection(vector));
    }

    public void addCuboid(PencilPlayer player, Vector min, Vector max) {
        Pencil.getSelectionManager().update(player, new CuboidSelection(min, max));
    }

    public void addPolygon(PencilPlayer player, ArrayList<Vector> vectors) {
        Pencil.getSelectionManager().update(player, new PolygonSelection(PolygonSelection.FillMode.REGULAR, vectors));
    }
}
