package com.pencil.engine.utils.service;

import com.pencil.engine.Pencil;
import com.pencil.engine.command.PencilCommand;
import com.pencil.engine.utils.utilities.StringUtils;
import org.apache.logging.log4j.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandService implements CommandExecutor {

    private ArrayList<PencilCommand> commands;

    public CommandService() {
        this.commands = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PLAYER.getMessage(),
                    MessageService.MessageType.ERROR, true));

            return false;
        }

        Player player = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("pencil")) {
            return false;
        }

        if (args[0].equalsIgnoreCase("help")) {
            for (PencilCommand command : commands) {
                player.sendMessage(MessageService.formatMessage(
                        StringUtils.capitalize(command.getName()) + " -> " + command.getDescription(),
                        MessageService.MessageType.LIST, false));
            }
        }

        for (PencilCommand command : commands) {
            if (args[0].equalsIgnoreCase(cmd.getName())) {
                if (player.hasPermission(command.getPermission())) {
                    command.onCommand(player, Arrays.copyOfRange(args, 1, args.length));

                    return false;
                }
            }
        }

        player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_COMMAND.getMessage(),
                MessageService.MessageType.WARNING, true));

        return false;
    }
}
