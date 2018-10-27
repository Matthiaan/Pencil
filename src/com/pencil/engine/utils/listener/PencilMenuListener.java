package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PencilMenuListener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onDefaultMenu(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(Pencil.getPrefix() + ChatColor.GREEN + "Menu")) {
            int slot = event.getSlot();


        }
    }

}
