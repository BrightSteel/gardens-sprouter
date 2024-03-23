package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;

public abstract class PlayerExecutorCommand extends SprouterCommand{

    public PlayerExecutorCommand(String name) {
        super(name);
    }

    public PlayerExecutorCommand(String name, int requiredArgs) {
        super(name, requiredArgs);
    }

    @Override
    public void execute(OnlineUser onlineUser, String[] args) {
        super.execute(onlineUser, args);
        if (onlineUser instanceof SprouterPlayer player) {
            execute(player, args);
        } else {
            throw new InvalidUserException();
        }
    }

    public abstract void execute(SprouterPlayer executor, String[] args);
}
