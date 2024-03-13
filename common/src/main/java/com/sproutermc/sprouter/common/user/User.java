package com.sproutermc.sprouter.common.user;

import com.sproutermc.sprouter.common.world.Location;

public interface User {

    void sendMessage(String message);

    void teleport(Location location);

    void setGameMode(String gameMode);
}
