package com.gardensmc.sprouter.spigot.listener;

import com.gardensmc.sprouter.spigot.user.SpigotPlayer;
import com.sproutermc.sprouter.common.listener.PlayerChatListener;
import com.sproutermc.sprouter.common.listener.PlayerJoinGameListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("unused") // events look unused but are automatically called by Spigot
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoinGame(PlayerJoinEvent event) {
        new PlayerJoinGameListener(new SpigotPlayer(event.getPlayer())).callListener();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        new PlayerChatListener(
                new SpigotPlayer(e.getPlayer()), e.getMessage()
        ).callListener();
    }
}
