package com.gardensmc.sprouter.spigot;

import com.gardensmc.sprouter.spigot.listener.ListenerHandler;
import com.gardensmc.sprouter.spigot.logger.SpigotLogger;
import com.gardensmc.sprouter.spigot.user.SpigotSprouterPlayer;
import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.command.SprouterCommand;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SprouterSpigot extends JavaPlugin {

    public static SprouterSpigot plugin;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;

        GardensSprouter.initialize(getDataFolder(), new SpigotLogger());
        Commands.getCommands().forEach(this::registerCommand);

        // register handlers
        new ListenerHandler().registerListeners();
    }

    private void registerCommand(SprouterCommand sprouterCommand) {
        Objects.requireNonNull(this.getCommand(sprouterCommand.getName())).setExecutor(
                (sender, command, label, args) -> {
                    sprouterCommand.execute(new SpigotSprouterPlayer(sender), args);
                    return true;
                });
    }
}
