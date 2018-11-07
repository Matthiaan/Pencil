package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.shape.PencilPreShapeCreationEvent;
import com.pencil.engine.utils.events.shape.PencilShapeFillRequestEvent;
import com.pencil.engine.utils.events.shape.PencilShapeScaleRequestEvent;
import com.pencil.engine.utils.player.PencilPlayer;
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
    public void onPreShapeCreationEvent(PencilPreShapeCreationEvent event) {
        //TODO: Here I want to generate the shape
    }

}
