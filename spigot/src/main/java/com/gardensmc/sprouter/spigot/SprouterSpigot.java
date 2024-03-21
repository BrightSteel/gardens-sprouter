package com.gardensmc.sprouter.spigot;

import com.gardensmc.sprouter.spigot.listener.ListenerHandler;
import com.gardensmc.sprouter.spigot.logger.SpigotLogger;
import com.gardensmc.sprouter.spigot.server.SpigotServer;
import com.gardensmc.sprouter.spigot.user.SpigotSprouterUserFactory;
import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.command.Commands;
import com.sproutermc.sprouter.common.command.exception.NoPermissionException;
import com.sproutermc.sprouter.common.command.exception.PlayerNotFoundException;
import com.sproutermc.sprouter.common.command.type.SprouterCommand;
import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.exception.InvalidUserException;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.UserMessageHandler;
import lombok.Getter;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

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
        PluginCommand command = this.getCommand(sprouterCommand.getName());
        if (command == null) {
            getLogger().log(Level.SEVERE, "Failed to get command: " + sprouterCommand.getName());
        } else {
            command.setExecutor(getCommandExecutor(sprouterCommand));
            command.setTabCompleter(getTabCompleter(sprouterCommand));
        }
    }

    private TabCompleter getTabCompleter(SprouterCommand sprouterCommand) {
        return (sender, command, label, args) -> sprouterCommand.getTabCompletion(args);
    }

    private CommandExecutor getCommandExecutor(SprouterCommand sprouterCommand) {
        return (sender, command, label, args) -> {
            OnlineUser onlineUser = SpigotSprouterUserFactory.createOnlineUser(sender);
            UserMessageHandler userMessageHandler = new UserMessageHandler(onlineUser);
            try {
                sprouterCommand.execute(onlineUser, args);
            } catch (InvalidUserException e) {
                userMessageHandler.sendError("Console cannot perform this command!");
            } catch (InvalidArgumentException e) {
                userMessageHandler.sendError(
                        "Invalid arguments, must be: " + command.getUsage().replace("<command>", command.getName())
                ); // not sure why, but getUsage doesn't do this replacement like /help does
            } catch (PlayerNotFoundException e) {
                userMessageHandler.sendError(String.format("Player '%s' could not be found", e.getUsername()));
            } catch (NoPermissionException e) {
                userMessageHandler.sendError("You do not have permission to use this command!");
            } catch (Exception e) {
                userMessageHandler.sendError("An internal server error occurred");
                SprouterSpigot.plugin.getLogger().log(
                        Level.SEVERE,
                        "Failed to run command: " + command.getName(),
                        e
                );
            }
            return true;
        };
    }
}
