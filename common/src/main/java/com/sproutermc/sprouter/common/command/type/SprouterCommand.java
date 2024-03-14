package com.sproutermc.sprouter.common.command.type;

import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.user.SprouterUser;
import lombok.Getter;


@Getter
public abstract class SprouterCommand {

    private final String name;

    public SprouterCommand(String name) {
        this.name = name;
        Commands.addCommandToRegistry(this);
    }

    /**
     * Executes command
     * @throws com.sproutermc.sprouter.common.command.exception.InvalidArgumentException
     *  if user-provided arguments are invalid
     * @throws com.sproutermc.sprouter.common.command.exception.InvalidUserException
     *  if user cannot perform the command
     * @throws com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException
     *  if user-provided player cannot be found
     */
    public abstract void execute(SprouterUser sprouterUser, String[] args);
}
