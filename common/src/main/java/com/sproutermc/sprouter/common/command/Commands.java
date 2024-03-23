package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused") // registered commands look unused unless they're called elsewhere
public class Commands {

    @Getter
    private static final Set<SprouterCommand> commands = new HashSet<>();

    private static final SprouterCommand nicknameCommand = new NicknameCommand();
    private static final SprouterCommand gameModeCommand = new GameModeCommand();
    private static final SprouterCommand flyCommand = new FlyCommand();
    private static final SprouterCommand meCommand = new MeCommand();
    private static final SprouterCommand messageCommand = new MessageCommand();
    private static final SprouterCommand tpaCommand = new TpaCommand();
    private static final SprouterCommand tpaHereCommand = new TpaHereCommand();
    private static final SprouterCommand tpCommand = new TpCommand();
    private static final SprouterCommand tpHereCommand = new TpHereCommand();
    private static final SprouterCommand tpposCommand = new TpposCommand();

    public static void addCommandToRegistry(SprouterCommand sprouterCommand) {
        commands.add(sprouterCommand);
    }
}
