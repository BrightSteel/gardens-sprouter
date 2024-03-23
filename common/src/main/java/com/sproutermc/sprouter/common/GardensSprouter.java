package com.sproutermc.sprouter.common;

import com.sproutermc.sprouter.common.database.DatabaseConnector;
import com.sproutermc.sprouter.common.database.DatabasePreparation;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.cache.PlayerDisplayNameCache;
import com.sproutermc.sprouter.common.database.cache.PlayerEntryCache;
import com.sproutermc.sprouter.common.cache.SimpleCache;
import com.sproutermc.sprouter.common.logger.SprouterLogger;
import com.sproutermc.sprouter.common.server.SprouterServer;
import com.sproutermc.sprouter.common.user.player.PlayerTpRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Setters are available for testing only.
 */
public class GardensSprouter {
    @Getter
    private static SprouterLogger sprouterLogger;

    // server
    @Getter @Setter
    private static SprouterServer sprouterServer;

    // db
    @Getter
    private static DatabaseConnector dbConnector;
    @Getter
    private static DatabasePreparation dbPreparation;

    // cache
    @Getter @Setter
    private static PlayerEntryCache playerEntryCache;
    @Getter @Setter
    private static PlayerDisplayNameCache playerDisplayNameCache;
    @Getter @Setter
    private static SimpleCache<UUID, PlayerTpRequest> playerTpCache;

    // needs to be called from both spigot and fabric
    public static void initialize(SprouterLogger logger, SprouterServer server) {
        sprouterLogger = logger;
        sprouterServer = server;

        // db order matters
        dbConnector = new DatabaseConnector();
        dbPreparation = new DatabasePreparation();
        Tables.init();
        dbPreparation.executePrepareStatements();

        // caches
        playerEntryCache = new PlayerEntryCache();
        playerDisplayNameCache = new PlayerDisplayNameCache();
        playerTpCache = new SimpleCache<>(2);
    }
}
