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

        public ChatColor getColor() {
            return color;
        }
    }

    public enum PreFormattedMessage {
        NO_PERMISSION("Seems like you don't have the permission to execute this command!"),
        NO_COMMAND("Pencil didn't recognise that command!"),
        NO_PLAYER("Only players can use Pencil!"),
        NO_ARGUMENTS("This command doesn't take any arguments!"),

        GUI_ADDED_MENU_PENCIL("You have received the Pencil Menu"),

        UTILS_INVENTORY_FULL("Seems like you inventory is full!"),
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
