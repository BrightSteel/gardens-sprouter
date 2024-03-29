package com.sproutermc.sprouter.common.database.table;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.database.entry.UuidEntry;

import java.sql.SQLException;

public class PlayerTable extends Table {

    @Override
    public String getPrepareStatement() {
        return """
               CREATE TABLE IF NOT EXISTS player(
                    uuid varchar(36) NOT NULL,
                    username varchar(16) NOT NULL,
                    nickname varchar(128),
                    nickname_plain varchar(32),
                    seen TEXT,
                    PRIMARY KEY (uuid)
               )
               """;
    }

    public PlayerEntry getEntry(String uuid) {
        try {
            String query = "SELECT * FROM player WHERE uuid = ?";
            return (PlayerEntry) GardensSprouter.getDbConnector().querySingleton(query, new PlayerEntry(), uuid);
        } catch (SQLException e) {
            logError(Operation.GET, e);
            return null;
        }
    }

    public UuidEntry getUuidEntryByUsernameOrNickname(String usernameOrNickname) {
        try {
            String query = "SELECT uuid FROM player WHERE LOWER(username) = ? OR LOWER(nickname_plain) = ? LIMIT 1";
            return (UuidEntry) GardensSprouter.getDbConnector().querySingleton(query, new UuidEntry(),
                    usernameOrNickname.toLowerCase(),
                    usernameOrNickname.toLowerCase()
            );
        } catch (SQLException e) {
            logError(Operation.GET, e);
            return null;
        }
    }

    public boolean createEntry(PlayerEntry playerEntry) {
        try {
            String update = """
                            INSERT INTO player (
                                uuid,
                                username,
                                seen
                            ) VALUES (?, ?, ?)
                            """;
            GardensSprouter.getDbConnector().executeUpdate(update,
                    playerEntry.getUuid().toString(),
                    playerEntry.getUsername(),
                    playerEntry.getSeen().toString()
            );
            return true;
        } catch (SQLException e) {
            logError(Operation.CREATE, e);
            return false;
        }
    }

    public boolean updateEntry(PlayerEntry playerEntry) {
        try {
            String update = """
                            UPDATE player
                            SET username = ?, nickname = ?, nickname_plain = ?, seen = ?
                            WHERE uuid = ?
                            """;
            GardensSprouter.getDbConnector().executeUpdate(update,
                    playerEntry.getUsername(),
                    playerEntry.getNickname(),
                    playerEntry.getNicknamePlain(),
                    playerEntry.getSeen().toString(),
                    playerEntry.getUuid().toString()
            );
            return true;
        } catch (SQLException e) {
            logError(Operation.UPDATE, e);
            return false;
        }
    }
}
