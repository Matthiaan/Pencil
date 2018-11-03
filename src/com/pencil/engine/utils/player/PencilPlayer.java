package com.pencil.engine.utils.player;

import org.bukkit.entity.Player;

public class PencilPlayer {

    public enum SelectionMode {
        NA, NORMAL, POLY
    }

    private Player player;
    private PencilHistory history;

    private SelectionMode selectionMode;

    public PencilPlayer(Player player) {
        this.player = player;

        history = new PencilHistory(player.getUniqueId());
    }

    public Player getPlayer() {
        return player;
    }

    public PencilHistory getHistory() {
        return history;
    }

    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode;
    }
}
