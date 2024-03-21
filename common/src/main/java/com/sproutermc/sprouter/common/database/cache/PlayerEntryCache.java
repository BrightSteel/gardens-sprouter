package com.sproutermc.sprouter.common.database.cache;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerEntryCache extends AsyncCache<UUID, PlayerEntry> {

    @Override
    CompletableFuture<PlayerEntry> createCacheEntry(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Tables.playerTable.getEntry(uuid.toString()));
    }

    // alternative way of fetching playerEntry; caches displayName -> UUID if result is found
    public PlayerEntry getByDisplayName(String displayName) {
        UUID key = GardensSprouter.getPlayerDisplayNameCache().awaitGet(displayName);
        return key == null ? null : awaitGet(key);
    }
}
