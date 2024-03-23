package com.gardensmc.sprouter.spigot.user;

import com.gardensmc.sprouter.spigot.world.SpigotLocationAdapter;
import com.sproutermc.sprouter.common.chat.ChatUtil;
import com.sproutermc.sprouter.common.state.SprouterGameMode;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.world.SprouterLocation;
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
        player.sendMessage(ChatUtil.translateColors(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        // todo - only allow op if no vault permission plugin exists?
        return player.isOp() || player.hasPermission(permission);
    }

    @Override
    public void teleport(SprouterLocation location) {
        player.teleport(SpigotLocationAdapter.toSpigotLocation(location));
    }

    @Override
    public void setGameMode(SprouterGameMode gameMode) {
        player.setGameMode(GameMode.valueOf(gameMode.name()));
    }

    @Override
    public boolean isFlyAllowed() {
        return player.getAllowFlight();
    }

    @Override
    public void setFlyAllowed(boolean allowed) {
        player.setAllowFlight(allowed);
    }

    @Override
    public void setTabListName(String name) {
        player.setPlayerListName(ChatUtil.translateColors(name));
    }

    @Override
    public SprouterLocation getLocation() {
        return SpigotLocationAdapter.fromSpigotLocation(player.getLocation());
    }
}
