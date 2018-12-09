package com.pencil.engine.pipeline.engines;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceEngine {

    public static Inventory createInventory(String name, int slots) {
        return Bukkit.createInventory(null, slots, name);
    }

    public static void displayInventory(Player player, Inventory inventory) {
        player.closeInventory();
        player.openInventory(inventory);
        player.updateInventory();
    }

    public static void fillInventory(Inventory inventory, ItemStack item) {
        while (inventory.firstEmpty() != -1) {
            inventory.setItem(inventory.firstEmpty(), item);
        }
    }

}
