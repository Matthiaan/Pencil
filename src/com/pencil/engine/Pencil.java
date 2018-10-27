package com.pencil.engine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Pencil extends JavaPlugin {

    /**
     * This method is used to start the plugin, all necessary
     * Services, Engines, etc... should have an instance that
     * is assigned here!
     *
     * Also here we check for World Edit and we check whether there
     * could be a problem regarding memory!
     *
     */
    @Override
    public void onEnable() {
        System.out.println("[Pencil] Initiating Pencil...");

        if (hasPlugin("WorldEdit") || hasPlugin("VoxelSniper")) {
            System.out.println("[Pencil] Pencil can't work properly when World Edit or Voxel Sniper is installed!");
            System.out.println("[Pencil] Please remove these plugins in order for Pencil to work!");

            onDisable();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Pencil");
    }

    public static boolean hasPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }

    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Pencil ✎" + ChatColor.DARK_GRAY + "] >> " + ChatColor.RESET;
    }
}
