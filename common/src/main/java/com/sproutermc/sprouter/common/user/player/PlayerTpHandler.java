package com.sproutermc.sprouter.common.user.player;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.UserMessageHandler;
import com.sproutermc.sprouter.common.world.SprouterLocation;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;

public class PlayerTpHandler {

    public static void sendTeleportRequest(SprouterPlayer sender, SprouterPlayer recipient, PlayerTpRequest.Direction direction) {
        GardensSprouter.getPlayerTpCache().put(recipient.getUniqueId(), new PlayerTpRequest(
                sender.getUniqueId(),
                direction,
                direction == PlayerTpRequest.Direction.HERE ? sender.getLocation() : null // only save for here
        ));

        // send messages
        new UserMessageHandler(sender).sendMessage(String.format("Sent teleport request to %s. Request will expire in 2 minutes.", formatPlayer(recipient.getDisplayName())));
        new UserMessageHandler(recipient).sendMessage(
                direction == PlayerTpRequest.Direction.TO
                        ? String.format("%s would like to teleport to you. Type /tpaccept to accept.", formatPlayer(sender.getDisplayName()))
                        : String.format("%s would like you to teleport to them. Type /tpaccept to accept.", formatPlayer(sender.getDisplayName()))
        );
    }

    // only allows TO direction
    public static void sendForcedTeleport(OnlineUser sender, SprouterPlayer targetPlayer, SprouterPlayer recipient) {
        new UserMessageHandler(sender).sendMessage(
                String.format("Teleported %s to %s", formatPlayer(targetPlayer.getDisplayName()), formatPlayer(recipient.getDisplayName()))
        );
        new UserMessageHandler(targetPlayer).sendMessage(
                String.format("%s teleported you to %s", formatPlayer(sender.getDisplayName()), formatPlayer(recipient.getDisplayName()))
        );
        // do immediate teleport
        targetPlayer.teleport(recipient.getLocation());
    }

    public static void sendForcedTeleport(SprouterPlayer sender, SprouterPlayer recipient, PlayerTpRequest.Direction direction) {
        new UserMessageHandler(sender).sendMessage(
                direction == PlayerTpRequest.Direction.TO
                        ? "Teleporting to " + formatPlayer(recipient.getDisplayName())
                        : String.format("Teleported %s to you.", recipient.getDisplayName())
        );
        // TO direction does not notify recipient
        if (direction == PlayerTpRequest.Direction.HERE) {
            new UserMessageHandler(recipient).sendMessage(formatPlayer(sender.getDisplayName()) + " teleported you to them.");
        }

        // do immediate teleport
        sender.teleport(recipient.getLocation());
    }

    public static void acceptTeleportRequest(SprouterPlayer sender) {
        removeTeleportRequest(sender, true);
    }

    public static void denyTeleportRequest(SprouterPlayer sender) {
        removeTeleportRequest(sender, false);
    }

    private static void doTeleport(SprouterPlayer recipient, SprouterPlayer requester, PlayerTpRequest request) {
        SprouterPlayer target = request.getDirection() == PlayerTpRequest.Direction.TO
                ? requester // to moves requester
                : recipient; // here moves recipient
        SprouterLocation destination = request.getDirection() == PlayerTpRequest.Direction.HERE
                ? request.getDestination()
                : recipient.getLocation();
        target.teleport(destination);
    }

    private static void removeTeleportRequest(SprouterPlayer sender, boolean accept) {
        String status = accept ? "Accepted" : "Denied";
        PlayerTpRequest request = GardensSprouter.getPlayerTpCache().remove(sender.getUniqueId());
        UserMessageHandler senderMessager = new UserMessageHandler(sender);
        if (request != null) {
            SprouterPlayer requester = GardensSprouter.getSprouterServer().getPlayer(request.getRequester());
            if (requester != null) {
                senderMessager.sendMessage(status + " teleport request");
                new UserMessageHandler(requester).sendMessage(String.format("%s %s your teleport request.", formatPlayer(sender.getDisplayName()), status.toLowerCase()));
                if (accept) {
                    doTeleport(sender, requester, request);
                }
            } else {
                senderMessager.sendError("Player who sent teleport request is no longer online");
            }
        } else {
            senderMessager.sendError("No pending teleport requests");
        }
    }
}
