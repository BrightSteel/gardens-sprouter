package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.type.PlayerExecutorCommand;
import com.sproutermc.sprouter.common.user.player.PlayerTpHandler;
import com.sproutermc.sprouter.common.user.player.PlayerTpRequest;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;

public class TpHereCommand extends PlayerExecutorCommand {

    public TpHereCommand() {
        super("tphere", 1);
    }

    @Override
    public void execute(SprouterPlayer executor, String[] args) {
        SprouterPlayer targetPlayer = getPlayerOrThrow(args[0]);
        PlayerTpHandler.sendForcedTeleport(executor, targetPlayer, PlayerTpRequest.Direction.HERE);
    }
}
