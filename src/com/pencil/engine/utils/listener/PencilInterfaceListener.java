package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.utils.events.shape.PencilShapeFillRequestEvent;
import com.pencil.engine.utils.events.shape.PencilShapeScaleRequestEvent;
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

public class PencilInterfaceListener implements Listener {

    //TODO: Fix Multi-Clicks!
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
                    player.sendMessage(MessageService.formatMessage("You can select as many positions as you want!",
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
                if (!Pencil.getSelectionManager().hasSelection(pencilPlayer)) {
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_MAKE_SELECTION_VECTOR.getMessage(),
                            MessageService.MessageType.WARNING, true));
                    player.closeInventory();
                }

                //TODO: Make a way to determine when to reset!
                Selection selection = Pencil.getSelectionManager().get(pencilPlayer, false);

                if (slot == 10) {
                    Bukkit.getServer().getPluginManager().callEvent(new PencilShapeScaleRequestEvent(player, ShapeUtils.ShapeType.CUBE,
                            selection, null));

                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));

                    //TODO: Create a Scaling Interface
                    //TODO: -> Scaling -> Filled? -> Material
                } else if (slot == 11) {
                    Bukkit.getServer().getPluginManager().callEvent(new PencilShapeScaleRequestEvent(player, ShapeUtils.ShapeType.CUBOID,
                            selection, null));

                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 12) {
                    Bukkit.getServer().getPluginManager().callEvent(new PencilShapeScaleRequestEvent(player, ShapeUtils.ShapeType.PYRAMID,
                            selection, null));

                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 13) {
                    Bukkit.getServer().getPluginManager().callEvent(new PencilShapeScaleRequestEvent(player, ShapeUtils.ShapeType.PRISM,
                            selection, null));

                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 16) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Spherical Shapes")) {
                if (slot == 10) {

                } else if (slot == 11) {

                } else if (slot == 12) {

                } else if (slot == 16) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            }
            //TODO: Fix this mess of code!
            else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Shape Scale X")) {
                if (slot == 21) {
                    int a = event.getClickedInventory().getItem(22).getAmount() - 1;

                    if (a == 0) {
                        a = 1;
                    }

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 23) {
                    int a = event.getClickedInventory().getItem(22).getAmount() + 1;

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 43) {
                    Pencil.getScaleManager().set(pencilPlayer, event.getClickedInventory().getItem(23).getAmount());

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Y",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 37) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Shape Scale Y")) {
                if (slot == 21) {
                    int a = event.getClickedInventory().getItem(22).getAmount() - 1;

                    if (a == 0) {
                        a = 1;
                    }

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Y",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 23) {
                    int a = event.getClickedInventory().getItem(22).getAmount() + 1;

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Y",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 43) {
                    Pencil.getScaleManager().set(pencilPlayer, event.getClickedInventory().getItem(23).getAmount());

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Z",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, 1, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 37) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Shape Scale Z")) {
                if (slot == 21) {
                    int a = event.getClickedInventory().getItem(22).getAmount() - 1;

                    if (a == 0) {
                        a = 1;
                    }

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Z",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 23) {
                    int a = event.getClickedInventory().getItem(22).getAmount() + 1;

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Z",
                            ItemUtils.getItem(Material.STONE_BUTTON, 0, a, ChatColor.AQUA + "Scale of the shape")));
                } else if (slot == 43) {
                    Pencil.getScaleManager().set(pencilPlayer, event.getClickedInventory().getItem(23).getAmount());
                    Bukkit.getServer().getPluginManager().callEvent(new PencilShapeFillRequestEvent(player,
                            Pencil.getShapeManager().get(pencilPlayer, false),
                            Pencil.getSelectionManager().get(pencilPlayer, false),
                            Pencil.getScaleManager().get(pencilPlayer, false),
                            null));

                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createFilledShapeRequestInterface());
                } else if (slot == 37) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Filled Shape")) {
                boolean result = true;

                if (slot == 12) {
                    result = true;

                    //TODO: Create Material Interface Listener...
                } else if (slot == 14) {
                    result = false;

                    //TODO: Create Material Interface Listener...
                } else if (slot == 31) {
                    Pencil.reset(pencilPlayer, true, false, true);

                    player.closeInventory();
                }
            }

            event.setResult(Event.Result.DENY);
        }
    }

}