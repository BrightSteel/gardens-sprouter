package com.sproutermc.sprouter.common.listener;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.listener.type.PlayerListener;
import com.sproutermc.sprouter.common.user.SprouterPlayer;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerJoinGameListener extends PlayerListener {

    public PlayerJoinGameListener(SprouterPlayer sprouterPlayer) {
        super(sprouterPlayer);
    }

    @Override
    public void callListener() {
        createOrUpdatePlayerEntry();
        sprouterPlayer.setTabListName(sprouterPlayer.getDisplayName());
    }

    private void createOrUpdatePlayerEntry() {
        LocalDateTime currentTime = LocalDateTime.now(Clock.systemUTC());
        UUID playerUUID = UUID.fromString(sprouterPlayer.getUuid());
        PlayerEntry playerEntry = GardensSprouter.getPlayerEntryCache().awaitGet(playerUUID);
        if (playerEntry == null) {
            // create entry if none exist; ensures players always have entry in db
            CompletableFuture.supplyAsync(() -> Tables.playerTable.createEntry(
                    new PlayerEntry()
                            .setUuid(playerUUID)
                            .setUsername(sprouterPlayer.getUsername())
                            .setSeen(currentTime)
            )).thenRun(() -> GardensSprouter.getPlayerEntryCache().remove(playerUUID));
        } else if (!playerEntry.getUsername().equals(sprouterPlayer.getUsername())) {
            // update entry - username and seen
            CompletableFuture.supplyAsync(() -> Tables.playerTable.updateEntry(
                    playerEntry
                            .setUsername(sprouterPlayer.getUsername())
                            .setSeen(currentTime)
            )).thenRun(() -> GardensSprouter.getPlayerEntryCache().remove(playerUUID));
        }
    }
}
