package com.pencil.engine.utils.service.manager;

import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.PolygonSelection;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VectorManager extends Manager {

    private HashMap<PencilPlayer, ArrayList<Vector>> vectors;
    private String name = "vector_manager";

    public VectorManager() {
        vectors = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        vectors.entrySet().parallelStream().forEach(entry -> System.out.println(entry.getKey().getPlayer().getDisplayName() + " -> " + entry.getValue().size() + " Vectors stored!"));
    }

    public void add(PencilPlayer player, ArrayList<Vector> vectors) {
        reset(player);

        this.vectors.put(player, vectors);
    }

    public void update(PencilPlayer player, Vector vector) {
        if (get(player) != null) {
            get(player).add(vector);
        } else {
            add(player, new ArrayList<>(Collections.singleton(vector)));
        }
    }

    public Selection retrieve(PencilPlayer player) {
        ArrayList<Vector> vectors = get(player);

        if (vectors == null) {
            return null;
        } else if (vectors.size() == 1) {
            return new VectorSelection(vectors.get(0), player.getPlayer().getWorld());
        } else if (vectors.size() == 2) {
            return new CuboidSelection(vectors.get(0), vectors.get(1), player.getPlayer().getWorld());
        } else {
            return new PolygonSelection(PolygonSelection.FillMode.REGULAR, vectors);
        }
    }

    public void remove(PencilPlayer player) {
        reset(player);
    }

    public ArrayList<Vector> get(PencilPlayer player) {
        if (vectors.containsKey(player)) {
            return vectors.get(player);
        }

        return null;
    }

    private void reset(PencilPlayer player) {
        if (vectors.containsKey(player)) {
            vectors.remove(player);
        }
    }

}
