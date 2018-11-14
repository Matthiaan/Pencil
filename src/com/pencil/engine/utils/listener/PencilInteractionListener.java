package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilHotbarEvent;
import com.pencil.engine.utils.events.PencilVectorSelectionEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.Bukkit;
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

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInteraction(PlayerInteractEvent event) {
        if (event.getItem().getItemMeta().getDisplayName().contains(Pencil.getPrefix())) {
            if (ItemUtils.matches(event.getItem(), ItemUtils.getWandItem())) {
                PencilPlayer player = Pencil.getPlayerService().getPlayer(event.getPlayer());
                Action action = event.getAction();
                PencilPlayer.SelectionMode mode = player.getMode();

                //TODO: Optimize this mess
                if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                    Vector vector = new Vector(event.getClickedBlock().getLocation());

                    if (mode != PencilPlayer.SelectionMode.NA) {
                        if (mode == PencilPlayer.SelectionMode.VECTOR) {
                            System.out.println("Vector Selection");

                            //We want to reset the position every time!
                            event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                    MessageService.MessageType.INFO, false));

                            Pencil.getVectorManager().add(player, new ArrayList<>(Collections.singleton(vector)));
                        } else if (mode == PencilPlayer.SelectionMode.DOUBLE) {
                            //We want to store only 1 Vector in cache, than return it to the Vector Manager

                            if (cached.containsKey(player)) {
                                cached.get(player).add(vector);

                                event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_SECOND_POSITION_SET.getMessage(),
                                        MessageService.MessageType.INFO, false));

                                Pencil.getVectorManager().add(player, new ArrayList<>(cached.get(player)));
                            } else {
                                //We want to keep updating the Vector Manager
                                event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                        MessageService.MessageType.INFO, false));

                                cached.put(player, new ArrayList<>(Collections.singleton(vector)));
                            }
                        } else if (mode == PencilPlayer.SelectionMode.MULTI) {
                            event.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_POSITION_SET.getMessage(),
                                    MessageService.MessageType.INFO, false));

                            Pencil.getVectorManager().update(player, vector);
                        }
                    }

                    Bukkit.getServer().getPluginManager().callEvent(new PencilVectorSelectionEvent(player.getPlayer(), vector));
                } else {
                    //It's a click in the air!
                    System.out.println("Got here!");

                    InterfaceUtils.createWandMenu();
                }
            }
        }
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
