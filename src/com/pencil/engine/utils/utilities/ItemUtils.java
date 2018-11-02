package com.pencil.engine.utils.utilities;

import com.pencil.engine.Pencil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
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
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getCustomItem(Material material, int id, short damage, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount, damage, (byte) id);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

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

    public static ItemStack getMenuItem() {
        return getItem(Material.COMPASS, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "Menu");
    }

    public static ItemStack getWandItem() {
        return getItem(Material.DIAMOND_AXE, 0, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand");
    }

    public static boolean matches(ItemStack item, ItemStack comparison) {
        if (!(item.getType() == comparison.getType())) {
            return false;
        }

        if ((!(item.getItemMeta() == comparison.getItemMeta()))) {
            return false;
        }

        return true;
    }
}
