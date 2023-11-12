package com.github.advancedwipe.opendeckedout.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class DungeonUtils {

  private DungeonUtils() {
    // private
  }

  public static boolean isInArena(Location location, Location pos1, Location pos2) {
    if (!pos1.getWorld().equals(
        pos2.getWorld())) { // Needs to be the same world, might be redundant due to it being checked on setting pos1 and pos2
      return false;
    }

    if (!location.getWorld().equals(pos1.getWorld())) { // Location not in same world as pos1
      return false;
    }

    double minX = Math.min(pos1.getX(), pos2.getX());
    double maxX = Math.max(pos1.getX(), pos2.getX());
    double minY = Math.min(pos1.getY(), pos2.getY());
    double maxY = Math.max(pos1.getY(), pos2.getY());
    double minZ = Math.min(pos1.getZ(), pos2.getZ());
    double maxZ = Math.max(pos1.getZ(), pos2.getZ());

    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();

    return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
  }

  public static List<Location> getBlocksInBoundingBox(Location pos1, Location pos2) {
    List<Location> locationsInBoundingBox = new ArrayList<>();
    var world = pos1.getWorld();
    int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
    int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
    int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

    int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
    int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
    int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          locationsInBoundingBox.add(new Location(world, x, y, z));
        }
      }
    }

    return locationsInBoundingBox;
  }


}
