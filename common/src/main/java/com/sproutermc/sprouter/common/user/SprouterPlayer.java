package com.sproutermc.sprouter.common.user;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.world.SprouterLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
public abstract class SprouterPlayer extends SprouterOfflinePlayer implements OnlineUser {

    public SprouterPlayer(UUID uuid, String username) {
        super(uuid, username);
    }

    public abstract void teleport(SprouterLocation location);

    public abstract void setGameMode(SprouterGameMode gameMode);

    public abstract boolean isFlyAllowed();

    public abstract void setFlyAllowed(boolean allowed);

    public abstract void setTabListName(String name);
}
