package com.sproutermc.sprouter.common.listener;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.listener.type.PlayerListener;
import com.sproutermc.sprouter.common.user.SprouterPlayer;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class PlayerJoinGameListener extends PlayerListener {

    public PlayerJoinGameListener(SprouterPlayer sprouterPlayer) {
        super(sprouterPlayer);
    }

    @Override
    public void callListener() {
        // todo - might want to move to a handler
        LocalDateTime currentTime = LocalDateTime.now(Clock.systemUTC());
        PlayerEntry playerEntry = GardensSprouter.playerEntryCache.awaitGet(sprouterPlayer.getUuid());
        if (playerEntry == null) {
            // create entry if none exist; ensures players always have entry in db
            CompletableFuture.supplyAsync(() -> Tables.playerTable.createEntry(
                    new PlayerEntry()
                            .setUuid(sprouterPlayer.getUuid())
                            .setUsername(sprouterPlayer.getUsername())
                            .setSeen(currentTime)
            ));
        } else if (!playerEntry.getUsername().equals(sprouterPlayer.getUsername())) {
            // update entry - username and seen
            CompletableFuture.supplyAsync(() -> Tables.playerTable.updateEntry(
                    playerEntry
                            .setUsername(sprouterPlayer.getUsername())
                            .setSeen(currentTime)
            ));
        }
    }
}
