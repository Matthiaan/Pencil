package com.pencil.engine.utils.listener;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.events.shape.PencilPreShapeCreationEvent;
import com.pencil.engine.utils.player.PencilPlayer;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PencilMaterialListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onMaterialGUI(InventoryClickEvent event) {
        if (InterfaceUtils.isPencilInventory(event.getClickedInventory())) {
            Player player = (Player) event.getWhoClicked();
            PencilPlayer pencilPlayer = Pencil.getPlayerService().getPlayer(player);
            int slot = event.getSlot();

            if (event.getClickedInventory().getName().equals(Pencil.getPrefix() + ChatColor.GREEN + "Stone Types")) {
                if (slot < 45) {
                    ItemStack item = event.getClickedInventory().getItem(slot);

                    if (item.getType().isSolid()) {
                        Bukkit.getServer().getPluginManager().callEvent(new PencilPreShapeCreationEvent(
                                player,
                                Pencil.getSelectionManager().get(pencilPlayer, false),
                                Pencil.getShapeManager().get(pencilPlayer, false),
                                Pencil.getScaleManager().get(pencilPlayer, false),
                                item.getType()
                        ));
                    }
                }
            }
        }
    }

}
