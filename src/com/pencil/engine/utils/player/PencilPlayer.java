package com.pencil.engine.utils.player;

import org.bukkit.entity.Player;

public class PencilPlayer {

    private Player player;
    private PencilHistory history;

    public PencilPlayer(Player player) {
        this.player = player;
        this.history = new PencilHistory(player.getUniqueId());
    }

    public Player getPlayer() {
        return player;
    }

    public PencilHistory getHistory() {
        return history;
    }
}
