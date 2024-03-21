package com.sproutermc.sprouter.common.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    // Regular expression pattern to match &a or &#abcde
    private static final Pattern colorPattern = Pattern.compile("&[a-zA-Z0-9]|&#[a-fA-F0-9]{6}");
    private static final Pattern hexPattern = Pattern.compile("&#([a-fA-F0-9]{6})");
    private static final char COLOR_CHAR = '§';

    public static String stripFormatting(String message) {
        return colorPattern.matcher(message).replaceAll("");
    }

    public static String translateColors(String message) {
        return translateAlternateColorCodes(translateHex(message));
    }

    // todo - verify this works on fabric
    public static String translateHex(String message) {
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder stringBuilder = new StringBuilder(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(stringBuilder, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(stringBuilder).toString();
    }

    // todo - verify this works on fabric
    public static String translateAlternateColorCodes(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot translate null text");
        }

        // copied directly from Spigot - not available in fabric so let's hold it here for both to use
        char[] b = message.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
