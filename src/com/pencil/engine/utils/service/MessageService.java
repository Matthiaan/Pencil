package com.pencil.engine.utils.service;

import com.pencil.engine.Pencil;
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
        ACTION_MULTI_UNDONE("All actions have been undone!"),
        ACTION_REDONE("Action has been redone!"),
        ACTION_MULTI_REDONE("All actions have been redone!"),
        ACTION_NOT_UNDOABLE("That action isn't undoable! If you want to skip actions please provide an index!"),
        ACTION_NO_ACTIONS_HISTORY("Your current history of actions is empty!"),
        ACTION_HISTORY_CLEARED("Your current history has been cleared!"),
        ACTION_SHAPE_CREATION("Processing your Shape request..."),
        ACTION_SELECTION_EMPTY("Watch out! Your selection is empty!"),
        ACTION_SHAPE_PROCESSING_EXCEPTION("Seems like there was an error while processing your request!"),
        ACTION_SOMETHING_WENT_WRONG("Seems like something went wrong!"),
        ACTION_RULER_FIRST_POSITION("Measure starting position set!"),

        NO_PERMISSION("Seems like you don't have the permission to execute this command!"),
        NO_COMMAND("Pencil didn't recognise that command!"),
        NO_PLAYER("Only players can use Pencil!"),
        NO_ARGUMENTS("This command doesn't take any arguments!"),

        COMMAND_ARGUMENT_MUST_BE_NUMERICAL("The argument for this command must be numerical!"),
        COMMAND_ARGUMENT_MISSING("The argument for this command is missing!"),
        COMMAND_ARGUMENT_NOT_RECOGNIZED("That argument wasn't recognized!"),

        GUI_ADDED_MENU_PENCIL("You have received the Pencil Menu"),
        GUI_ADDED_WAND_PENCIL("You have received the Pencil Wand"),
        GUI_REGULAR_TOOL("Your Pencil Wand type has been set to REGULAR selection mode!"),
        GUI_RULER_TOOL("Your Pencil Wand type has been set to RULER mode!"),

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
            return Pencil.getPrefix() + type.color + "Oops! " + message + ChatColor.RESET;
        } else {
            return Pencil.getPrefix() + type.color + message + ChatColor.RESET;
        }
    }

}
