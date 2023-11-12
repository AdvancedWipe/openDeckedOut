package com.github.advancedwipe.opendeckedout.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code DungeonUtils} class provides a set of static utility methods for common dungeon tasks.
 * These methods are designed to be reusable across different parts of the application.
 *
 * <p>Note: This class contains only static methods and is not intended to be instantiated.
 */
public class DungeonUtils {

  private DungeonUtils() {
    // private
  }

  /**
   * Checks if a given location is within the boundaries of a rectangular arena defined by two
   * corner locations.
   *
   * <p>The method determines if the provided location is within the region specified by the two
   * corner locations (pos1 and pos2) in the same world. The corners are defined by the minimum and
   * maximum coordinates in each dimension. If the provided location is within the boundaries, the
   * method returns true; otherwise, it returns false.
   *
   * @param location the Location to check for within the arena
   * @param pos1     the first corner Location of the arena
   * @param pos2     the second corner Location of the arena
   * @return true if the location is within the arena boundaries, false otherwise
   * @throws IllegalArgumentException if either pos1 or pos2 is null
   * @throws IllegalArgumentException if pos1 and pos2 are not in the same world
   * @see Location
   */
  public static boolean isInArena(Location location, @NotNull Location pos1,
      @NotNull Location pos2) {
    // Needs to be the same world, might be redundant due to it being
    // checked on setting pos1 and pos2
    if (!pos1.getWorld().equals(pos2.getWorld())) {
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

  /**
   * Generates a list of block Locations within the bounding box defined by two corner Locations.
   *
   * <p>The method creates a list of block Locations within the three-dimensional bounding box
   * defined by the two corner Locations (pos1 and pos2). The corners are defined by the minimum and
   * maximum block coordinates in each dimension. The resulting list includes all block Locations
   * within the specified range, iterating over each possible block coordinate combination.
   *
   * @param pos1 the first corner Location of the bounding box
   * @param pos2 the second corner Location of the bounding box
   * @return a List of block Locations within the bounding box
   * @throws IllegalArgumentException if either pos1 or pos2 is null
   * @see Location
   */
  public static @NotNull List<Location> getLocationsInBoundingBox(@NotNull Location pos1,
      @NotNull Location pos2) {
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
