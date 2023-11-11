package com.github.advancedwipe.opendeckedout.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.advancedwipe.opendeckedout.game.artifact.Artifact;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

class ArtifactTest {

  private final double x = 10.5;
  private final double y = 20.3;
  private final double z = 30.7;
  private final float yaw = 45.0f;
  private final float pitch = 30.0f;
  private final Location location = new Location(null, x, y, z, yaw, pitch);
  private final String locationString = x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;

  @Test
  void toString_ShouldReturnExpectedString_WithValidInput() {
    Artifact artifact = new Artifact(location, 2, "Medium");

    String result = artifact.toString();

    assertEquals(locationString + ";2;Medium", result);
  }

}
