package com.sproutermc.sprouter.common.database.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UuidEntry extends DatabaseEntry {

    // todo make this an embedded entry and use in playerEntry
    private UUID uuid;

    @Override
    public void populate(ResultSet rs) throws SQLException {
        uuid = UUID.fromString(rs.getString("uuid"));
    }
}
