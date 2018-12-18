package com.pencil.engine.command;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class LogCommand extends PencilCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length != 0) {
            String argument = args[0];

            if (argument.equalsIgnoreCase("show")) {
                if (!Pencil.getPlayerService().getPlayer(player).getHistory().getActions().isEmpty()) {
                    for (PencilAction action : Pencil.getPlayerService().getPlayer(player).getHistory().getActions().values()) {
                        player.sendMessage(MessageService.formatMessage(action.toString(), MessageService.MessageType.LIST));
                    }
                } else {
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_NO_ACTIONS_HISTORY.getMessage(),
                            MessageService.MessageType.INFO));
                }
            } else if (argument.equalsIgnoreCase("clear")) {
                Pencil.getPlayerService().getPlayer(player).getHistory().clear();

                player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_HISTORY_CLEARED.getMessage(),
                        MessageService.MessageType.LIST));
            } else {
                player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.COMMAND_ARGUMENT_NOT_RECOGNIZED.getMessage(),
                        MessageService.MessageType.INFO));
            }
        } else {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.COMMAND_ARGUMENT_MISSING.getMessage(),
                    MessageService.MessageType.INFO));
        }

        //TODO: Make permissions
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
        return "log";
    }

    @Override
    public String getArgs() {
        return "[<clear><show>]";
    }

    @Override
    public String getDescription() {
        return "Commands that involve your personal history of actions.";
    }

    @Override
    public Permission getPermission() {
        return null;
    }
}
