package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.user.SprouterUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public class SpigotCommandSender extends SprouterUser {

    private final CommandSender commandSender;

    public SpigotCommandSender(CommandSender commandSender) {
        super("Console");
        this.commandSender = commandSender;
    }

    @Override
    public void sendMessage(String message) {
        commandSender.sendMessage(message);
    }
}
