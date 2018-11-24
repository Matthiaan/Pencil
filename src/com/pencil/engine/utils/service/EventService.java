package com.pencil.engine.utils.service;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.PencilEvent;
import com.pencil.engine.utils.listener.PencilHotbarListener;
import com.pencil.engine.utils.listener.PencilInteractionListener;
import com.pencil.engine.utils.listener.PencilInterfaceListener;
import com.pencil.engine.utils.listener.PencilUtilityListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class EventService implements Listener {

    public EventService() {
        init();
    }

    private void init() {
        Bukkit.getServer().getPluginManager().registerEvents(new PencilHotbarListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilInteractionListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilInterfaceListener(), Pencil.getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new PencilUtilityListener(), Pencil.getPlugin());
    }

    public void queueEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void process(Player player, PencilEvent event) {
        Pencil.getActionManager().update(Pencil.getPlayerService().getPlayer(player), event.getAction());
    }

}
