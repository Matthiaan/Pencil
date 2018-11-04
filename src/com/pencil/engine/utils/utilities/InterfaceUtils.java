package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import com.pencil.engine.routines.engines.InterfaceEngine;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        gui.setItem(10, ItemUtils.getItem(Material.DIAMOND_AXE, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand"));
        gui.setItem(11, ItemUtils.getItem(Material.WRITTEN_BOOK, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "History"));

        return gui;
    }

    public static Inventory createVectorInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Point Selection", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(16, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getCustomItem(Material.WOODEN_HOE, 0, (short) 1, 1, ChatColor.AQUA + "Singular Point Selection"));
        gui.setItem(11, ItemUtils.getCustomItem(Material.WOODEN_HOE, 0, (short) 2, 1, ChatColor.AQUA + "Poly Point Selection"));
        gui.setItem(15, ItemUtils.getItem(Material.FEATHER, 0, 1, ChatColor.AQUA + "Reset Point Selection"));

        return gui;
    }

    public static Inventory createScaleInterface(String name, ItemStack item) {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + name, 5, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(37, ItemUtils.getExitItem());
        gui.setItem(43, ItemUtils.getConfirmItem());
        gui.setItem(22, item);
        gui.setItem(21, ItemUtils.getSkullItem(1, "MHF_ArrowDown", ChatColor.AQUA + "Down"));
        gui.setItem(23, ItemUtils.getSkullItem(1, "MHF_ArrowUp", ChatColor.AQUA + "Up"));

        return gui;
    }

    public static Inventory createShapeTypeInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Shape Types", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(16, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Cuboid Shapes"));
        gui.setItem(11, ItemUtils.getSkullItemFromBase64(1, ChatColor.AQUA + "Spherical Shapes",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI0MDcwYzliNjY1OWVkMjViMmNhMTI2OTE1ZjRkODgyMGZhZmNlNDMyNGVkOWE4ZjRiOGE1MDYzNDUzMDdmIn19fQ=="));

        return gui;
    }

    public static Inventory createCuboidShapesInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Cuboid Shapes", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(16, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Cube"));
        gui.setItem(11, ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Cuboid"));
        gui.setItem(12, ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Pyramid"));
        gui.setItem(13, ItemUtils.getSkullItem(1, "flashlight", ChatColor.AQUA + "Prism"));

        return gui;
    }

    public static Inventory createSphericalShapesInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Spherical Shapes", 3, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(16, ItemUtils.getExitItem());
        gui.setItem(10, ItemUtils.getSkullItemFromBase64(1, ChatColor.AQUA + "Sphere",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI0MDcwYzliNjY1OWVkMjViMmNhMTI2OTE1ZjRkODgyMGZhZmNlNDMyNGVkOWE4ZjRiOGE1MDYzNDUzMDdmIn19fQ=="));
        gui.setItem(11, ItemUtils.getSkullItemFromBase64(1, ChatColor.AQUA + "Ellipsoid",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI0MDcwYzliNjY1OWVkMjViMmNhMTI2OTE1ZjRkODgyMGZhZmNlNDMyNGVkOWE4ZjRiOGE1MDYzNDUzMDdmIn19fQ=="));
        gui.setItem(12, ItemUtils.getSkullItemFromBase64(1, ChatColor.AQUA + "Cylinder",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI0MDcwYzliNjY1OWVkMjViMmNhMTI2OTE1ZjRkODgyMGZhZmNlNDMyNGVkOWE4ZjRiOGE1MDYzNDUzMDdmIn19fQ=="));

        return gui;
    }

    public static Inventory createFilledShapeRequestInterface() {
        Inventory gui = InterfaceEngine.createInventory(Pencil.getPrefix() + ChatColor.GREEN + "Spherical Shapes", 5, 9);

        InterfaceEngine.fillInventory(gui, ItemUtils.getFillItem());

        gui.setItem(31, ItemUtils.getExitItem());
        gui.setItem(12, ItemUtils.getNoItem());
        gui.setItem(14, ItemUtils.getYesItem());
        gui.setItem(13, ItemUtils.getItem(Material.PAPER, 0, 1, ChatColor.AQUA + "Would you like your shape to be filled?"));

        return gui;
    }

}