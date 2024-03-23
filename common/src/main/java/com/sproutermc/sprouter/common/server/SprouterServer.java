package com.sproutermc.sprouter.common.server;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.user.player.SprouterOfflinePlayer;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.util.List;
import java.util.UUID;

public abstract class SprouterServer {

    @Nullable
    public abstract SprouterPlayer getPlayer(UUID uuid);

    /**
     * Attempts to find a SprouterPlayer for provided displayName
     * @param displayNameContains displayName to run a contains on currently online users
     * @return SprouterPlayer matching criteria, or null if none found
     */
    @Nullable
    public SprouterPlayer getPlayer(String displayNameContains) {
        // first try exact username match, case in-sensitive
        SprouterPlayer player = getPlayerExact(displayNameContains);
        if (player != null) {
            return player;
        }
        // otherwise see if name is contained by any online player's displayName
        PlayerEntry playerEntry = getOnlinePlayers()
                .stream()
                .map(p -> GardensSprouter.getPlayerEntryCache().awaitGet(UUID.fromString(p.getUuid())))
                .filter(pEntry -> {
                    String filter = displayNameContains.toLowerCase();
                    String nickNamePlain = pEntry.getNickname() == null ? "" : pEntry.getNicknamePlain();
                    return nickNamePlain.toLowerCase().contains(filter) || pEntry.getUsername().toLowerCase().contains(filter);
                })
                .findFirst()
                .orElse(null);

        return playerEntry == null ? null : getPlayer(playerEntry.getUuid());
    }

    @Nullable
    public abstract SprouterPlayer getPlayerExact(String username);

    /**
     * First attempts to find an onlinePlayer with displayName containing provided displayName.
     * If that fails, attempts to find an offlinePlayer with displayName equaling provided displayName.
     * @param displayName to run a contains against for online users, and an equals against offline users (case-insensitive)
     * @return SprouterOfflinePlayer, or SprouterPlayer, matching provided criteria, or null if none found
     */
    @Nullable
    public SprouterOfflinePlayer getPlayerByDisplayName(String displayName) {
        // first try matching to online users - data gets cached so quick enough
        SprouterPlayer sprouterPlayer = getPlayer(displayName);
        if (sprouterPlayer != null) {
            return sprouterPlayer;
        }
        // try to get offline user by displayName (nickname or else username)
        PlayerEntry playerEntry = GardensSprouter.getPlayerEntryCache().getByDisplayName(displayName);
        return playerEntry == null ? null : new SprouterOfflinePlayer(playerEntry);
    }

    public abstract List<? extends SprouterPlayer> getOnlinePlayers();

    public abstract File getWorkingDirectory();

    public abstract void broadcastMessage(String message);
}
