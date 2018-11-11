package com.pencil.engine.utils.service;

import com.pencil.engine.Pencil;
import com.pencil.engine.utils.utilities.StringUtils;
import org.bukkit.ChatColor;

public class MessageService {

    public enum MessageType {
        LIST(ChatColor.WHITE),
        INFO(ChatColor.GREEN),
        WARNING(ChatColor.YELLOW),
        ERROR(ChatColor.RED);

        private ChatColor color;

        MessageType(ChatColor color) {
            this.color = color;
        }
    }

    public enum PreFormattedMessage {
        ACTION_UNDONE("Action has been undone!"),
        ACTION_MULTI_UNDONE("Actions have been undone!"),
        ACTION_NOT_UNDOABLE("That action isn't undoable! If you want to skip actions please provide an index!"),
        ACTION_NO_ACTIONS_HISTORY("Your current history of actions is empty!"),
        ACTION_HISTORY_CLEARED("Your current history has been cleared!"),
        ACTION_SHAPE_CREATION("Processing your Shape request..."),

        NO_PERMISSION("Seems like you don't have the permission to execute this command!"),
        NO_COMMAND("Pencil didn't recognise that command!"),
        NO_PLAYER("Only players can use Pencil!"),
        NO_ARGUMENTS("This command doesn't take any arguments!"),

        COMMAND_ARGUMENT_MUST_BE_NUMERICAL("The argument for this command must be numerical!"),
        COMMAND_ARGUMENT_MISSING("The argument for this command is missing!"),
        COMMAND_ARGUMENT_NOT_RECOGNIZED("That argument wasn't recognized!"),

        GUI_ADDED_MENU_PENCIL("You have received the Pencil Menu"),
        GUI_ADDED_WAND_PENCIL("You have received the Pencil Wand"),

        UTILS_INVENTORY_FULL("Seems like you inventory is full!"),
        UTILS_FIRST_POSITION_SET("Position 1 has been set! "),
        UTILS_SECOND_POSITION_SET("Position 2 has been set! "),
        UTILS_POSITION_SET("Position has been added!"),
        ;

        private String message;

        PreFormattedMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static String formatMessage(String message, MessageType type, boolean interaction) {
        if (type == null) {
            type = MessageType.INFO;
        }

        if (interaction) {
            return Pencil.getPrefix() + type.color + StringUtils.getRandomInteraction() + message + ChatColor.RESET;
        } else {
            return Pencil.getPrefix() + type.color + message + ChatColor.RESET;
        }
    }

}
