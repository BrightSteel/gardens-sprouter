package com.sproutermc.sprouter.common;

import com.sproutermc.sprouter.common.database.DatabaseConnector;
import com.sproutermc.sprouter.common.database.DatabasePreparation;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.cache.PlayerEntryCache;
import com.sproutermc.sprouter.common.logger.SprouterLogger;

import java.io.File;

public class GardensSprouter {

    public static SprouterLogger LOGGER;

    // db
    public static DatabaseConnector dbConnector;
    public static DatabasePreparation dbPreparation;

    // cache
    public static PlayerEntryCache playerEntryCache;

    // needs to be called from both spigot and fabric
    public static void initialize(File pluginDir, SprouterLogger logger) {
        LOGGER = logger;

        // db order matters
        dbConnector = new DatabaseConnector(pluginDir);
        dbPreparation = new DatabasePreparation();
        Tables.init();
        dbPreparation.executePrepareStatements();

        // caches
        playerEntryCache = new PlayerEntryCache();
    }
}
