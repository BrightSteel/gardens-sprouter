package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.DatabaseException;
import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.PlayerEntry;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.user.UserMessageHandler;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatEmphasis;
import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;

public class NicknameCommand extends PlayerCommand {

    public NicknameCommand() {
        super("nickname", 1);
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        super.executeOnSelf(player, args);
        String nickname = args[0];
        if (nickname.equalsIgnoreCase("off")) {
            nickname = null;
        }
        if (updateNickname(player.getUniqueId(), nickname)) {
            player.setTabListName(player.getDisplayName());
            new UserMessageHandler(player).sendMessage(nickname == null ? "Removed nickname" : "Set nickname to " + formatEmphasis(nickname));
        } else {
            throw new DatabaseException();
        }
    }

    @Override
    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        super.executeOnPlayer(executor, targetPlayer, args);
        String nickname = args[0];
        if (nickname.equalsIgnoreCase("off")) {
            nickname = null;
        }
        if (updateNickname(targetPlayer.getUniqueId(), nickname)) {
            targetPlayer.setTabListName(targetPlayer.getDisplayName());
            new UserMessageHandler(executor).sendMessage(
                    nickname == null
                            ? String.format("Removed %s's nickname", formatPlayer(targetPlayer.getUsername()))
                            : String.format("Set %s's nickname to %s", formatPlayer(targetPlayer.getUsername()), formatEmphasis(nickname))
            );
            new UserMessageHandler(executor).sendMessage(
                    nickname == null
                            ? String.format("%s removed your nickname", formatPlayer(executor.getDisplayName()))
                            : String.format("%s set your nickname to %s", formatPlayer(executor.getDisplayName()), formatEmphasis(nickname))
            );
        } else {
            throw new DatabaseException();
        }
    }

    @Override
    public List<String> getTabCompletion(String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("<nickname/off>");
        }
        return super.getTabCompletion(args);
    }

    private boolean updateNickname(UUID playerUUID, String nickname) {
        PlayerEntry playerEntry = GardensSprouter.getPlayerEntryCache().awaitGet(playerUUID);
        CompletableFuture<Boolean> promise = CompletableFuture.supplyAsync(() ->
                Tables.playerTable.updateEntry(playerEntry.setNickname(nickname))
        );
        try {
            boolean success = promise.get();
            GardensSprouter.getPlayerEntryCache().remove(playerUUID);
            if (playerEntry.getNickname() != null) {
                // cache can hold both/either username and nickname as keys
                GardensSprouter.getPlayerDisplayNameCache().remove(playerEntry.getUsername());
                GardensSprouter.getPlayerDisplayNameCache().remove(playerEntry.getNicknamePlain());
            }
            return success;
        } catch (Exception e) {
            return false;
        }
    }
}
