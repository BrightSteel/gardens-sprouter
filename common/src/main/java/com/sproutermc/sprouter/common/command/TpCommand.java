package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.PlayerTpHandler;
import com.sproutermc.sprouter.common.user.player.PlayerTpRequest;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;

public class TpCommand extends PlayerCommand {

    public TpCommand() {
        super("tp", 1, 0);
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        super.executeOnSelf(player, args);
        SprouterPlayer targetPlayer = getPlayerOrThrow(args[0]);
        PlayerTpHandler.sendForcedTeleport(player, targetPlayer, PlayerTpRequest.Direction.TO);
    }

    @Override
    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        super.executeOnPlayer(executor, targetPlayer, args);
        String targetUsername = args[0];
        SprouterPlayer targetPlayer1 = GardensSprouter.getSprouterServer().getPlayer(targetUsername);
        if (targetPlayer1 == null) {
            throw new PlayerNotFoundException(targetUsername);
        }
        PlayerTpHandler.sendForcedTeleport(executor, targetPlayer1, targetPlayer);
    }
}
