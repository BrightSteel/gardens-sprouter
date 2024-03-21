package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.chat.ChatUtil;
import com.sproutermc.sprouter.common.user.SprouterConsole;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public class SpigotConsole extends SprouterConsole {

    private final CommandSender commandSender;

    public SpigotConsole(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public void sendMessage(String message) {
        commandSender.sendMessage(ChatUtil.translateColors(message));
    }
}
