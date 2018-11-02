package com.pencil.engine;

import com.pencil.engine.utils.service.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Pencil extends JavaPlugin {

    private static Metrics metrics;
    private static PlayerService playerService;

    /**
     * This method is used to start the plugin, all necessary
     * Services, Engines, etc... should have an instance that
     * is assigned here!
     *
     * Also here we check for World Edit/VoxelSniper and we check whether there
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

        if (Settings.getConfig().<Boolean>get("settings.use-metrics")) {
            metrics = new Metrics(getPlugin());

            //TODO: Add metrics for which operations, commands, miscellaneous  utilities, etc are used!
        }

        playerService = new PlayerService();
        playerService.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Pencil");
    }

    private static boolean hasPlugin(String name) {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
    }

    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Pencil ✎" + ChatColor.DARK_GRAY + "] >> " + ChatColor.RESET;
    }

    public static Metrics getMetrics() {
        return metrics;
    }

    public static PlayerService getPlayerService() {
        return playerService;
    }
}
