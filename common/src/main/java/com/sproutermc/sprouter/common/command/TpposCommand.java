package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.type.PlayerExecutorCommand;
import com.sproutermc.sprouter.common.user.UserMessageHandler;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.world.SprouterLocation;

public class TpposCommand extends PlayerExecutorCommand {

    public TpposCommand() {
        super("tppos", 3);
    }

    @Override
    public void execute(SprouterPlayer executor, String[] args) {
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            float yaw = args.length > 3 ? Float.parseFloat(args[3]) : 0;
            float pitch = args.length > 4 ? Float.parseFloat(args[4]) : 0;
            String world = args.length > 5 ? args[5] : null;
            if (executor.teleport(new SprouterLocation(x, y, z, yaw, pitch, world))) {
                new UserMessageHandler(executor).sendMessage("Teleporting to specified location...");
            } else {
                throw new InvalidArgumentException("world");
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException();
        }
    }
}
