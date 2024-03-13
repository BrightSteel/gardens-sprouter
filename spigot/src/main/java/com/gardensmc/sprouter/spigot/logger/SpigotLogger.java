package com.gardensmc.sprouter.spigot.logger;

import com.sproutermc.sprouter.common.logger.SprouterLogger;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class SpigotLogger implements SprouterLogger {

    @Override
    public void info(String message) {
        Bukkit.getLogger().info(message);
    }

    @Override
    public void warn(String message) {
        Bukkit.getLogger().warning(message);
    }

    @Override
    public void error(String message) {
        Bukkit.getLogger().log(Level.SEVERE, message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        Bukkit.getLogger().log(Level.SEVERE, message, throwable);
    }
}
