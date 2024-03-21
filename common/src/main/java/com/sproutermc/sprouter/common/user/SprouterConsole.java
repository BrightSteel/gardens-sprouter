package com.sproutermc.sprouter.common.user;

public abstract class SprouterConsole extends SprouterUser implements OnlineUser {

    private static final String CONSOLE_DISPLAY_NAME = "Console";
    private static final String CONSOLE_UUID = "CONSOLE";

    public SprouterConsole() {
        super(CONSOLE_UUID, CONSOLE_DISPLAY_NAME);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
