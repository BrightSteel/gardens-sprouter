package com.sproutermc.sprouter.common.database.entry;

import com.sproutermc.sprouter.common.chat.ChatUtil;
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
public class PlayerEntry extends DatabaseEntry {

    private UUID uuid;
    private String username;
    private String nickname;
    private LocalDateTime seen; // should be stored as UTC

    @Override
    public void populate(ResultSet rs) throws SQLException {
        LocalDateTime seen = rs.getString("seen") != null
                ? LocalDateTime.parse(rs.getString("seen"))
                : null;
        this.uuid = UUID.fromString(rs.getString("uuid"));
        this.username = rs.getString("username");
        this.nickname = rs.getString("nickname");
        this.seen = seen;
    }

    // denormalized nickname for query purposes
    public String getNicknamePlain() {
        return nickname == null ? null : ChatUtil.stripFormatting(nickname);
    }
}
