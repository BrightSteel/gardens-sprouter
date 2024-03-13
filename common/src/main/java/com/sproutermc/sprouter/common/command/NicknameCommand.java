package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.user.SprouterPlayer;

public class NicknameCommand extends SprouterCommand {

    public NicknameCommand() {
        super("nickname");
    }

    @Override
    public void execute(SprouterPlayer sprouterPlayer, String[] args) {
        sprouterPlayer.sendMessage("hey");
    }
}
