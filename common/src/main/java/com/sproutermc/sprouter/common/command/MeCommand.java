package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterUser;

import java.util.Collections;
import java.util.List;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;

public class MeCommand extends SprouterCommand {

    public MeCommand() {
        super("me", 1);
    }

    @Override
    public void execute(OnlineUser sprouterUser, String[] args) {
        super.execute(sprouterUser, args);
        String message = String.join(" ", args);
        GardensSprouter.getSprouterServer().broadcastMessage(
                String.format("&d* %s &d%s", formatPlayer(sprouterUser.getDisplayName()), message)
        );
    }

    @Override
    public List<String> getTabCompletion(String[] args) {
        return Collections.emptyList();
    }
}
