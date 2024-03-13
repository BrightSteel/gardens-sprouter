package com.sproutermc.sprouter.common.user;

import com.sproutermc.sprouter.common.world.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class SprouterPlayer {

    private UUID uuid;
    private String username;

    public abstract void sendMessage(String message);

    public abstract void teleport(Location location);

    public abstract void setGameMode(String gameMode);
}
