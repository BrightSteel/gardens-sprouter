package com.sproutermc.sprouter.common.command;

import com.sproutermc.sprouter.common.command.exception.InvalidArgumentException;
import com.sproutermc.sprouter.common.command.type.PlayerCommand;
import com.sproutermc.sprouter.common.state.SprouterOnOff;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import com.sproutermc.sprouter.common.user.UserMessageHandler;

import java.util.Collections;
import java.util.List;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatEmphasis;
import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;


public class FlyCommand extends PlayerCommand {

    public FlyCommand() {
        super("fly", 0, 1);
    }

    @Override
    public void executeOnSelf(SprouterPlayer player, String[] args) {
        super.executeOnSelf(player, args);
        SprouterOnOff onOffState = getOnOffState(player, args);
        player.setFlyAllowed(onOffState.isOn());
        new UserMessageHandler(player).sendMessage("Set fly mode to " + formatEmphasis(onOffState.toString().toLowerCase()));
    }

    @Override
    public void executeOnPlayer(OnlineUser executor, SprouterPlayer targetPlayer, String[] args) {
        super.executeOnPlayer(executor, targetPlayer, args);
        SprouterOnOff onOffState = getOnOffState(targetPlayer, args);
        targetPlayer.setFlyAllowed(onOffState.isOn());
        String flyModePretty = formatEmphasis(onOffState.toString().toLowerCase());
        new UserMessageHandler(executor).sendMessage(
                String.format("Set %s's fly mode to %s", formatPlayer(targetPlayer.getDisplayName()), flyModePretty)
        );
        new UserMessageHandler(targetPlayer).sendMessage(
                String.format("%s set your fly mode to %s", formatPlayer(executor.getDisplayName()), flyModePretty)
        );
    }

    @Override
    public List<String> getTabCompletion(String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("[on|off]");
        }
        return super.getTabCompletion(args);
    }

    private SprouterOnOff getOnOffState(SprouterPlayer player, String[] args) {
        if (args.length > requiredArgs) { // specified on/off state
            try {
                System.out.println(args[requiredArgs]);
                return SprouterOnOff.valueOf(args[requiredArgs].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidArgumentException();
            }
        }
        // get opposite of current state to effectively toggle it
        return player.isFlyAllowed() ? SprouterOnOff.OFF : SprouterOnOff.ON;
    }
}
