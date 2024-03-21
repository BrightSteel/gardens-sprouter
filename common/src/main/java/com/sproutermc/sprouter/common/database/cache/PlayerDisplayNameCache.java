package com.sproutermc.sprouter.common.database.cache;

import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.UuidEntry;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerDisplayNameCache extends AsyncCache<String, UUID> {

    public PlayerDisplayNameCache() {
        super(2); // probably doesn't need to be held for long?
    }

    @Override
    CompletableFuture<UUID> createCacheEntry(String displayNamePlain) {
        return CompletableFuture.supplyAsync(() -> {
            UuidEntry uuidEntry = Tables.playerTable.getUuidEntryByUsernameOrNickname(displayNamePlain);
            return uuidEntry == null ? null : uuidEntry.getUuid();
        });
    }
}
