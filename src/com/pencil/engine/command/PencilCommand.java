package com.pencil.engine.command;

import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public abstract class PencilCommand {

    /**
     * Main method where the command gets executed.
     *
     * @param player The player that has send the command.
     * @param args The remaining arguments for the command.
     */
    public abstract void onCommand(Player player, String[] args);

    /**
     * @return The name of the command.
     */
    public abstract String getName();

    /**
     * @return The arguments of the command.
     */
    public abstract String getArgs();

    /**
     * @return The description of what the command does.
     */
    public abstract String getDescription();

    /**
     * @return The permission needed in order to execute the command.
     */
    public abstract Permission getPermission();
}
