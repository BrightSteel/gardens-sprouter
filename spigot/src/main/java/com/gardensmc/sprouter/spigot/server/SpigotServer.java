package com.gardensmc.sprouter.spigot.server;

import com.gardensmc.sprouter.spigot.SprouterSpigot;
import com.gardensmc.sprouter.spigot.user.SpigotPlayer;
import com.sproutermc.sprouter.common.server.SprouterServer;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.util.UUID;

public class SpigotServer implements SprouterServer {

    @Override
    @Nullable
    public SprouterPlayer getOnlinePlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player == null ? null : new SpigotPlayer(player);
    }

    @Override
    public SprouterPlayer getOnlinePlayer(String username) {
        Player player = Bukkit.getPlayer(username);
        return player == null ? null : new SpigotPlayer(player);
    }

    @Override
    public File getWorkingDirectory() {
        return SprouterSpigot.getPlugin().getDataFolder();
    }
}
