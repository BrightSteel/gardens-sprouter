package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.user.OnlineUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotSprouterUserFactory {

    public static OnlineUser createOnlineUser(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            return new SpigotPlayer(player);
        }
        return new SpigotConsole(commandSender);
    }
}
