package com.sproutermc.sprouter.common.database;

import com.sproutermc.sprouter.common.database.table.PlayerTable;

public class Tables {

    public static PlayerTable playerTable;

    public static void init() {
        playerTable = new PlayerTable();
    }
}
