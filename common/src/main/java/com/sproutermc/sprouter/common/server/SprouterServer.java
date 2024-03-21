package com.sproutermc.sprouter.common.server;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.user.SprouterOfflinePlayer;
import com.sproutermc.sprouter.common.user.SprouterPlayer;

import java.io.File;
import java.util.List;
import java.util.UUID;

public abstract class SprouterServer {

    public abstract SprouterPlayer getPlayer(UUID uuid);

    /**
     * Attempts to find a SprouterPlayer for provided displayName
     * @param displayNameContains displayName to run a contains on currently online users
     * @return SprouterPlayer matching criteria, or null if none found
     */
    public SprouterPlayer getPlayer(String displayNameContains) {
        // first try exact username match, case in-sensitive
        SprouterPlayer player = getPlayerExact(displayNameContains);
        if (player == null) {
            // otherwise see if name is contained by any online player's displayName
            PlayerEntry playerEntry = getOnlinePlayers()
                    .stream()
                    .map(p -> GardensSprouter.getPlayerEntryCache().awaitGet(UUID.fromString(p.getUuid())))
                    .filter(pEntry -> {
                        String filter = displayNameContains.toLowerCase();
                        String nickNamePlain = pEntry.getNickname() == null ? "" : pEntry.getNicknamePlain();
                        return nickNamePlain.toLowerCase().contains(filter) || pEntry.getUsername().toLowerCase().contains(filter);
                    })
                    .findFirst().orElse(null);
            if (playerEntry != null) {
                return getPlayer(playerEntry.getUuid());
            }
        }
        return player;
    }

    public abstract SprouterPlayer getPlayerExact(String username);

    /**
     * First attempts to find an onlinePlayer with displayName containing provided displayName.
     * If that fails, attempts to find an offlinePlayer with displayName equaling provided displayName.
     * @param displayName to run a contains against for online users, and an equals against offline users (case-insensitive)
     * @return SprouterOfflinePlayer, or SprouterPlayer, matching provided criteria, or null if none found
     */
    public SprouterOfflinePlayer getPlayerByDisplayName(String displayName) {
        // first try matching to online users - data gets cached so quick enough
        SprouterPlayer sprouterPlayer = getPlayer(displayName);
        if (sprouterPlayer != null) {
            return sprouterPlayer;
        }
        // try to get offline user by displayName (nickname or else username)
        try {
            PlayerEntry playerEntry = GardensSprouter.getPlayerEntryCache().getByDisplayName(displayName);
            if (playerEntry != null) {
                return new SprouterOfflinePlayer(playerEntry);
            }
        } catch (Exception e) {
            GardensSprouter.getSprouterLogger().error("Failed to get future result", e);
        }
        return null;
    }

    public abstract List<? extends SprouterPlayer> getOnlinePlayers();

    public abstract File getWorkingDirectory();

    public abstract void broadcastMessage(String message);
}
