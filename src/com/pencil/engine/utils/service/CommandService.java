package com.pencil.engine.utils.service;

import com.pencil.engine.command.LogCommand;
import com.pencil.engine.command.MenuCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandService implements CommandExecutor, TabExecutor {

    private LogCommand logCommand;
    private MenuCommand menuCommand;

    public CommandService() {
        logCommand = new LogCommand();
        menuCommand = new MenuCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PLAYER.getMessage(),
                    MessageService.MessageType.ERROR));

            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("pencil")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(MessageService.formatMessage(logCommand.getName() +" " + logCommand.getArgs() + " -> " + logCommand.getDescription(),
                        MessageService.MessageType.LIST));
                player.sendMessage(MessageService.formatMessage(menuCommand.getName() +" " + menuCommand.getArgs() + " -> " + menuCommand.getDescription(),
                        MessageService.MessageType.LIST));

                return true;
            } else if (args[0].equalsIgnoreCase("log")) {
                return true;
            } else if (args[0].equalsIgnoreCase("menu")) {
                menuCommand.onCommand(player, args);

                return true;
            } else if (args[0].equalsIgnoreCase("undo")) {
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pencil")) {
            return new ArrayList<>(Arrays.asList("log", "menu"));
        }

        if (args[0].startsWith("l")) {
            return new ArrayList<>(Collections.singleton("log"));
        } else if (args[0].startsWith("m")) {
            return new ArrayList<>(Collections.singleton("menu"));
        }

        return null;
    }
}
