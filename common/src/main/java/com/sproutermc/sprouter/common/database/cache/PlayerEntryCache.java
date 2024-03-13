package com.sproutermc.sprouter.common.database.cache;

import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerEntryCache extends AsyncCache<UUID, PlayerEntry> {

    @Override
    CompletableFuture<PlayerEntry> createCacheEntry(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> Tables.playerTable.getEntry(uuid.toString()));
    }
}
