package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.chat.ChatUtil;
import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.command.exception.NoPermissionException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.user.SprouterUser;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.command.exception.DatabaseException;
import lombok.Getter;

import java.util.List;


@Getter
public abstract class SprouterCommand {

    private final String name;
    protected final String permissionNode;
    protected final int requiredArgs;

    public SprouterCommand(String name, int requiredArgs) {
        this.name = name;
        this.requiredArgs = requiredArgs;
        this.permissionNode = buildPermissionNode(name);
        Commands.addCommandToRegistry(this);
    }

    public SprouterCommand(String name) {
        this(name, 0);
    }

    /**
     * Executes command
     * @throws NoPermissionException
     *  if user does not have permission
     * @throws InvalidArgumentException
     *  if user-provided arguments are invalid
     * @throws InvalidUserException
     *  if user cannot perform the command
     * @throws PlayerNotFoundException
     *  if user-provided player cannot be found
     * @throws DatabaseException
     *  if database interaction fails
     */
    public void execute(OnlineUser onlineUser, String[] args) {
        if (!onlineUser.hasPermission(permissionNode)) {
            throw new NoPermissionException(permissionNode);
        }
    }

    public List<String> getTabCompletion(String[] args) {
        String startsWith = args[args.length - 1];
        return GardensSprouter.getSprouterServer().getOnlinePlayers()
                .stream()
                .map(SprouterPlayer::getDisplayName)
                .map(ChatUtil::stripFormatting)
                .filter(s -> s.toLowerCase().startsWith(startsWith.toLowerCase()))
                .toList();
    }

    private String buildPermissionNode(String name) {
        return "sprouter." + name;
    }
}
