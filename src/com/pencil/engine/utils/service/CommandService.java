package com.pencil.engine.utils.service;

import com.pencil.engine.command.LogCommand;
import com.pencil.engine.command.MenuCommand;
import com.pencil.engine.command.UndoCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService implements CommandExecutor, TabExecutor {

    private LogCommand logCommand;
    private MenuCommand menuCommand;
    private UndoCommand undoCommand;

    public CommandService() {
        logCommand = new LogCommand();
        menuCommand = new MenuCommand();
        undoCommand = new UndoCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PLAYER.getMessage(),
                    MessageService.MessageType.ERROR, true));

            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("pencil")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(MessageService.formatMessage(logCommand.getName() +" " + logCommand.getArgs() + " -> " + logCommand.getDescription(),
                        MessageService.MessageType.LIST, false));
                player.sendMessage(MessageService.formatMessage(menuCommand.getName() +" " + menuCommand.getArgs() + " -> " + menuCommand.getDescription(),
                        MessageService.MessageType.LIST, false));
                player.sendMessage(MessageService.formatMessage(undoCommand.getName() +" " + undoCommand.getArgs() + " -> " + undoCommand.getDescription(),
                        MessageService.MessageType.LIST, false));

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
            return new ArrayList<>(Arrays.asList("log", "menu", "undo"));
        }

        return null;
    }
}
