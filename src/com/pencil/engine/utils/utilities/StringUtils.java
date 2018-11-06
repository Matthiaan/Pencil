package com.pencil.engine.utils.utilities;

import org.apache.commons.codec.binary.Base64;

import java.util.Random;

public class StringUtils {

    /**
     * Chat Interactions are used to keep players who can't find the right
     * command, or get an error, motivated, I just though it would be a
     * nice, little addition that just contributes to this personal
     * interaction between the plugin and the player.
     */
    public enum ChatInteraction {
        OOPS("Oops! ", 0),
        WHOOPSIES("Whoopsies! ", 1),
        HUH("Huh! ", 2),
        OH_NO("Oh No! ", 3);

        private String name;
        private int id;

        ChatInteraction(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static ChatInteraction getFromID(int id) {
            for (ChatInteraction interaction : values()) {
                if (interaction.getId() == id) {
                    return interaction;
                }
            }

            return null;
        }
    }

    public static ChatInteraction getRandomInteraction() {
        return ChatInteraction.getFromID(new Random().nextInt(4));
    }
}