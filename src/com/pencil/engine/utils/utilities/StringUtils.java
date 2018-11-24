package com.pencil.engine.utils.utilities;

public class StringUtils {

    public static String getSuffix(String name) {
        if (name.endsWith("x") || name.endsWith("s")) {
            return "'";
        } else {
            return "'s";
        }
    }

}
