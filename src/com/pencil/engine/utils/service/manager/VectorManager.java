package com.pencil.engine.utils.service.manager;

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
