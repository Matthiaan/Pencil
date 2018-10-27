package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.exception.InventorySizeException;
import com.pencil.engine.routines.engines.InterfaceEngine;
import com.pencil.engine.utils.events.PencilHotbarEvent;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PencilHotbarListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onHotbarEvent(PencilHotbarEvent event) {
        if (ItemUtils.matchesRoughly(event.getItem(),
                ItemUtils.getItem(Material.COMPASS, 0, 1, Pencil.getPrefix() + ChatColor.GREEN + "Menu"))) {
            try {
                PencilInventory gui = InterfaceEngine.buildMainMenu(InterfaceEngine.build(Pencil.getPrefix() + ChatColor.GREEN + "Menu", 3, 9,
                        PencilInventory.InventoryType.CLOSEABLE,
                        PencilInventory.InventoryType.FILLABLE));

                event.getPlayer().openInventory(gui.getMainInterface());
            } catch (InventorySizeException ex) {
                ex.printStackTrace();
            }
        }
    }

}
