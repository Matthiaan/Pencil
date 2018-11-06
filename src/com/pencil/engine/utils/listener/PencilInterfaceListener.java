package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.PencilShapeFillRequestEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import com.pencil.engine.utils.utilities.ShapeUtils;
import org.bukkit.Bukkit;
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
                    player.sendMessage(MessageService.formatMessage("You can select either 1 or 2 positions! Positions will automatically reset after selecting more than 2 positions!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 11) {
                    pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.POLY);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("You can sas many positions as you want!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 15) {
                    pencilPlayer.setSelectionMode(PencilPlayer.SelectionMode.NA);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("You're Selection Mode has been reset!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Shape Types")) {
                if (slot == 10) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createCuboidShapesInterface());
                } else if (slot == 11) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createSphericalShapesInterface());
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Cuboid Shapes")) {
                if (slot == 10) {
                    //TODO: Create a Scaling Interface
                } else if (slot == 11) {

                } else if (slot == 12) {

                } else if (slot == 13) {

                } else if (slot == 16) {
                    //TODO: Call a PencilResetEvent
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Spherical Shapes")) {
                if (slot == 10) {

                } else if (slot == 11) {

                } else if (slot == 12) {

                } else if (slot == 16) {
                    //TODO: Call a PencilResetEvent
                    player.closeInventory();
                }
            }

            event.setResult(Event.Result.DENY);
        }
    }

}
