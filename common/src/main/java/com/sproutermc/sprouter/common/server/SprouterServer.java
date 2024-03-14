package com.sproutermc.sprouter.common.server;

import com.sproutermc.sprouter.common.user.SprouterPlayer;

import java.io.File;
import java.util.UUID;

public interface SprouterServer {
    SprouterPlayer getOnlinePlayer(UUID uuid);
    SprouterPlayer getOnlinePlayer(String username);
    File getWorkingDirectory();
}
