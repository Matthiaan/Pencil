package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PencilInterfaceListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInventory(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Menu")) {
                if (slot == 10) {
                    if (InterfaceUtils.hasPlace(player)) {
                        player.getInventory().addItem(ItemUtils.getWandItem());

                        MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_ADDED_WAND_PENCIL.getMessage(),
                                MessageService.MessageType.INFO, false);
                    }
                } else if (slot == 21) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Point Selection")) {
                if (slot == 10) {

                } else if (slot == 11) {

                } else if (slot == 12) {

                } else if (slot == 21) {
                    player.closeInventory();
                }
            }

            event.setResult(Event.Result.DENY);
        }
    }

}
