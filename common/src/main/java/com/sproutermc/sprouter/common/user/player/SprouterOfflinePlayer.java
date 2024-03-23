package com.sproutermc.sprouter.common.user.player;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.user.SprouterUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
public class SprouterOfflinePlayer extends SprouterUser {

    public SprouterOfflinePlayer(UUID uuid, String username) {
        super(uuid.toString(), username);
    }

    public SprouterOfflinePlayer(PlayerEntry playerEntry) {
        super(playerEntry.getUuid().toString(), playerEntry.getUsername());
    }

    @Override
    public String getDisplayName() {
        String nickname = GardensSprouter.getPlayerEntryCache()
                .awaitGet(getUniqueId())
                .getNickname();
        return nickname != null ? nickname : super.getDisplayName();
    }

    public String getUsername() {
        return super.getDisplayName();
    }

    public UUID getUniqueId() {
        return UUID.fromString(uuid);
    }
}
