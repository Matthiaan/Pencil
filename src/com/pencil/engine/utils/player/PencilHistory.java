package com.pencil.engine.utils.player;

import com.pencil.engine.utils.action.PencilAction;

import java.util.HashMap;
import java.util.UUID;

public class PencilHistory {

    private UUID player;
    private HashMap<Integer, PencilAction> actions;

    public PencilHistory(UUID player) {
        this.player = player;
        this.actions = new HashMap<>();
    }

    public UUID getPlayer() {
        return player;
    }

    public HashMap<Integer, PencilAction> getActions() {
        return actions;
    }

    public PencilAction getAction(int index) {
        return actions.get(index);
    }

    public PencilAction getLatestAction() {
        return actions.get(actions.size() - 1);
    }

    public void addAction(PencilAction action) {
        actions.put(actions.size(), action);
    }

    public void clear() {
        actions.clear();
    }

    public int size() {
        return actions.size();
    }

    //TODO: Implement Buffer system
    public void recalculate(int index) { }
    
}
