package com.pencil.engine.utils.service.manager;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.HashMap;

public class SelectionManager extends Manager {

    private String name = "selection_manager";
    private HashMap<PencilPlayer, Selection> currentSelection;

    public SelectionManager() {
        currentSelection = new HashMap<>();
    }

    @Override
    public void run() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void update(PencilPlayer player, Selection selection) {
        reset(player);

        currentSelection.put(player, selection);
    }

    public void remove(PencilPlayer player) {
        reset(player);
    }

    private void reset(PencilPlayer player) {
        if (currentSelection.containsKey(player)) {
            currentSelection.remove(player);
        }
    }

    public Selection get(PencilPlayer player) {
        if (currentSelection.containsKey(player)) {
            return currentSelection.get(player);
        }

        return null;
    }

    public Selection get(PencilPlayer player, Selection.SelectionType type) {
        if (get(player).getType() == type) {
            return get(player);
        }

        return null;
    }

    public boolean hasSelection(PencilPlayer player) {
        return currentSelection.containsKey(player);
    }
}