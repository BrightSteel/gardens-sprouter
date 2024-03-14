package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.user.SprouterUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotUserFactory {

    public static SprouterUser createUser(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            return new SpigotPlayer(player);
        }
        return new SpigotCommandSender(commandSender);
    }
}
