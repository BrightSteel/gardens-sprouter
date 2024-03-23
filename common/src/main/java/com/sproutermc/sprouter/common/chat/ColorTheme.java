package com.sproutermc.sprouter.common.chat;

public class ColorTheme {

    // todo - make these configurable
    public static final String MAIN_COLOR = "&6";
    public static final String EMPHASIS_COLOR = "&e";
    public static final String PLAYER_COLOR = "&e";
    public static final String ERROR_COLOR = "&c";
    public static final String SPROUTER_PREFIX = "[Sprouter]";

    public static String formatEmphasis(String emphasized) {
        return ColorTheme.EMPHASIS_COLOR + emphasized + ColorTheme.MAIN_COLOR;
    }

    // todo - also return their default rank color (nick colors override this)
    // also, should this only accept SprouterPlayer ? We don't really know what we're getting here in order to accomplish above task
    public static String formatPlayer(String player) {
        return ColorTheme.PLAYER_COLOR + player + ColorTheme.MAIN_COLOR;
    }
}
