package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import com.sproutermc.sprouter.common.user.SprouterUser;

public class NicknameCommand extends SprouterCommand {

    public NicknameCommand() {
        super("nickname");
    }

    @Override
    public void execute(SprouterUser sprouterUser, String[] args) {
        sprouterUser.sendMessage("hey");
    }
}
