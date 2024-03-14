package com.gardensmc.sprouter.spigot.listener;

import com.gardensmc.sprouter.spigot.SprouterSpigot;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ListenerHandler {

    public static Set<Listener> listeners = new HashSet<>();

    public ListenerHandler() {
        // register listeners here
        listeners = Set.of(
                new PlayerListener()
        );
    }

    public void registerListeners() {
        listeners.forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(
                listener, SprouterSpigot.getPlugin())
        );
    }
}
