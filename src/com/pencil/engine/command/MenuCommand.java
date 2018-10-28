package com.pencil.engine.command;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class MenuCommand extends PencilCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (player.hasPermission(getPermission())) {
            if (args.length != 0) {
                player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_ARGUMENTS.getMessage(),
                        MessageService.MessageType.INFO, true));
            }

            if (!(player.getInventory().firstEmpty() == -1)) {
                player.getInventory().addItem(ItemUtils.getMenuItem());
                player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.GUI_ADDED_MENU_PENCIL.getMessage(),
                        MessageService.MessageType.INFO, false));
            } else {
                player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.UTILS_INVENTORY_FULL.getMessage(),
                        MessageService.MessageType.ERROR, true));
            }
        } else {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PERMISSION.getMessage(),
                    MessageService.MessageType.WARNING, true));
        }

        player.getInventory().addItem(ItemUtils.getMenuItem());
    }

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getArgs() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Gives you the Pencil Menu";
    }

    @Override
    public Permission getPermission() {
        return null;
    }
}