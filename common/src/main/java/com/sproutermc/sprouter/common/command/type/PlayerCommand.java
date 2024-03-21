package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.command.exception.NoPermissionException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterPlayer;

public abstract class PlayerCommand extends SprouterCommand {

    protected final String permissionNodeOthers;

    public PlayerCommand(String name, int requiredArgs) {
        super(name, requiredArgs);
        this.permissionNodeOthers = buildPermissionNodeOthers(name);
    }

    public PlayerCommand(String name) {
        super(name);
        this.permissionNodeOthers = buildPermissionNodeOthers(name);
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
        } else if (args.length > requiredArgs) { // optional player parameter was specified
            String username = args[requiredArgs];
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
