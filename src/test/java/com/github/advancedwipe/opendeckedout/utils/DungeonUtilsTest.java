package com.github.advancedwipe.opendeckedout.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

class DungeonUtilsTest {

  @Test
  void isInArena_ShouldReturnTrue_WhenLocationInsideBoundingBox() {

    World world = mock(World.class);
    Location pos1 = new Location(world, 0, 0, 0);
    Location pos2 = new Location(world, 10, 10, 10);
    Location locationInside = new Location(pos1.getWorld(), 5, 5, 5);

    assertTrue(DungeonUtils.isInArena(locationInside, pos1, pos2));
  }

  @Test
  void isInArena_ShouldReturnFalse_WhenLocationOutsideBoundingBox() {
    Location pos1 = new Location(mock(World.class), 0, 0, 0);
    Location pos2 = new Location(mock(World.class), 10, 10, 10);
    Location locationOutside = new Location(pos1.getWorld(), 15, 15, 15);

    assertFalse(DungeonUtils.isInArena(locationOutside, pos1, pos2));
  }

  @Test
  void isInArena_ShouldReturnFalse_WhenDifferentWorlds() {
    Location pos1 = new Location(mock(World.class), 0, 0, 0);
    Location pos2 = new Location(mock(World.class), 10, 10, 10);
    Location location = new Location(mock(World.class, "world2"), 5, 5, 5);

    assertFalse(DungeonUtils.isInArena(location, pos1, pos2));
  }

  @Test
  void getBlocksInBoundingBox_ShouldReturnCorrectLocations() {
    Location pos1 = new Location(mock(World.class), 0, 0, 0);
    Location pos2 = new Location(mock(World.class), 2, 2, 2);

    List<Location> result = DungeonUtils.getLocationsInBoundingBox(pos1, pos2);

    assertEquals(27, result.size()); // 3x3x3 grid
    assertTrue(result.contains(new Location(pos1.getWorld(), 0, 0, 0)));
    assertTrue(result.contains(new Location(pos1.getWorld(), 1, 1, 1)));
    assertTrue(result.contains(new Location(pos1.getWorld(), 2, 2, 2)));
  }
}
