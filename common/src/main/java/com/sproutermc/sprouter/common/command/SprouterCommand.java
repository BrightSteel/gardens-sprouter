package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.user.User;
import lombok.Data;

@Data
public abstract class SprouterCommand {
    private String name;

    public SprouterCommand(String name) {
        this.name = name;
        Commands.addCommandToRegistry(this);
    }

    public abstract void execute(User user, String[] args);
}
