package com.sproutermc.sprouter.common.database;

import com.sproutermc.sprouter.common.database.table.MessageTable;
import com.sproutermc.sprouter.common.database.table.PlayerTable;

public class Tables {

    public static PlayerTable playerTable;
    public static MessageTable messageTable;

    public static void init() {
        playerTable = new PlayerTable();
        messageTable = new MessageTable();
    }
}
