package com.sproutermc.sprouter.common.user;

import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.world.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
public abstract class SprouterPlayer extends SprouterUser {

    private final UUID uuid;

    public SprouterPlayer(UUID uuid, String username) {
        super(username);
        this.uuid = uuid;
    }

    public abstract void teleport(Location location);

    public abstract void setGameMode(SprouterGameMode gameMode);

    public String getUsername() {
        return getName();
    }
}
