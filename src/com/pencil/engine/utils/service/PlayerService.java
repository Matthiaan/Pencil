package com.pencil.engine.utils.service;

import com.pencil.engine.utils.player.PencilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerService {

    private HashMap<Player, PencilPlayer> players;

    public PlayerService() {
        players = new HashMap<>();
    }

    public void init() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }
    }

    public void flush() {
        players.clear();
    }

    public void addPlayer(Player player) {
        if (!players.containsKey(player)) {
            PencilPlayer pencilPlayer = new PencilPlayer(player);

            pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.NA);

            players.put(player, pencilPlayer);
        }
    }

    public void removePlayer(Player player) {
        if (!players.containsKey(player)) {
            players.remove(player);
        }
    }

    public PencilPlayer getPlayer(Player player) {
        if (players.containsKey(player)) {
            return players.get(player);
        }

        return null;
    }
}
