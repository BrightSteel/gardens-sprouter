package com.sproutermc.sprouter.common.listener;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.listener.type.PlayerListener;
import com.sproutermc.sprouter.common.user.SprouterPlayer;

public class PlayerChatListener extends PlayerListener {

    private final String message;

    public PlayerChatListener(SprouterPlayer sprouterPlayer, String message) {
        super(sprouterPlayer);
        this.message = message;
    }

    // todo - make format configurable
    @Override
    public void callListener() {
        GardensSprouter.getSprouterServer().broadcastMessage(
                String.format("%s&8: &7%s", sprouterPlayer.getDisplayName(), message)
        );
    }
}
