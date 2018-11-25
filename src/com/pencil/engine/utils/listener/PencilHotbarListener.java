package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PencilHotbarListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onHotbarEvent(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        if (!event.getItem().getItemMeta().getDisplayName().contains(Pencil.getPrefix())) {
            return;
        }

        if (ItemUtils.matches(event.getItem(), ItemUtils.getMenuItem())) {
            event.getPlayer().openInventory(InterfaceUtils.createMenuInterface());
        }
    }

}
