package com.github.advancedwipe.opendeckedout.game;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerSensor {

  private final Location location;
  private final int cooldownTimer = 3; // timer in dungeon ticks
  private final Map<Player, Integer> cooldowns = new HashMap<>();
  private boolean hasCooldown;

  public PlayerSensor(Location location) {
    this.location = location;
    this.hasCooldown = false;
  }

  public Location getLocation() {
    return location;
  }

  public void activated(Player player) {
    cooldowns.put(player, cooldownTimer);
    hasCooldown = true;
  }

  public boolean hasCooldown() {
    return hasCooldown;
  }

  public void decreaseCooldown() {
    if (cooldowns.isEmpty()) {
      return;
    }

    // Decrease cooldown for each player
    for (Map.Entry<Player, Integer> entry : cooldowns.entrySet()) {
      Player key = entry.getKey();
      Integer cooldown = entry.getValue();
      if (cooldown <= 0) {
        hasCooldown = false;
        cooldowns.remove(key);
        return;
      }
      cooldowns.put(key, cooldown - 1);
    }
  }


}
