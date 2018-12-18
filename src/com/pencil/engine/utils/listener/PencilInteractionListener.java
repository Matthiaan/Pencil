package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.geometry.selection.CuboidSelection;
import com.pencil.engine.geometry.selection.VectorSelection;
import com.pencil.engine.geometry.vector.Vector;
import com.pencil.engine.utils.events.PencilVectorSelectionEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import com.pencil.engine.utils.utilities.ItemUtils;
import com.pencil.engine.utils.utilities.ToolUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class PencilInteractionListener implements Listener {

    private HashMap<PencilPlayer, Vector> vectors = new HashMap<>();
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
                                    MessageService.MessageType.INFO));

                            rulers.put(player, vector);
                        }
                    } else if (player.getToolType().equals(ToolUtils.ToolType.REGULAR)) {
                        //TODO: Optimize this mess
                        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                            Vector vector = new Vector(event.getClickedBlock().getLocation());

                            if (mode != PencilPlayer.SelectionMode.NA) {
                                if (mode == PencilPlayer.SelectionMode.VECTOR) {
                                    Pencil.getSelectionManager().update(player, new VectorSelection(vector));
                                    player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_POSITION_SET.getMessage(),
                                            MessageService.MessageType.INFO));
                                    player.getPlayer().sendMessage(MessageService.formatMessage("Position set at " + vector.toString(),
                                            MessageService.MessageType.INFO));
                                } else if (mode == PencilPlayer.SelectionMode.DOUBLE) {
                                    if (vectors.containsKey(player)) {
                                        Pencil.getSelectionManager().update(player, new CuboidSelection(vectors.get(player), vector, player.getPlayer().getWorld()));

                                        vectors.remove(player);
                                        player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_SECOND_POSITION_SET.getMessage(),
                                                MessageService.MessageType.INFO));
                                        player.getPlayer().sendMessage(MessageService.formatMessage("Position set at " + vector.toString(),
                                                MessageService.MessageType.INFO));
                                    } else {
                                        vectors.put(player, vector);
                                        player.getPlayer().sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_FIRST_POSITION_SET.getMessage(),
                                                MessageService.MessageType.INFO));
                                        player.getPlayer().sendMessage(MessageService.formatMessage("Position set at " + vector.toString(),
                                                MessageService.MessageType.INFO));
                                    }
                                } else {
                                    //IT's Multi
                                    player.getPlayer().sendMessage(MessageService.formatMessage( "This feature will be available in a future update!",
                                            MessageService.MessageType.WARNING));
                                    player.getPlayer().openInventory(InterfaceUtils.createVectorInterface());
                                }

                                Pencil.getEventService().queueEvent(new PencilVectorSelectionEvent(player.getPlayer(), vector));
                            } else {
                                player.getPlayer().openInventory(InterfaceUtils.createVectorInterface());
                            }

                            event.setCancelled(true);
                        } else {
                            //It's a click in the air!
                            player.getPlayer().openInventory(InterfaceUtils.createWandMenu());
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) { }
    }

}
