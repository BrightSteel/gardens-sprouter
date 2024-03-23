package com.sproutermc.sprouter.common.user.player;

import com.sproutermc.sprouter.common.GardensSprouter;
import com.sproutermc.sprouter.common.user.UserMessageHandler;

import static com.sproutermc.sprouter.common.chat.ColorTheme.formatPlayer;

public class PlayerTpHandler {

    public static void sendTeleportRequest(SprouterPlayer sender, SprouterPlayer recipient, PlayerTpRequest.Direction direction) {
        GardensSprouter.getPlayerTpCache().put(recipient.getUniqueId(), new PlayerTpRequest(
                sender.getUniqueId(),
                direction,
                direction == PlayerTpRequest.Direction.TO ? recipient.getLocation() : sender.getLocation()
        ));

        // send messages
        new UserMessageHandler(sender).sendMessage(String.format("Sent teleport request to %s. Request will expire in 2 minutes.", formatPlayer(recipient.getDisplayName())));
        new UserMessageHandler(recipient).sendMessage(
                direction == PlayerTpRequest.Direction.TO
                        ? String.format("%s would like to teleport to you. Type /tpaccept to accept.", formatPlayer(sender.getDisplayName()))
                        : String.format("%s would like you to teleport to them. Type /tpaccept to accept.", formatPlayer(sender.getDisplayName()))
        );
    }

    // todo - support the dest with TO direction being a third player, so not sender or recipient
    public static void sendForcedTeleport(SprouterPlayer sender, SprouterPlayer recipient, PlayerTpRequest.Direction direction) {
        PlayerTpRequest request = new PlayerTpRequest(
                sender.getUniqueId(),
                direction,
                direction == PlayerTpRequest.Direction.TO ? recipient.getLocation() : sender.getLocation()
        );
        new UserMessageHandler(sender).sendMessage(
                direction == PlayerTpRequest.Direction.TO
                        ? "Teleporting to " + formatPlayer(recipient.getDisplayName())
                        : String.format("Teleported %s to you.", recipient.getDisplayName())
        );
        // TO direction does not notify recipient
        if (direction == PlayerTpRequest.Direction.HERE) {
            new UserMessageHandler(recipient).sendMessage(formatPlayer(sender.getDisplayName()) + " teleported you to them.");
        }

        doTeleport(sender, request);
    }

    public static void acceptTeleportRequest(SprouterPlayer sender) {
        PlayerTpRequest request = removeTeleportRequest(sender, true);
        if (request != null) {
            doTeleport(sender, request);
        }
    }

    public static void denyTeleportRequest(SprouterPlayer sender) {
        removeTeleportRequest(sender, false);
    }

    private static void doTeleport(SprouterPlayer requester, PlayerTpRequest request) {
        SprouterPlayer target = request.getDirection() == PlayerTpRequest.Direction.TO
                ? GardensSprouter.getSprouterServer().getPlayer(request.getRequester())
                : requester;
        if (target != null) {
            target.teleport(request.getDestination());
        }
    }

    private static PlayerTpRequest removeTeleportRequest(SprouterPlayer sender, boolean accept) {
        String status = accept ? "Accepted" : "Denied";
        PlayerTpRequest request = GardensSprouter.getPlayerTpCache().remove(sender.getUniqueId());
        UserMessageHandler senderMessager = new UserMessageHandler(sender);
        if (request != null) {
            SprouterPlayer requester = GardensSprouter.getSprouterServer().getPlayer(request.getRequester());
            if (requester != null) {
                senderMessager.sendMessage(status + " teleport request");
                new UserMessageHandler(requester).sendMessage(String.format("%s %s your teleport request.", formatPlayer(sender.getDisplayName()), status.toLowerCase()));
            } else {
                senderMessager.sendError("Player who sent teleport request is no longer online");
            }
        } else {
            senderMessager.sendError("No pending teleport requests");
        }
        return request;
    }
}
