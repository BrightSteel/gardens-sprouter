package com.sproutermc.sprouter.common.database.table;

import com.sproutermc.sprouter.common.GardensSprouter;

public abstract class Table {

    public abstract String getPrepareStatement();

    public Table() {
        GardensSprouter.dbPreparation.addStatement(getPrepareStatement());
    }

    protected void logError(Operation operation, Exception e) {
        GardensSprouter.LOGGER.error(String.format("Failed to %s entry", operation), e);
    }

    public enum Operation {
        CREATE, UPDATE, GET
    }
}
