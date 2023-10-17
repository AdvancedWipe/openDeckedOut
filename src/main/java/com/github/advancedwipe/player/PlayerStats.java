package com.github.advancedwipe.player;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

public class PlayerStats {

  private UUID uuid;
  private int gamesPlayed;
  private int wins;
  private int losses;

  public PlayerStats(Player player) {
    this.uuid = player.getUniqueId();
  }

  public int getGamesPlayed() {
    return gamesPlayed;
  }

  public void setGamesPlayed(int gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public int getLosses() {
    return losses;
  }

  public void setLosses(int losses) {
    this.losses = losses;
  }

  public UUID getUuid() {
    return this.uuid;
  }
}
