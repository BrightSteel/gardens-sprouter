package com.sproutermc.sprouter.common.world;

import lombok.Value;

@Value
public class SprouterLocation {
    double x, y, z;
    float yaw, pitch;
    String worldName;
}
