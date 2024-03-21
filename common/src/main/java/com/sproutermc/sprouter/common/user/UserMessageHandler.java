package com.sproutermc.sprouter.common.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMessageHandler {

    private final OnlineUser onlineUser;

    // todo - make the message format configurable
    public void sendMessage(String message) {
        String m = String.format("&a[Sprouter] &7%s", message);
        onlineUser.sendMessage(m);
    }

    public void sendError(String message) {
        String m = String.format("&a[Sprouter] &c%s", message);
        onlineUser.sendMessage(m);
    }
}
