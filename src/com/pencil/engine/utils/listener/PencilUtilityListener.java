package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.routines.engines.RenderEngine;
import com.pencil.engine.utils.events.PencilHistoryEvent;
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
        RenderEngine.render(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onShapeCreationEvent(PencilShapeEvent event) {
        event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SHAPE_CREATED.getMessage(),
                MessageService.MessageType.INFO, false));
    }

    //TODO: For Shape Generation -> Put action in AFTER completed render, otherwise we might end up with errors
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPencilHistoryEvent(PencilHistoryEvent event) {
        Pencil.getActionManager().update(Pencil.getPlayerService().getPlayer(event.getPlayer()), event.getAction());
    }

}
