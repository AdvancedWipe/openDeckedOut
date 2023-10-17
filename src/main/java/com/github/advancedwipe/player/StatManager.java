package com.github.advancedwipe.player;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

public class StatManager {

  private final OpenDeckedOut plugin;
  private final List<PlayerStats> stats;
  private Map<String, Object> data;

  public StatManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.stats = new ArrayList<>();
    this.data = new HashMap<>();
    loadPlayerStats();
  }

  public void loadPlayerStats() {
    try (FileReader fileReader = new FileReader(
        OpenDeckedOut.getInstance().getDataFolder() + "/player_stats.yml")) {
      data = new Yaml().load(fileReader);
    } catch (IOException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
    }
  }

  public void savePlayerStats() {
    try (FileWriter fileWriter = new FileWriter(
        OpenDeckedOut.getInstance().getDataFolder() + "/player_stats.yml")) {
      new Yaml().dump(data, fileWriter);
    } catch (IOException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
    }
  }

  public void updatePlayerStats(PlayerStats stats) {
    // Update the statistics
    stats.setWins(stats.getWins() + 1);
    stats.setGamesPlayed(stats.getGamesPlayed() + 1);

    // Save the updated statistics to the YAML file
    savePlayerStats();
  }

  public Optional<PlayerStats> getStat(Player player) {
    return stats.stream().filter(playerStats -> playerStats.getUuid().equals(player.getUniqueId()))
        .findFirst();
  }

  public PlayerStats getOrCreatePlayerStat(Player player) {
    return getStat(player).orElseGet(() -> {
      PlayerStats playerStat = new PlayerStats(player);
      stats.add(playerStat);
      return playerStat;
    });
  }

}
