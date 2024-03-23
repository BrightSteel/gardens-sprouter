package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import com.sproutermc.sprouter.common.chat.ChatMessageHandler;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterOfflinePlayer;

import java.util.Arrays;

public class MessageCommand extends SprouterCommand {

    public MessageCommand() {
        super("msg", 1);
    }

    @Override
    public void execute(OnlineUser sprouterUser, String[] args) {
        super.execute(sprouterUser, args);
        if (args.length == 0) {
            // todo - view unread messages
        } else if (args.length == 1) {
            // missing message argument
            throw new InvalidArgumentException();
        } else {
            String targetName = args[0];
            SprouterOfflinePlayer targetPlayer = GardensSprouter.getSprouterServer().getPlayerByDisplayName(targetName);
            if (targetPlayer == null) {
                throw new PlayerNotFoundException(targetName);
            }
            String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            new ChatMessageHandler(sprouterUser, targetPlayer).sendMessage(message);
        }
    }
}
