package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilHotbarEvent;
import com.pencil.engine.utils.events.PencilVectorSelectionEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import com.pencil.engine.utils.utilities.ToolUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PencilInteractionListener implements Listener {

    private HashMap<PencilPlayer, ArrayList<Vector>> cached = new HashMap<>();
    private HashMap<PencilPlayer, Vector> rulers = new HashMap<>();

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInteraction(PlayerInteractEvent event) {
        try {
            if (ItemUtils.isPencilItem(event.getItem())) {
                if (ItemUtils.matches(event.getItem(), ItemUtils.getWandItem())) {
                    PencilPlayer player = Pencil.getPlayerService().getPlayer(event.getPlayer());
                    Action action = event.getAction();
                    PencilPlayer.SelectionMode mode = player.getMode();

                    if (player.getToolType().equals(ToolUtils.ToolType.RULER)) {
                        Vector vector = new Vector(event.getClickedBlock().getLocation());

                        if (rulers.containsKey(player)) {
                            Vector previous = rulers.get(player);
                            ToolUtils.measure(player, previous, vector);

                            rulers.remove(player);
                        } else {
                            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_RULER_FIRST_POSITION.getMessage(),
                                    MessageService.MessageType.INFO, false));

                            rulers.put(player, vector);
                        }
                    } else {
                        //TODO: Optimize this mess
                        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                            Vector vector = new Vector(event.getClickedBlock().getLocation());

                            if (mode != PencilPlayer.SelectionMode.NA) {
                                if (mode == PencilPlayer.SelectionMode.VECTOR) {

                                    //We want to reset the position every time!
                                    event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                            MessageService.MessageType.INFO, false));
                                    event.getPlayer().sendMessage(MessageService.formatMessage(ChatColor.GREEN + "Position: " + vector.toString(),
                                            MessageService.MessageType.INFO, false));

                                    Pencil.getVectorManager().addVector(player, vector);
                                } else if (mode == PencilPlayer.SelectionMode.DOUBLE) {
                                    //We want to store only 1 Vector in cache, than return it to the Vector Manager

                                    if (cached.containsKey(player)) {
                                        if (cached.get(player).size() == 2) {
                                            cached.remove(player);
                                            cached.put(player, new ArrayList<>(Collections.singleton(vector)));

                                            event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                                    MessageService.MessageType.INFO, false));
                                        } else {
                                            Vector cachedVector = cached.get(player).get(0);

                                            event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_SECOND_POSITION_SET.getMessage(),
                                                    MessageService.MessageType.INFO, false));

                                            Pencil.getVectorManager().addCuboid(player, cachedVector, vector);
                                        }

                                        event.getPlayer().sendMessage(MessageService.formatMessage(ChatColor.GREEN + "Position: " + vector.toString(),
                                                MessageService.MessageType.INFO, false));
                                    } else {
                                        //We want to keep updating the Vector Manager
                                        event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                                MessageService.MessageType.INFO, false));
                                        event.getPlayer().sendMessage(MessageService.formatMessage(ChatColor.GREEN + "Position: " + vector.toString(),
                                                MessageService.MessageType.INFO, false));

                                        cached.put(player, new ArrayList<>(Collections.singleton(vector)));
                                    }
                                } else if (mode == PencilPlayer.SelectionMode.MULTI) {
                                    event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_POSITION_SET.getMessage(),
                                            MessageService.MessageType.INFO, false));
                                    event.getPlayer().sendMessage(MessageService.formatMessage(ChatColor.GREEN + "Position: " + vector.toString(),
                                            MessageService.MessageType.INFO, false));

                                    cached.get(player).add(vector);

                                    Pencil.getVectorManager().addPolygon(player, cached.get(player));
                                }
                            } else {
                                player.getPlayer().openInventory(InterfaceUtils.createVectorInterface());
                            }

                            event.setCancelled(true);
                            Pencil.getEventService().queueEvent(new PencilVectorSelectionEvent(player.getPlayer(), vector));
                        } else {
                            //It's a click in the air!
                            player.getPlayer().openInventory(InterfaceUtils.createWandMenu());
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) { }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHotbarInteraction(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        if (event.getItem().getItemMeta().getDisplayName().contains(Pencil.getPrefix())) {
            Pencil.getEventService().queueEvent(new PencilHotbarEvent(event.getPlayer(), event.getItem()));
        }
    }

}
