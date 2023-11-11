package com.github.advancedwipe.opendeckedout.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

class PlayerSensorTest {

  private final double x = 10.5;
  private final double y = 20.3;
  private final double z = 30.7;
  private final float yaw = 45.0f;
  private final float pitch = 30.0f;
  private final Location location = new Location(null, x, y, z, yaw, pitch);
  private final Player player = mock(Player.class);

  @Test
  void constructor_ShouldInitializeLocationAndCooldownState() {

    PlayerSensor playerSensor = new PlayerSensor(location);

    assertEquals(location, playerSensor.getLocation());
    assertFalse(playerSensor.hasCooldown());
  }

  @Test
  void activated_ShouldSetCooldownForPlayer() {
    PlayerSensor playerSensor = new PlayerSensor(location);

    playerSensor.activated(player);

    assertTrue(playerSensor.hasCooldown());
  }

  @Test
  void decreaseCooldown_ShouldDecreaseCooldownForActivatedPlayer() {
    PlayerSensor playerSensor = new PlayerSensor(location);
    playerSensor.activated(player);

    playerSensor.decreaseCooldown();
    playerSensor.decreaseCooldown();
    playerSensor.decreaseCooldown();
    playerSensor.decreaseCooldown();

    assertFalse(playerSensor.hasCooldown());
  }
}
