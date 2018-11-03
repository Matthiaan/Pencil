package com.pencil.engine.command;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.action.PencilAction;
import com.pencil.engine.utils.service.MessageService;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class UndoCommand extends PencilCommand {

    //If a single action isn't undoable, it will ask the player to provide an index
    @Override
    public void onCommand(Player player, String[] args) {
        if (player.hasPermission(getPermission())) {
            if (args.length == 0) {
                PencilAction action = Pencil.getPlayerService().getPlayer(player).getHistory().getLatestAction();

                if (action.isUndoable()) {
                    action.undo(player);

                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_UNDONE.getMessage(),
                            MessageService.MessageType.WARNING, true));
                } else {
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_NOT_UNDOABLE.getMessage(),
                            MessageService.MessageType.WARNING, true));
                }
            } else {
                String argument = args[0];

                try {
                    int passes = Integer.parseInt(argument);

                    for (int i = passes; i >= 0; i--) {
                        PencilAction action = Pencil.getPlayerService().getPlayer(player).getHistory().getAction(i);

                        if (action.isUndoable()) {
                            action.undo(player);
                        }
                    }

                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.ACTION_MULTI_UNDONE.getMessage(),
                            MessageService.MessageType.WARNING, true));
                } catch (NumberFormatException ex) {
                    player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.COMMAND_ARGUMENT_MUST_BE_NUMERICAL.getMessage(),
                            MessageService.MessageType.WARNING, true));
                }
            }
        } else {
            player.sendMessage(MessageService.formatMessage(MessageService.PreFormattedMessage.NO_PERMISSION.getMessage(),
                    MessageService.MessageType.WARNING, true));
        }
    }

    @Override
    public String getName() {
        return "undo";
    }

    @Override
    public String getArgs() {
        return "<index>";
    }

    @Override
    public String getDescription() {
        return "Undoes one/multiple action(s).";
    }

    @Override
    public Permission getPermission() {
        return null;
    }
}
