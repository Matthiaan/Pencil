package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.routines.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilShapeEvent;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.service.MessageService;
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
    public void onPreShapeCreationEvent(PencilShapePreProcessingEvent event) {
        event.getPlayer().sendMessage(MessageService.formatMessage("Received Request!",
                MessageService.MessageType.INFO, false));

        RenderEngine.render(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPreShapeCreationEvent(PencilShapeEvent event) {

    }

}
