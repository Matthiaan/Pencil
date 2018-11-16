package com.pencil.engine.utils.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.pencil.engine.Pencil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class ItemUtils {

    private static Field profileField;

    public static ItemStack getItem(Material material, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
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
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount, (short) 0);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getSkullItemFromBase64(int amount, String name, String base, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount, (short) 0);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        byte[] data = Base64.getEncoder().encode(String.format(
                "{textures:{SKIN:{url:\"%s\"}}}", base).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(data)));

        try {
            if (profileField == null) {
                profileField = meta.getClass().getDeclaredField("profile");
            }

            profileField.setAccessible(true);
            profileField.set(meta, profile);

            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(lore));

            item.setItemMeta(meta);

            return item;
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ItemStack changeMeta(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getExitItem() {
        return getItem(Material.BARRIER, 1, ChatColor.RED + "Exit");
    }

    public static ItemStack getFillItem() {
        return getItem(Material.GRAY_STAINED_GLASS_PANE, 1, "");
    }

    public static ItemStack getNextPageItem() {
        return getSkullItem(1, "MHF_ArrowRight", ChatColor.GREEN + "Next Page");
    }

    public static ItemStack getPreviousPageItem() {
        return getSkullItem(1, "MHF_ArrowLeft", ChatColor.GREEN + "Previous Page");
    }

    public static ItemStack getMenuItem() {
        return getItem(Material.COMPASS, 1, Pencil.getPrefix() + ChatColor.AQUA + "Menu");
    }

    public static ItemStack getWandItem() {
        return getItem(Material.DIAMOND_AXE, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand");
    }

    public static ItemStack getConfirmItem() {
        return getSkullItem(1, "MHF_ArrowRight", ChatColor.GREEN + "Confirm");
    }

    public static ItemStack getYesItem() {
        return getItem(Material.GREEN_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes");
    }

    public static ItemStack getNoItem() {
        return getItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No");
    }

    public static boolean isPencilItem(ItemStack item) {
        if (item.hasItemMeta()) {
            if (item.getItemMeta().getDisplayName().contains("Pencil")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean matches(ItemStack item, ItemStack comparison) {
        if (!(item.getType() == comparison.getType())) {
            return false;
        }

        if (item.hasItemMeta() && comparison.hasItemMeta()) {
            if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(comparison.getItemMeta().getDisplayName())) {
                return false;
            }
        }

        return true;
    }
}
