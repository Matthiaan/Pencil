package com.pencil.engine.api;

import org.bukkit.plugin.Plugin;

public class PencilPlugin {

    private Plugin plugin;
    private boolean hasPermission;

    public PencilPlugin(Plugin plugin, boolean hasPermission) {
        this.plugin = plugin;
        this.hasPermission = hasPermission;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }
}
