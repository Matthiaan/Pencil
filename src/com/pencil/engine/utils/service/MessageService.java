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
        ACTION_NO_ACTIONS_HISTORY("Your current history of actions is empty!"),
        ACTION_HISTORY_CLEARED("Your current history has been cleared!"),
        ACTION_REQUEST_PRE_PROCESSING("Processing your request..."),
        ACTION_REQUEST_PROCESSED("Request has been processed!"),
        ACTION_SELECTION_EMPTY("Watch out! Your selection is empty!"),
        ACTION_REQUEST_PROCESSING_EXCEPTION("Seems like there was an error while processing your request!"),
        ACTION_SOMETHING_WENT_WRONG("Seems like something went wrong!"),
        ACTION_RULER_FIRST_POSITION("Measure starting position set!"),

        CLIPBOARD_EMPTY("Your clipboard is empty!"),
        CLIPBOARD_ROTATED("Your clipboard has been rotated!"),

        NO_PERMISSION("Seems like you don't have the permission to execute this command!"),
        NO_RIGHT_SELECTION("You don't have the right selection for this operation!"),
        NO_COMMAND("Pencil didn't recognise that command!"),
        NO_PLAYER("Only players can use Pencil!"),
        NO_ARGUMENTS("This command doesn't take any arguments!"),
        NO_UNDO_OPTION("No action to be undone!"),
        NO_REDO_OPTION("No action to be redone!"),
        NO_HISTORY_AVAILABLE("There is not current history available for the operation!"),

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

        SELECTION_COPIED("Your selection has been copied to your clipboard!"),
        SELECTION_PASTED("Your selection has been pasted!"),
        ;

        private String message;

        PreFormattedMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static String formatMessage(String message, MessageType type) {
        if (type == null) {
            type = MessageType.INFO;
        }

        return Pencil.getPrefix() + type.color + message + ChatColor.RESET;
    }

}
