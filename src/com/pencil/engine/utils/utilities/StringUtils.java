package com.pencil.engine.utils.utilities;

import org.apache.commons.lang.WordUtils;

import java.util.Random;

public class StringUtils {

    /**
     * Chat Interactions are used to keep players who can't find the right
     * command, or get an error, motivated, I just though it would be a
     * nice, little addition that just contributes to this personal
     * interaction between the plugin and the player.
     */
    public enum ChatInteraction {
        OOPS("Oops! ", 1),
        WHOOPSIES("Whoopsies! ", 2),
        HUH("Huh, ", 3),
        OH_NO("Oh No! ", 4),
        WHOOPSIE_A_DAISY("Whoopsie-a-daisy! ", 5);

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
        return ChatInteraction.getFromID(new Random().nextInt(5));
    }

    public static String capitalize(String str) {
        return WordUtils.capitalize(str);
    }

}
