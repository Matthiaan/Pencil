package com.pencil.engine.command;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public abstract class PencilCommand {

    public abstract void onCommand(Player player, String[] args);

    public abstract String getName();

    public abstract String getArgs();

    public abstract String getDescription();

    public abstract Permission getPermission();

}
