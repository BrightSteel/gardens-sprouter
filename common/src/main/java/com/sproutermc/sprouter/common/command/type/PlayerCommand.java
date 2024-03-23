package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.command.exception.NoPermissionException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;

/**
 * For commands following this format:
 * /<command> <...requiredArgs> [...optionalArgs] [optionalPlayer]
 */
public abstract class PlayerCommand extends SprouterCommand {

    protected final String permissionNodeOthers;
    protected final int optionalArgs;

    public PlayerCommand(String name, int requiredArgs, int optionalArgs) {
        super(name, requiredArgs);
        this.optionalArgs = optionalArgs;
        this.permissionNodeOthers = buildPermissionNodeOthers(name);
    }

    public PlayerCommand(String name, int requiredArgs) {
        this(name, requiredArgs, 0);
    }

    public PlayerCommand(String name) {
        this(name, 0);
    }

    public void executeOnSelf(SprouterPlayer player, String[] args) {
        if (!player.hasPermission(permissionNode)) {
            throw new NoPermissionException(permissionNode);
        }
    }

    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        if (!executor.hasPermission(permissionNodeOthers)) {
            throw new NoPermissionException(permissionNodeOthers);
        }
    }

    @Override
    public void execute(OnlineUser onlineUser, String[] args) {
        if (args.length < requiredArgs) {
            throw new InvalidArgumentException();
        } else if (args.length > requiredArgs + optionalArgs) { // player parameter was specified
            String username = args[requiredArgs + optionalArgs];
            // check if user specified themselves
            if (onlineUser instanceof SprouterPlayer sprouterPlayer && sprouterPlayer.getUsername().equalsIgnoreCase(username)) {
                executeOnSelf(sprouterPlayer, args);
            } else {
                SprouterPlayer targetPlayer = GardensSprouter.getSprouterServer().getPlayer(username);
                if (targetPlayer == null) {
                    throw new PlayerNotFoundException(username);
                }
                executeOnPlayer(onlineUser, targetPlayer, args);
            }
        } else {
            if (onlineUser instanceof SprouterPlayer sprouterPlayer) {
                executeOnSelf(sprouterPlayer, args);
            } else {
                throw new InvalidUserException();
            }
        }
    }

    private String buildPermissionNodeOthers(String name) {
        return String.format("sprouter.%s.others", name);
    }
}
