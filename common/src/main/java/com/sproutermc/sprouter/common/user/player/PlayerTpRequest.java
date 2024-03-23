package com.sproutermc.sprouter.common.user.player;

import com.sproutermc.sprouter.common.world.SprouterLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerTpRequest {
    private UUID requester;
    private Direction direction;
    private SprouterLocation destination;

    public enum Direction {
        TO, HERE
    }
}
