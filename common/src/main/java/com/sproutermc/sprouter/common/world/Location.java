package com.sproutermc.sprouter.common.world;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double x, y, z;
    private float yaw, pitch;
    private String worldName;
}
