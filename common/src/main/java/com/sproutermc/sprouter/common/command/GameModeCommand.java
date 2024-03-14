package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.user.SprouterUser;

public class GameModeCommand extends PlayerCommand {

    public GameModeCommand() {
        super("gamemode", 1);
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        SprouterGameMode gameMode = getSprouterGameMode(args[0]);
        player.setGameMode(gameMode);

        player.sendMessage("Set game mode to " + gameMode.toString().toLowerCase());
    }

    @Override
    public void executeOnPlayer(SprouterUser executor, SprouterPlayer targetPlayer, String[] args) {
        SprouterGameMode gameMode = getSprouterGameMode(args[0]);
        String gameModePretty = gameMode.toString().toLowerCase();
        targetPlayer.setGameMode(gameMode);

        targetPlayer.sendMessage(String.format("%s set your game mode to %s", executor.getName(), gameModePretty));
        executor.sendMessage(String.format("Set %s's game mode to %s", targetPlayer.getUsername(), gameModePretty));
    }

    private SprouterGameMode getSprouterGameMode(String gameMode) {
        try {
            return SprouterGameMode.valueOf(gameMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException();
        }
    }
}
