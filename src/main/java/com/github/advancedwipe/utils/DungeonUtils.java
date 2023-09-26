package com.github.advancedwipe.utils;

import org.bukkit.Location;

public class DungeonUtils {

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


}
