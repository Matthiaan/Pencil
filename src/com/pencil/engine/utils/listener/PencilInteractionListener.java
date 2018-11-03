package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.vector.Vector;
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
import java.util.HashMap;

public class PencilInteractionListener implements Listener {

    private HashMap<PencilPlayer, Vector> normalSelections = new HashMap<>();
    private HashMap<PencilPlayer, ArrayList<Vector>> polySelections = new HashMap<>();

    @EventHandler (priority = EventPriority.MONITOR)
    public void onInteraction(PlayerInteractEvent event) {
        if (event.getItem().getItemMeta().getDisplayName().contains(Pencil.getPrefix())) {
            if (event.getItem() == ItemUtils.getWandItem()) {
                PencilPlayer player = Pencil.getPlayerService().getPlayer(event.getPlayer());
                Action action = event.getAction();

                if (player.getSelectionMode() == PencilPlayer.SelectionMode.NA) {
                    if (ItemUtils.matches(event.getItem(), ItemUtils.getWandItem())) {
                        event.getPlayer().openInventory(InterfaceUtils.createVectorInterface());
                    }
                } else if (player.getSelectionMode() == PencilPlayer.SelectionMode.NORMAL) {
                    if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                        Vector vector = new Vector(event.getClickedBlock().getLocation());

                        if (normalSelections.containsKey(player)) {
                            Vector previous = normalSelections.get(player);

                            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_SECOND_POSITION_SET + vector.toString(),
                                    MessageService.MessageType.INFO, false));

                            //TODO: Save this in a selection!
                        } else {
                            normalSelections.put(player, vector);
                            player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET + vector.toString(),
                                    MessageService.MessageType.INFO, false));
                        }

                        Bukkit.getServer().getPluginManager().callEvent(new PencilVectorSelectionEvent(player.getPlayer(), vector));
                    } else if (action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR) {
                    }
                } else if (player.getSelectionMode() == PencilPlayer.SelectionMode.POLY) {
                    if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                        Vector vector = new Vector(event.getClickedBlock().getLocation());
                        int index = 0;

                        if (polySelections.containsKey(player)) {
                            polySelections.get(player).add(vector);

                            index = polySelections.get(player).size();
                        } else {
                            polySelections.put(player, new ArrayList<>());
                            polySelections.get(player).add(vector);

                            index = 1;
                        }


                        player.getPlayer().sendMessage(MessageService.formatMessage("Position " + index + " has been set! " + vector.toString(),
                                MessageService.MessageType.INFO, false));

                        Bukkit.getServer().getPluginManager().callEvent(new PencilVectorSelectionEvent(player.getPlayer(), vector));
                    } else if (action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR) {

                    }
                }
            }
        }
    }

}
