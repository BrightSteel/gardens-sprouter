package com.gardensmc.sprouter.spigot.listener;

import com.gardensmc.sprouter.spigot.user.SpigotSprouterPlayer;
import com.sproutermc.sprouter.common.listener.PlayerJoinGameListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("unused") // events look unused but are automatically called by Spigot
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoinGame(PlayerJoinEvent event) {
        new PlayerJoinGameListener(new SpigotSprouterPlayer(event.getPlayer())).callListener();
    }
}
