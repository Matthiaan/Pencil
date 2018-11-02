package com.pencil.engine.utils.listener;

import com.pencil.engine.utils.events.PencilHotbarEvent;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PencilHotbarListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onHotbarEvent(PencilHotbarEvent event) {
        if (ItemUtils.matches(event.getItem(), ItemUtils.getMenuItem())) {
            event.getPlayer().openInventory(InterfaceUtils.createMenuInterface());
        }

        if (ItemUtils.matches(event.getItem(), ItemUtils.getWandItem())) {
            event.getPlayer().openInventory(InterfaceUtils.createVectorInterface());
        }
    }

}
