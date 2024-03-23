package com.sproutermc.sprouter.common.user;

import com.sproutermc.sprouter.common.chat.ColorTheme;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMessageHandler {

    private final OnlineUser onlineUser;

    public void sendMessage(String message) {
        String m = String.format("%s %s", ColorTheme.SPROUTER_PREFIX, ColorTheme.MAIN_COLOR + message);
        onlineUser.sendMessage(m);
    }

    public void sendError(String message) {
        String m = String.format("%s %s", ColorTheme.SPROUTER_PREFIX, ColorTheme.ERROR_COLOR + message);
        onlineUser.sendMessage(m);
    }
}
