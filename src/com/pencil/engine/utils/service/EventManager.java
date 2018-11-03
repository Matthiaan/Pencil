package com.pencil.engine.utils.service;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.PencilEvent;
import com.pencil.engine.utils.events.PencilHotbarEvent;
import com.pencil.engine.utils.listener.PencilHotbarListener;
import com.pencil.engine.utils.listener.PencilInterfaceListener;
import com.pencil.engine.utils.listener.PencilUtilityListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventManager implements Listener {

    public void queueEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public EventManager() {
        init();
    }

    private void init() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Pencil.getPlugin());

        Bukkit.getServer().getPluginManager().registerEvents(new PencilHotbarListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilInterfaceListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilInterfaceListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilUtilityListener(), Pencil.getPlugin());
    }

    public void process(Player player, PencilEvent event) {
        Pencil.getPlayerService().getPlayer(player).getHistory().addAction(event.getAction());
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onInteraction(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            queueEvent(new PencilHotbarEvent(event.getPlayer(), event.getItem()));
        }
    }

}
