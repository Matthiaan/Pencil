package com.pencil.engine.utils.player;

import com.pencil.engine.geometry.vector.Vector;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
    private ArrayList<Vector> positions;

    public PencilPlayer(Player player) {
        this.player = player;

        history = new PencilHistory(player.getUniqueId());

        selectionMode = SelectionMode.NA;
        positions = new ArrayList<>();
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

    public void updatePositions(ArrayList<Vector> positions) {
        this.positions = positions;
    }

    public ArrayList<Vector> getPositions() {
        return positions;
    }
}
