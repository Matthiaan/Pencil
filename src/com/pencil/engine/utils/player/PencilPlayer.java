package com.pencil.engine.utils.player;

import org.bukkit.entity.Player;

public class PencilPlayer {

    public enum SelectionMode {
        NA, NORMAL, POLY
    }

    public enum FillMode {
        REGULAR, DIVIDE
    }

    private Player player;
    private PencilHistory history;

    private SelectionMode selectionMode;
    private int polyPointsLeft;

    public PencilPlayer(Player player) {
        this.player = player;

        history = new PencilHistory(player.getUniqueId());
        selectionMode = SelectionMode.NA;
        polyPointsLeft = 0;
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

    public int getPolyPointsLeft() {
        return polyPointsLeft;
    }

    public void setPolyPointsLeft(int polyPointsLeft) {
        this.polyPointsLeft = polyPointsLeft;
    }
}
