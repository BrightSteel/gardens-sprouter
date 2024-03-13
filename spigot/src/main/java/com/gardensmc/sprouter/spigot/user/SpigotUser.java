package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.user.User;
import com.sproutermc.sprouter.common.world.Location;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotUser implements User {
    private final Player player;

    public SpigotUser(Player player) {
        this.player = player;
    }

    public SpigotUser(CommandSender commandSender) {
        if (commandSender instanceof Player p) {
            this.player = p;
        } else {
            this.player = null;
        }
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage("lala");
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public void setGameMode(String gameMode) {
        player.setGameMode(GameMode.valueOf(gameMode));
    }
}
