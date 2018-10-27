package com.pencil.engine.routines.engines;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InterfaceEngine {

    public static Inventory createInventory(String name, int rows, int columns) {
        return Bukkit.createInventory(null, (rows * columns) - 1, name);
    }

    public static void displayInventory(Player player, Inventory inventory) {
        player.closeInventory();
        player.openInventory(inventory);
        player.updateInventory();
    }

}
