package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.user.User;

public class NicknameCommand extends SprouterCommand {

    public NicknameCommand() {
        super("nickname");
    }

    @Override
    public void execute(User user, String[] args) {
        user.sendMessage("hey");
    }
}
