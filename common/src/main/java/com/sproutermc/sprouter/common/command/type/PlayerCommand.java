package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.user.SprouterUser;

public abstract class PlayerCommand extends SprouterCommand {
    // todo might want to make a subtype that is RequiredArgsCommand or something, or maybe just include in normal one
    private final int requiredArgs;

    public PlayerCommand(String name, int requiredArgs) {
        super(name);
        this.requiredArgs = requiredArgs;
    }

    @Override
    public void execute(SprouterUser sprouterUser, String[] args) {
        if (args.length < requiredArgs) {
            throw new InvalidArgumentException();
        } else if (args.length > requiredArgs) { // optional player parameter was specified
            String username = args[requiredArgs];
            // check if user specified themselves
            if (sprouterUser instanceof SprouterPlayer sprouterPlayer && sprouterPlayer.getUsername().equalsIgnoreCase(username)) {
                executeOnSelf(sprouterPlayer, args);
            } else {
                SprouterPlayer targetPlayer = GardensSprouter.getSprouterServer().getOnlinePlayer(username);
                if (targetPlayer == null) {
                    throw new PlayerNotFoundException(username);
                }
                executeOnPlayer(sprouterUser, targetPlayer, args);
            }
        } else {
            if (sprouterUser instanceof SprouterPlayer sprouterPlayer) {
                executeOnSelf(sprouterPlayer, args);
            } else {
                throw new InvalidUserException();
            }
        }
    }

    public abstract void executeOnSelf(SprouterPlayer player, String[] args);

    public abstract void executeOnPlayer(SprouterUser executor, SprouterPlayer targetPlayer, String[] args);
}
