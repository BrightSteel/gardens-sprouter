package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.user.UserMessageHandler;

import java.util.Arrays;
import java.util.List;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatEmphasis;
import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;

public class GameModeCommand extends PlayerCommand  {

    public GameModeCommand() {
        super("gamemode", 1);
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        super.executeOnSelf(player, args);
        SprouterGameMode gameMode = getSprouterGameMode(args[0]);
        player.setGameMode(gameMode);

        new UserMessageHandler(player).sendMessage("Set game mode to " + formatEmphasis(gameMode.toString().toLowerCase()));
    }

    @Override
    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        super.executeOnPlayer(executor, targetPlayer, args);
        SprouterGameMode gameMode = getSprouterGameMode(args[0]);
        String gameModePretty = formatEmphasis(gameMode.toString().toLowerCase());
        targetPlayer.setGameMode(gameMode);

        new UserMessageHandler(targetPlayer).sendMessage(String.format("%s set your game mode to %s", formatPlayer(executor.getDisplayName()), gameModePretty));
        new UserMessageHandler(executor).sendMessage(String.format("Set %s's game mode to %s", formatPlayer(targetPlayer.getDisplayName()), gameModePretty));
    }

    private SprouterGameMode getSprouterGameMode(String gameMode) {
        try {
            return SprouterGameMode.valueOf(gameMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException();
        }
    }

    @Override
    public List<String> getTabCompletion(String[] args) {
        if (args.length == 1) {
            return Arrays.stream(SprouterGameMode.values())
                    .map(sprouterGameMode -> sprouterGameMode.name().toLowerCase())
                    .toList();
        }
        return super.getTabCompletion(args);
    }
}
