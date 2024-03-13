package com.gardensmc.sprouter.spigot;

import com.gardensmc.sprouter.spigot.user.SpigotUser;
import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.command.SprouterCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SprouterSpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        Commands.getCommands().forEach(this::registerCommand);
    }

    private void registerCommand(SprouterCommand sprouterCommand) {
        Objects.requireNonNull(this.getCommand(sprouterCommand.getName())).setExecutor(
                (sender, command, label, args) -> {
                    sprouterCommand.execute(new SpigotUser(sender), args);
                    return true;
                });
    }
}
