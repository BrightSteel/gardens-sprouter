package com.gardensmc.sprouter.spigot;

import com.gardensmc.sprouter.spigot.listener.ListenerHandler;
import com.gardensmc.sprouter.spigot.logger.SpigotLogger;
import com.gardensmc.sprouter.spigot.server.SpigotServer;
import com.gardensmc.sprouter.spigot.user.SpigotUserFactory;
import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SprouterSpigot extends JavaPlugin {

    @Getter
    private static SprouterSpigot plugin;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        plugin = this;

        GardensSprouter.initialize(new SpigotLogger(), new SpigotServer());
        Commands.getCommands().forEach(this::registerCommand);

        // register handlers
        new ListenerHandler().registerListeners();
    }

    private void registerCommand(SprouterCommand sprouterCommand) {
        Objects.requireNonNull(this.getCommand(sprouterCommand.getName())).setExecutor(
                (sender, command, label, args) -> {
                    try {
                        sprouterCommand.execute(SpigotUserFactory.createUser(sender), args);
                    } catch (InvalidUserException e) {
                        sender.sendMessage("Console cannot perform this command!");
                    } catch (InvalidArgumentException e) {
                        sender.sendMessage("Invalid arguments provided");
                    } catch (PlayerNotFoundException e) {
                        sender.sendMessage(String.format("Provided player '%s' could not be found", e.getUsername()));
                    }
                    return true;
                });
    }
}
