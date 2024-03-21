package com.sproutermc.sprouter.common.database.table;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.database.entry.MessageEntry;

import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class MessageTable extends Table {

    // todo - need AUTO INCREMENT keyword for MySQL?
    @Override
    public String getPrepareStatement() {
        return """
               CREATE TABLE IF NOT EXISTS message(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sender_uuid varchar(36) NOT NULL,
                    recipient_uuid varchar(36) NOT NULL,
                    content text NOT NULL,
                    time_stamp text NOT NULL,
                    read boolean NOT NULL
               )
               """;
    }

    // start at page 0
    public List<MessageEntry> getUnreadEntries(String recipientUUID, int page) {
        try {
            String query = """
                SELECT * FROM message WHERE recipient_uuid = ? AND read = FALSE
                ORDER BY id ASC
                LIMIT 5
                OFFSET ?
            """;
            return getPaged(query, recipientUUID, page);
        } catch (SQLException e) {
            logError(Operation.GET, e);
            return null;
        }
    }

    public List<MessageEntry> getLatestEntries(String recipientUUID, int page) {
        try {
            String query = """
                SELECT * FROM message WHERE recipient_uuid = ?
                ORDER BY id ASC
                LIMIT 5
                OFFSET ?
            """;
            return getPaged(query, recipientUUID, page);
        } catch (SQLException e) {
            logError(Operation.GET, e);
            return null;
        }
    }

    public void updateEntry(int id) {
        try {
            String update = "UPDATE message SET read = TRUE WHERE id = ?";
            GardensSprouter.getDbConnector().executeUpdate(update, id);
        } catch (SQLException e) {
            logError(Operation.UPDATE, e);
        }
    }

    public boolean createEntry(MessageEntry messageEntry) {
        try {
            String update = """
                            INSERT INTO message (
                                sender_uuid,
                                recipient_uuid,
                                content,
                                time_stamp,
                                read
                            ) VALUES (?, ?, ?, ?, ?)
                            """;
            GardensSprouter.getDbConnector().executeUpdate(update,
                    messageEntry.getSenderUUID(),
                    messageEntry.getRecipientUUID(),
                    messageEntry.getContent(),
                    messageEntry.getTimeStamp(),
                    messageEntry.isRead()
            );
            return true;
        } catch (SQLException e) {
            logError(Operation.CREATE, e);
            return false;
        }
    }

    public void deleteReadEntries(String recipientUUID) {
        try {
            String update = "DELETE FROM message WHERE read = TRUE";
            GardensSprouter.getDbConnector().executeUpdate(update);
        } catch (SQLException e) {
            logError(Operation.UPDATE, e);
        }
    }

    public void deleteOldReadEntries() {
        try {
            String update = "DELETE FROM message WHERE read = TRUE AND time_stamp < ?";
            // delete read messages that are older than one week
            LocalDateTime oneWeekAgo = LocalDateTime.now(Clock.systemUTC()).minusDays(7);
            GardensSprouter.getDbConnector().executeUpdate(update, oneWeekAgo);
        } catch (SQLException e) {
            logError(Operation.UPDATE, e);
        }
    }

    private List<MessageEntry> getPaged(String query, String recipientUUID, int page) throws SQLException {
        return GardensSprouter.getDbConnector()
                .queryCollection(query, new MessageEntry(), recipientUUID, page * 5)
                .stream()
                .map(entry -> (MessageEntry) entry)
                .toList();
    }
}
