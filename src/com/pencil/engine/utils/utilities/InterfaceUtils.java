package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import com.pencil.engine.routines.engines.InterfaceEngine;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InterfaceUtils {

    public static boolean hasPlace(Player player) {
        if (!(player.getInventory().firstEmpty() == -1)) {
            return true;
        } else {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_INVENTORY_FULL.getMessage(),
                    MessageService.MessageType.ERROR, true));

            return false;
        }
    }

    public static boolean isPencilInventory(Inventory inventory) {
        return inventory.getName().contains(Pencil.getPrefix());
    }

    public static Inventory createMenuInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Menu", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(21, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getItem(Material.DIAMOND_AXE, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand", ChatColor.RESET + ""));

        return gui;
    }

    public static Inventory createVectorInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Point Selection", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(21, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getCustomItem(Material.WOODEN_HOE, 0, (short) 1, 1, ChatColor.AQUA + "Singular Point Selection", ""));
        gui.setItem(11, ItemUtils.getCustomItem(Material.WOODEN_HOE, 0, (short) 2, 1, ChatColor.AQUA + "Poly Point Selection", ""));
        gui.setItem(16, ItemUtils.getItem(Material.FEATHER, 0, 1, ChatColor.AQUA + "Reset Point Selection", ""));

        return gui;
    }

    public static Inventory createScaleInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Point Selection", 5, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(30, ItemUtils.getExitItem());
        gui.setItem(13, ItemUtils.getItem(Material.STONE_BUTTON, 0, 3, ChatColor.AQUA + "3 Point Selection", ""));
        gui.setItem(12, ItemUtils.getSkullItem(1, "MHF_ArrowDown", ChatColor.AQUA + "Down", ""));
        gui.setItem(14, ItemUtils.getSkullItem(1, "MHF_ArrowUp", ChatColor.AQUA + "Up", ""));

        return gui;
    }

}
