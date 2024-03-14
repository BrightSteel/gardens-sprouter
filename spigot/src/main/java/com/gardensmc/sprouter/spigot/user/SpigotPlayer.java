package com.gardensmc.sprouter.spigot.user;

import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.world.Location;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpigotPlayer extends SprouterPlayer {

    private final Player player;

    public SpigotPlayer(Player player) {
        super(player.getUniqueId(), player.getName());
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public void setGameMode(SprouterGameMode gameMode) {
        player.setGameMode(GameMode.valueOf(gameMode.name()));
    }
}
