package com.sproutermc.sprouter.common.listener.type;

import com.sproutermc.sprouter.common.user.player.SprouterPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PlayerListener implements Listener {
    protected final SprouterPlayer sprouterPlayer;
}
