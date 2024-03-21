package com.gardensmc.sprouter.spigot.world;

import com.sproutermc.sprouter.common.world.SprouterLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class SpigotLocationAdapter {

    public static Location toSpigotLocation(SprouterLocation sprouterLocation) {
        return new Location(
                Bukkit.getWorld(sprouterLocation.getWorldName()),
                sprouterLocation.getX(),
                sprouterLocation.getY(),
                sprouterLocation.getZ(),
                sprouterLocation.getYaw(),
                sprouterLocation.getPitch()
        );
    }

    public static SprouterLocation fromSpigotLocation(Location location) {
        return new SprouterLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                Objects.requireNonNull(location.getWorld()).getName()
        );
    }
}
