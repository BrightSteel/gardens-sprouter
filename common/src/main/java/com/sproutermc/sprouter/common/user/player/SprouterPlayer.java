package com.sproutermc.sprouter.common.user.player;

import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.OnlineUser;
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

    public abstract boolean teleport(SprouterLocation location);

    public abstract void setGameMode(SprouterGameMode gameMode);

    public abstract boolean isFlyAllowed();

    public abstract void setFlyAllowed(boolean allowed);

    public abstract void setTabListName(String name);

    public abstract SprouterLocation getLocation();
}
