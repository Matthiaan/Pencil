package com.pencil.engine.command;

import com.pencil.engine.utils.action.PencilAction;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public abstract class PencilCommand implements PencilAction {

    public abstract void onCommand(Player player, String[] args);

    public abstract String getName();

    public abstract String getArgs();

    public abstract String getDescription();

    public abstract Permission getPermission();

    public ActionType getActionType() {
        return ActionType.COMMAND;
    }
}
