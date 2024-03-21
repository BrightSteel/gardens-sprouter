package com.sproutermc.sprouter.common.chat;

import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.MessageEntry;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterOfflinePlayer;
import com.sproutermc.sprouter.common.user.SprouterPlayer;
import com.sproutermc.sprouter.common.user.SprouterUser;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class ChatMessageHandler {

    private final OnlineUser sender;
    private final SprouterUser receiver;

    public void sendMessage(String message) {
        // send messages
        boolean isReceiverOnline;
        if (receiver instanceof OnlineUser onlineReceiver) {
            onlineReceiver.sendMessage(String.format(
                    "&6[%s &6-> You] &6%s",
                    sender.getDisplayName(),
                    message
            ));
            isReceiverOnline = true;
        } else {
            isReceiverOnline = false;
        }
        sender.sendMessage(String.format(
                "&6[You -> %s %s&6] &6%s",
                receiver.getDisplayName(),
                !isReceiverOnline ? "&7(offline)" : "",
                message
        ));

        // store message in table
        CompletableFuture.supplyAsync(() -> Tables.messageTable.createEntry(
                new MessageEntry()
                        .setSenderUUID(sender.getUuid())
                        .setRecipientUUID(receiver.getUuid())
                        .setRead(isReceiverOnline)
                        .setContent(message)
                        .setTimeStamp(LocalDateTime.now(Clock.systemUTC()))
        ));
    }
}
