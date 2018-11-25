package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilShapePreProcessingEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.*;
import com.pencil.engine.geometry.selection.Selection;
import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.event.EventHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class PencilInterfaceListener implements Listener {

    //TODO: Fix Multi-Clicks!
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPencilDialog(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().contains("Menu")) {
                if (slot == 10) {
                    if (InterfaceUtils.hasPlace(player)) {
                        player.getInventory().addItem(ItemUtils.getWandItem());
                        player.closeInventory();
                        pencilPlayer.setToolType(ToolUtils.ToolType.REGULAR);

                        MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_ADDED_WAND_PENCIL.getMessage(),
                                MessageService.MessageType.INFO, false);
                    }
                } else if (slot == 11) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createVectorInterface());
                } else if (slot == 12) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createToolsMenu());
                } else if (slot == 13) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createPlayerHistory(pencilPlayer));
                } else if (slot == 22) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().contains("Wand Options")) {
                if (slot == 10) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createVectorInterface());
                } else if (slot == 11) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createShapeTypeInterface());
                } else if (slot == 22) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().contains("Position Selection")) {
                if (slot == 10) {
                    pencilPlayer.setMode(PencilPlayer.SelectionMode.VECTOR);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("Whenever selecting more than 1 position, the previous stored position will be overwritten!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 11) {
                    pencilPlayer.setMode(PencilPlayer.SelectionMode.DOUBLE);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("Whenever selecting more than 2 positions, the previous stored positions will be overwritten!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 12) {
                    pencilPlayer.setMode(PencilPlayer.SelectionMode.MULTI);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("Pencil will keep storing positions until these have been reset!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 14) {
                    Pencil.getSelectionManager().remove(pencilPlayer);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("Your stored positions have been reset!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 15) {
                    pencilPlayer.setMode(PencilPlayer.SelectionMode.NA);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage("Your Selection Mode has been reset!",
                            MessageService.MessageType.INFO, false));
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else  if (event.getClickedInventory().getName().contains("Pencil Tools")) {
                if (slot == 10) {
                    pencilPlayer.setToolType(ToolUtils.ToolType.REGULAR);

                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_REGULAR_TOOL.getMessage(),
                            MessageService.MessageType.INFO, false));
                } else if (slot == 11) {
                    pencilPlayer.setToolType(ToolUtils.ToolType.RULER);

                    player.closeInventory();
                    player.closeInventory();
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_RULER_TOOL.getMessage(),
                            MessageService.MessageType.INFO, false));
                } else if (slot == 16) {
                    player.closeInventory();
                }
            }

            //TODO: Fix this mess of code!
            //TODO: Fix cursor reset!
            else if (event.getClickedInventory().getName().contains("Filled Shape")) {
                if (slot == 12) {
                    pencilPlayer.getCurrentRequest().setFilled(false);
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getStone());
                } else if (slot == 14) {
                    pencilPlayer.getCurrentRequest().setFilled(true);
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getStone());
                } else if (slot == 31) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            }

            event.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPencilShape(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().contains("Shape Types")) {
                if (slot == 10) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createCuboidShapesInterface());
                } else if (slot == 11) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createSphericalShapesInterface());
                } else if (slot == 12) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getStone());
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().contains("Cuboid Shapes")) {
                ShapeUtils.PositionSetType type = ShapeUtils.getType(Pencil.getSelectionManager().get(pencilPlayer));

                if (type == null) {
                    //We are using the player's position now!
                    //TODO: Implement an option to enable/disable this!
                    type = ShapeUtils.PositionSetType.SINGLE;

                    Vector vector = new Vector(pencilPlayer.getPlayer().getLocation());
                    Pencil.getVectorManager().addVector(pencilPlayer, vector);
                }

                if (slot == 10) {
                    //TODO: Shape -> Scaling/Height/Radii -> Filled? -> Material -> Render
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBE);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            //TODO: Better inventory name?
                            player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Size of one side of your cube.")));

                            break;
                        case DOUBLE:
                            player.sendMessage(MessageService.formatMessage("You can't create a cube when you have 2 positions stored!",
                                    MessageService.MessageType.ERROR, true));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("You can't create a cube when you have 2 positions stored!",
                                    MessageService.MessageType.ERROR, true));

                            break;
                    }
                } else if (slot == 11) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Width of your cuboid.")));

                            break;
                        case DOUBLE:
                            ArrayList<Vector> dVectors = Pencil.getSelectionManager().get(pencilPlayer, Selection.SelectionType.CUBOID).getVectors();

                            if (dVectors == null) {
                                System.out.println("error InterfaceUtils 160");

                                return;
                            }

                            Vector dMin = dVectors.get(0);
                            Vector dMax = dVectors.get(1);
                            CuboidSelection selection = new CuboidSelection(dMin, dMax, player.getWorld());

                            pencilPlayer.getCurrentRequest().setSelection(selection);
                            player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                            player.sendMessage(MessageService.formatMessage("This feature might not be working correctly yet!",
                                    MessageService.MessageType.INFO, false));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 12) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.PYRAMID);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale X",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Width of your pyramid.")));

                            break;
                        case DOUBLE:
                            //TODO: Rework this
                            ArrayList<Vector> dVectors = Pencil.getSelectionManager().get(pencilPlayer, Selection.SelectionType.CUBOID).getVectors();

                            if (dVectors == null) {
                                System.out.println("error InterfaceUtils 196");

                                return;
                            }

                            Vector dMin = dVectors.get(0);
                            Vector dMax = dVectors.get(1);

                            //Calculating so that min and max are contained inside of A CUBE! (Min is already in)
                            //The pyramid will be based on a square ground plane!
                            int p = Math.abs(dMax.getBlockX() - dMin.getBlockX());

                            CuboidSelection mSelection = new CuboidSelection(dMin, new Vector(p, p, p), player.getWorld());

                            pencilPlayer.getCurrentRequest().setSelection(mSelection);
                            player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                            player.sendMessage(MessageService.formatMessage("This feature might not be working correctly yet!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 13) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.PRISM);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                        case DOUBLE:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 16) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().contains("Spherical Shapes")) {
                ShapeUtils.PositionSetType type = ShapeUtils.getType(Pencil.getSelectionManager().get(pencilPlayer));

                if (type == null) {
                    //We are using the player's position now!
                    //TODO: Implement an option to enable/disable this!
                    type = ShapeUtils.PositionSetType.SINGLE;

                    Vector vector = new Vector(pencilPlayer.getPlayer().getLocation());
                    Pencil.getVectorManager().addVector(pencilPlayer, vector);
                }

                if (slot == 10) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.SPHERE);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.openInventory(InterfaceUtils.createScaleInterface("General Radius",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Radius of the sphere.")));

                            break;
                        case DOUBLE:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a next update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a next update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 11) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.ELLIPSOID);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.openInventory(InterfaceUtils.createScaleInterface("Radius X",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "X Radius of the sphere.")));

                            break;
                        case DOUBLE:
                            ArrayList<Vector> dVectors = Pencil.getSelectionManager().get(pencilPlayer, Selection.SelectionType.CUBOID).getVectors();

                            if (dVectors == null) {
                                System.out.println("error InterfaceUtils 293");

                                return;
                            }

                            Vector dMin = dVectors.get(0);
                            Vector dMax = dVectors.get(1);

                            CuboidSelection dSelection = new CuboidSelection(dMin, dMax, player.getWorld());

                            pencilPlayer.getCurrentRequest().setSelection(dSelection);
                            player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                            player.sendMessage(MessageService.formatMessage("This feature might not be working correctly yet!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a next update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 12) {
                    pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CYLINDER);
                    player.closeInventory();

                    switch (type) {
                        case SINGLE:
                            player.openInventory(InterfaceUtils.createScaleInterface("General Radius",
                                    ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Radius of the cylinder.")));
                        case DOUBLE:
                            ArrayList<Vector> dVectors = Pencil.getSelectionManager().get(pencilPlayer, Selection.SelectionType.CUBOID).getVectors();

                            if (dVectors == null) {
                                System.out.println("error InterfaceUtils 160");

                                return;
                            }

                            Vector dMin = dVectors.get(0);
                            Vector dMax = dVectors.get(1);

                            int p = Math.abs(dMax.getBlockX() - dMin.getBlockX());

                            CuboidSelection dSelection = new CuboidSelection(dMin, new Vector(p, p, p), player.getWorld());

                            //TODO: We should skip directly to the "filled-shape"-dialog
                            pencilPlayer.getCurrentRequest().setSelection(dSelection);
                            player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());

                            break;
                        case MULTI:
                            player.sendMessage(MessageService.formatMessage("This feature will be available in a next update!",
                                    MessageService.MessageType.WARNING, true));

                            break;
                    }
                } else if (slot == 16) {
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPencilScale(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().contains("General Scale")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    pencilPlayer.getCurrentRequest().setScale(new Vector(
                            event.getInventory().getItem(22).getAmount(),
                            event.getInventory().getItem(22).getAmount(),
                            event.getInventory().getItem(22).getAmount()));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Shape Scale X")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    String type = pencilPlayer.getCurrentRequest().getType().toString().toLowerCase();

                    pencilPlayer.getCurrentRequest().setScale(new Vector(event.getInventory().getItem(22).getAmount(), 0, 0));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Y",
                            ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Height of your " + type + ".")));
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Shape Scale Y")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    String type = pencilPlayer.getCurrentRequest().getType().toString().toLowerCase();

                    if (pencilPlayer.getCurrentRequest().getScale() == null) {
                        player.closeInventory();
                        player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SOMETHING_WENT_WRONG.getMessage(),
                                MessageService.MessageType.ERROR, true));

                        return;
                    }

                    pencilPlayer.getCurrentRequest().setScale(pencilPlayer.getCurrentRequest().getScale().add(new Vector(0, event.getInventory().getItem(22).getAmount(), 0)));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Shape Scale Z",
                            ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Length of your " + type + ".")));
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Shape Scale Z")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    if (pencilPlayer.getCurrentRequest().getScale() == null) {
                        player.closeInventory();
                        player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_SOMETHING_WENT_WRONG.getMessage(),
                                MessageService.MessageType.ERROR, true));

                        return;
                    }

                    pencilPlayer.getCurrentRequest().getScale().add(pencilPlayer.getCurrentRequest().getScale().add(new Vector(0, 0, event.getInventory().getItem(22).getAmount())));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("General Radius")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    pencilPlayer.getCurrentRequest().setScale(new Vector(event.getInventory().getItem(22).getAmount(),
                            event.getInventory().getItem(22).getAmount(),
                            event.getInventory().getItem(22).getAmount()));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Radius X")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    String type = pencilPlayer.getCurrentRequest().getType().toString().toLowerCase();

                    pencilPlayer.getCurrentRequest().setScale(new Vector(event.getInventory().getItem(22).getAmount(), 0, 0));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Radius Y",
                            ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Y Radius of the " + type + ".")));
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Radius Y")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    String type = pencilPlayer.getCurrentRequest().getType().toString().toLowerCase();

                    pencilPlayer.getCurrentRequest().setScale(pencilPlayer.getCurrentRequest().getScale().add(0, event.getInventory().getItem(22).getAmount(), 0));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createScaleInterface("Radius Z",
                            ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Z Radius of the " + type + ".")));
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Radius Z")) {
                if (slot == 21) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() - 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() - 1) + "")));
                    player.updateInventory();
                } else if (slot == 23) {
                    ItemStack item = event.getClickedInventory().getItem(22);

                    event.getClickedInventory().setItem(22, ItemUtils.changeMeta(item, item.getAmount() + 1, ChatColor.AQUA + ("Current Scale = " + (item.getAmount() + 1) + "")));
                    player.updateInventory();
                } else if (slot == 43) {
                    pencilPlayer.getCurrentRequest().setScale(pencilPlayer.getCurrentRequest().getScale().add(new Vector(0, 0, event.getInventory().getItem(22).getAmount())));
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createFilledShapeDialogInterface());
                } else if (slot == 37) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPencilMaterial(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().contains("Stone Types")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getRandom());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getNatural());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Natural Materials")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getStone());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getWood());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Woods")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getNatural());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getSlab());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Slabs & Stairs")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getWood());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcOne());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Colored Items 1")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getSlab());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcTwo());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Colored Items 2")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcOne());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcThree());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Colored Items 3")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcTwo());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getSea());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Sea Materials")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (pencilPlayer.getCurrentRequest() == null) {
                        pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                        pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                        pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                        pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                        pencilPlayer.getCurrentRequest().setFilled(true);

                        Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                    } else {
                        pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getcThree());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getRandom());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Random Materials")) {
                if (slot < 45 && !event.getClickedInventory().getItem(slot).getType().equals(Material.AIR)) {
                    if (slot == 10) {
                        if (pencilPlayer.getCurrentRequest() == null) {
                            pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                            pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                            pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                            pencilPlayer.getCurrentRequest().setMaterial(Material.AIR);
                            pencilPlayer.getCurrentRequest().setFilled(true);

                            Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                        } else {
                            pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                        }
                    } else {
                        if (pencilPlayer.getCurrentRequest() == null) {
                            pencilPlayer.setShapeRequest(ShapeUtils.ShapeType.CUBOID);
                            pencilPlayer.getCurrentRequest().setSelection(Pencil.getSelectionManager().get(pencilPlayer));
                            pencilPlayer.getCurrentRequest().setScale(new Vector(0, 0, 0));
                            pencilPlayer.getCurrentRequest().setMaterial(event.getInventory().getItem(slot).getType());
                            pencilPlayer.getCurrentRequest().setFilled(true);

                            Pencil.getEventService().queueEvent(new PencilShapePreProcessingEvent(player, pencilPlayer.getCurrentRequest()));
                        } else {
                            pencilPlayer.getCurrentRequest().isApplicableMaterial(event.getClickedInventory().getItem(slot).getType(), true);
                        }
                    }

                    player.closeInventory();
                } else if (slot == 45) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getSea());
                } else if (slot == 53) {
                    player.closeInventory();
                    player.openInventory(Pencil.getMaterials().getStone());
                } else if (slot == 49) {
                    player.closeInventory();
                    player.openInventory(InterfaceUtils.createResetDialogInterface());
                }
            } else if (event.getClickedInventory().getName().contains("Reset Request")) {
                if (slot == 12) {
                    player.closeInventory();
                } else if (slot == 14) {
                    player.closeInventory();
                    pencilPlayer.setShapeRequest(null);

                    Pencil.getSelectionManager().remove(pencilPlayer);
                } else if (slot == 31) {
                    player.closeInventory();
                }

                event.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPencilHistory(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().contains("History")) {
                if (slot <= 17) {
                    player.closeInventory();

                    Inventory action = InterfaceUtils.createActionHistory(pencilPlayer, slot);

                    if (action == null) {
                        player.openInventory(InterfaceUtils.createPlayerHistory(pencilPlayer));
                    } else {
                        player.openInventory(action);
                    }
                } else if (slot == 22) {
                    player.closeInventory();
                }
            } else if (event.getClickedInventory().getName().contains(player.getPlayer().getName())) {
                if (event.getClickedInventory().getName().contains("Action")) {
                    if (slot == 12) {
                        PencilAction action = Pencil.getActionManager().getPlayerActionMap(pencilPlayer).get(event.getClickedInventory().getItem(slot).getAmount());

                        if (action.isUndoable()) {
                            action.undo(player);
                        }

                        player.closeInventory();
                        player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_UNDONE.getMessage(),
                                MessageService.MessageType.INFO, false));
                    } else if (slot == 14) {
                        //TODO: Create Redo!
                        player.closeInventory();
                        player.sendMessage(MessageService.formatMessage("This feature will be available in a future update!",
                                MessageService.MessageType.WARNING, true));
                    } else if (slot == 18) {
                        player.closeInventory();
                        player.openInventory(InterfaceUtils.createPlayerHistory(pencilPlayer));
                    } else if (slot == 26) {
                        player.closeInventory();
                    }
                }
            }
        }
    }
}