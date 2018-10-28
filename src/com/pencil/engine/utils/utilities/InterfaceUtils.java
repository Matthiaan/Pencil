package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import com.pencil.engine.routines.engines.InterfaceEngine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class InterfaceUtils {

    public static Inventory createMenuInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Menu", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(21, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getItem(Material.DIAMOND_AXE, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand", ChatColor.RESET + ""));

        return gui;
    }

}
