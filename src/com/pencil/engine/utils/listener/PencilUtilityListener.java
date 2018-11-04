package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.PencilShapeFillRequestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PencilUtilityListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Pencil.getPlayerService().addPlayer(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Pencil.getPlayerService().removePlayer(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onShapeFillRequest(PencilShapeFillRequestEvent event) {

    }

}
