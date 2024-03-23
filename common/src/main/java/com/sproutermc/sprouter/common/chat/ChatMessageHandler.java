package com.sproutermc.sprouter.common.chat;

import com.sproutermc.sprouter.common.database.Tables;
import com.sproutermc.sprouter.common.database.entry.MessageEntry;
import com.sproutermc.sprouter.common.user.OnlineUser;
import com.sproutermc.sprouter.common.user.SprouterUser;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class ChatMessageHandler {

    private final OnlineUser sender;
    private final SprouterUser receiver;

    // todo make these formats configurable
    private static final String RECEIVE_FORMAT = "&6[${sender} &6-> You] &6${message}";
    private static final String SEND_FORMAT = "&6[You -> ${receiver}${receiver_status}] &6${message}";
    private static final String STATUS_OFFLINE = " &7(offline)";
    private static final String STATUS_ONLINE = "";

    public void sendMessage(String message) {
        boolean isReceiverOnline = receiver instanceof OnlineUser;
        StringSubstitutor substitutor = new StringSubstitutor(Map.of(
                "sender", sender.getDisplayName(),
                "receiver", receiver.getDisplayName(),
                "receiver_status", isReceiverOnline ? STATUS_ONLINE : STATUS_OFFLINE,
                "message", message
        ));

        // send messages
        sender.sendMessage(substitutor.replace(SEND_FORMAT));
        if (isReceiverOnline) {
            ((OnlineUser) receiver).sendMessage(substitutor.replace(RECEIVE_FORMAT));
        }

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
