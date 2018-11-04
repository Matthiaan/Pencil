package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PencilInterfaceListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInventory(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Menu")) {
                if (slot == 10) {
                    if (InterfaceUtils.hasPlace(player)) {
                        player.getInventory().addItem(ItemUtils.getWandItem());
                        player.closeInventory();

                        MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_ADDED_WAND_PENCIL.getMessage(),
                                MessageService.MessageType.INFO, false);
                    }
                } else if (slot == 21) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Point Selection")) {
                //TODO: See whether I can auto-add a pencil inventory closer so I don't always have to call player.closeInventory();
                if (slot == 10) {
                    pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.NORMAL);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("You can select either 1 or 2 positions!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 11) {
                    pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.POLY);

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface(Pencil.getPrefix() + ChatColor.GREEN + "Poly Point Selection",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 3, ChatColor.AQUA + "3 points", "")));
                } else if (slot == 15) {
                    pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.NA);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("You're Selection Mode has been reset!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Poly Point Selection")) {
                Inventory gui = event.getClickedInventory();
                ItemStack item = gui.getItem(22);

                if (slot == 21) {
                    item.setAmount(item.getAmount() - 1);
                    gui.setItem(22, item);

                    player.updateInventory();
                } else if (slot == 23) {
                    item.setAmount(item.getAmount() + 1);
                    gui.setItem(22, item);

                    player.updateInventory();
                } else if (slot == 37) {
                    player.closeInventory();
                } else if (slot == 43) {
                    pencilPlayer.setPolyPointsLeft(gui.getItem(22).getAmount());

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("You can select " + gui.getItem(22).getAmount() + " positions!",
                            MessageService.MessageType.INFO, false));
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Shape Types")) {

            }

            event.setResult(Event.Result.DENY);
        }
    }

}
