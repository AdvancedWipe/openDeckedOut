package com.github.advancedwipe.opendeckedout.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

public class DungeonUtilsTest {

  @Test
  void getBlocksInBoundingBox_ShouldReturnCorrectLocations() {
    Location pos1 = new Location(mock(World.class), 0, 0, 0);
    Location pos2 = new Location(mock(World.class), 2, 2, 2);

    List<Location> result = DungeonUtils.getBlocksInBoundingBox(pos1, pos2);

    assertEquals(27, result.size()); // 3x3x3 grid
    assertTrue(result.contains(new Location(pos1.getWorld(), 0, 0, 0)));
    assertTrue(result.contains(new Location(pos1.getWorld(), 1, 1, 1)));
    assertTrue(result.contains(new Location(pos1.getWorld(), 2, 2, 2)));
  }
}
