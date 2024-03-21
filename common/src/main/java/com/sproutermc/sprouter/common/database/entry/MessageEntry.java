package com.sproutermc.sprouter.common.database.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MessageEntry extends DatabaseEntry {

    private String senderUUID, recipientUUID;
    private String content;
    private LocalDateTime timeStamp;
    private boolean read;

    @Override
    public void populate(ResultSet rs) throws SQLException {
        this.senderUUID = rs.getString("sender_uuid");
        this.recipientUUID = rs.getString("recipient_uuid");
        this.content = rs.getString("content");
        this.timeStamp = LocalDateTime.parse(rs.getString("time_stamp"));
        this.read = rs.getBoolean("read");
    }
}
