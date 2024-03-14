package com.sproutermc.sprouter.common.database;

import com.sproutermc.sprouter.common.GardensSprouter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabasePreparation {

    private final List<String> prepareStatements;

    public DatabasePreparation() {
        this.prepareStatements = new ArrayList<>();
    }

    public void addStatement(String statement) {
        prepareStatements.add(statement);
    }

    public void executePrepareStatements() {
        try {
            for (String statement : prepareStatements) {
                GardensSprouter.getDbConnector().executeUpdate(statement);
            }
        } catch (SQLException e) {
            GardensSprouter.getSprouterLogger().error("Failed to prepare database!", e);
        }
    }
}
