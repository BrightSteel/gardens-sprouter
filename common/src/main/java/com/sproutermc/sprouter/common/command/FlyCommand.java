package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.user.SprouterUser;
import com.sproutermc.sprouter.common.user.UserMessageHandler;

public class FlyCommand extends PlayerCommand {

    public FlyCommand() {
        super("fly");
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        super.executeOnSelf(player, args);
        String flyMode = toggleFlyMode(player);
        new UserMessageHandler(player).sendMessage("Set fly mode to " + flyMode);
    }

    @Override
    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        super.executeOnPlayer(executor, targetPlayer, args);
        String flyMode = toggleFlyMode(targetPlayer);
        new UserMessageHandler(executor).sendMessage(
                String.format("Set %s's fly mode to %s", targetPlayer.getDisplayName(), flyMode)
        );
        new UserMessageHandler(targetPlayer).sendMessage(
                String.format("%s set your fly mode to %s", executor.getDisplayName(), flyMode)
        );
    }

    private String toggleFlyMode(SprouterPlayer player) {
        player.setFlyAllowed(!player.isFlyAllowed());
        return player.isFlyAllowed() ? "enabled" : "disabled";
    }
}
