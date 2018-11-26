package com.pencil.engine.utils.service.manager;

import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.action.PencilNonUndoableAction;
import com.pencil.engine.utils.player.PencilPlayer;

import java.util.HashMap;

public class ActionManager extends Manager {

    private HashMap<PencilPlayer, HashMap<Integer, PencilAction>> ids;
    private String name = "action_manager";

    public ActionManager() {
        ids = new HashMap<>();
    }

    @Override
    public void run() { }

    @Override
    public String getName() {
        return name;
    }

    public void register(PencilPlayer player) {
        ids.put(player, new HashMap<>());
        ids.get(player).put(getNextID(player), new PencilNonUndoableAction(PencilAction.ActionType.INITIALIZED));
    }

    //TODO: Check for Action Overwrites!
    public void update(PencilPlayer player, PencilAction action) {
        player.getHistory().addAction(action);

        if (ids.get(player).containsKey(0)) {
            int i = ids.get(player).size();

            ids.get(player).put(i, action);
        }
    }

    public HashMap<Integer, PencilAction> getPlayerActionMap(PencilPlayer player) {
        return ids.get(player);
    }

    public int getNextID(PencilPlayer player) {
        return ids.get(player).size();
    }
}
