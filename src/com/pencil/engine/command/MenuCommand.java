package com.pencil.engine.command;

import com.pencil.engine.utils.service.MessageService;
import com.pencil.engine.utils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class MenuCommand extends PencilCommand {

    @Override
    public void onCommand(Player player, String[] args) {
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

        //TODO: Make permissions!
        /*
        if (player.hasPermission(getPermission())) {

        } else {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PERMISSION.getMessage(),
                    MessageService.MessageType.WARNING, true));
        }
        */
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
