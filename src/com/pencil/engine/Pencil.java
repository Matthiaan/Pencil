package com.pencil.engine;

import com.pencil.engine.utils.InterfaceSet;
import com.pencil.engine.utils.service.CommandService;
import com.pencil.engine.utils.service.MetricsService;
import com.pencil.engine.utils.service.PlayerService;
import com.pencil.engine.utils.utilities.InterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Pencil extends JavaPlugin {

    private static Metrics metrics;
    private static PlayerService playerService;
    private static MetricsService metricsService;

    private static InterfaceSet materials;

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

        if (Settings.getAnalytics().get("analytics") == null) {
            Settings.getAnalytics().set("analytics.command-usage-ratio.commands", 0);
            Settings.getAnalytics().set("analytics.command-usage-ratio.interface", 0);
            Settings.getAnalytics().set("analytics.selection-type.normal", 0);
            Settings.getAnalytics().set("analytics.selection-type.poly", 0);
        }

        if (Settings.getConfig().<Boolean>get("settings.use-metrics")) {
            metrics = new Metrics(getPlugin());
        }

        getCommand("pencil").setExecutor(new CommandService());

        playerService = new PlayerService();
        playerService.init();

        metricsService = new MetricsService();

        materials = InterfaceUtils.createMaterialInterface();
    }

    @Override
    public void onDisable() {
        Settings.getAnalytics().set("analytics.command-usage-ratio.commands",
                Settings.getAnalytics().<Integer>get("analytics.command-usage-ratio.commands" + metricsService.getCommands()));
        Settings.getAnalytics().set("analytics.command-usage-ratio.interface",
                Settings.getAnalytics().<Integer>get("analytics.command-usage-ratio.interface" + metricsService.getGuiCommands()));
        Settings.getAnalytics().set("analytics.selection-type.normal",
                Settings.getAnalytics().<Integer>get("analytics.selection-type.normal" + metricsService.getNormalType()));
        Settings.getAnalytics().set("analytics.selection-type.poly",
                Settings.getAnalytics().<Integer>get("analytics.selection-type.poly" + metricsService.getPolyType()));
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("PSuite");
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

    public static InterfaceSet getMaterials() {
        return materials;
    }
}
