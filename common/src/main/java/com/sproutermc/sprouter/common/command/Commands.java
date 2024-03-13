package com.sproutermc.sprouter.common.command;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


public class Commands {

    @Getter
    private static final Set<SprouterCommand> commands = new HashSet<>();

    @SuppressWarnings("unused") // registered commands look unused unless they're called elsewhere
    public static SprouterCommand nicknameCommand = new NicknameCommand();

    public static void addCommandToRegistry(SprouterCommand sprouterCommand) {
        commands.add(sprouterCommand);
    }
}
