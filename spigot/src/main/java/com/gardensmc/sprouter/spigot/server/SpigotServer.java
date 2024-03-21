package com.gardensmc.sprouter.spigot.server;

import com.gardensmc.sprouter.spigot.SprouterSpigot;
import com.gardensmc.sprouter.spigot.user.SpigotPlayer;
import com.sproutermc.sprouter.common.chat.ChatUtil;
import com.sproutermc.sprouter.common.server.SprouterServer;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class SpigotServer extends SprouterServer {

    @Override
    public SprouterPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player == null ? null : new SpigotPlayer(player);
    }

    @Override
    public SprouterPlayer getPlayerExact(String username) {
        Player player = Bukkit.getPlayerExact(username);
        return player == null ? null : new SpigotPlayer(player);
    }

    @Override
    public List<? extends SprouterPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(SpigotPlayer::new)
                .toList();
    }

    @Override
    public File getWorkingDirectory() {
        return SprouterSpigot.getPlugin().getDataFolder();
    }

    @Override
    public void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(ChatUtil.translateColors(message));
    }
}
