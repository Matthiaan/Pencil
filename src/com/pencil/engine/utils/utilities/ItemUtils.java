package com.pencil.engine.utils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ItemUtils {

    public static ItemStack getItem(Material material, int id, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) 0, (byte) id);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getSkullItem(int amount, String owner, String name, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount, (short) 0, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static boolean matchesMaterial(Material given, Material check) {
        return (given == check);
    }

    public static boolean matchesAmount(int given, int check) {
        return (given == check);
    }

    public static boolean matchesName(String given, String check) {
        return given.equalsIgnoreCase(check);
    }

    public static boolean matchesRoughly(ItemStack given, ItemStack check) {
        if (!matchesMaterial(given.getType(), check.getType())) {
            return false;
        }

        if (!matchesAmount(given.getAmount(), check.getAmount())) {
            return false;
        }

        if (!(given.hasItemMeta() || check.hasItemMeta())) {
            return false;
        }

        if (!matchesName(given.getItemMeta().getDisplayName(), check.getItemMeta().getDisplayName())) {
            return false;
        }

        return true;
    }

    public static ItemStack getExitItem() {
        return getItem(Material.BARRIER, 0, 1, ChatColor.RED + "Exit");
    }

    public static ItemStack getFillItem() {
        return getItem(Material.GRAY_STAINED_GLASS_PANE, 0, 1, "");
    }

    public static ItemStack getNextPageItem() {
        return getSkullItem(1, "MHF_ArrowRight", ChatColor.GREEN + "Next Page");
    }

    public static ItemStack getPreviousPageItem() {
        return getSkullItem(1, "MHF_ArrowLeft", ChatColor.GREEN + "Previous Page");
    }

}
