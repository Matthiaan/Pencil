package com.pencil.engine.api;

import org.bukkit.plugin.Plugin;

public class PencilAPI {

    public static PencilPlugin isPencilExtension(Plugin plugin) {
        if (plugin.getName().equalsIgnoreCase("MoGraph")) {
            return new PencilPlugin(plugin, true);
        }

        return new PencilPlugin(plugin, false);
    }

}
