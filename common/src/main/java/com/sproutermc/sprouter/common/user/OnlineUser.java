package com.sproutermc.sprouter.common.user;

public interface OnlineUser {
    String getDisplayName();
    String getUuid();
    void sendMessage(String message);
    boolean hasPermission(String permission);
}
