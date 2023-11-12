package com.github.advancedwipe.opendeckedout.sound;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import com.github.advancedwipe.opendeckedout.game.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Heartbeat {

  OpenDeckedOut plugin;
  private final Dungeon dungeon;
  private int taskId;
  private int delay;

  public Heartbeat(OpenDeckedOut plugin, Dungeon dungeon, int delay) {
    this.plugin = plugin;
    this.dungeon = dungeon;
    this.delay = delay;
    scheduleSoundTask();
  }

  public void changeSoundFrequency(int newDelay) {
    Bukkit.getScheduler().cancelTask(taskId);
    delay = newDelay;
    scheduleSoundTask();
  }

  private void scheduleSoundTask() {
    taskId = Bukkit.getScheduler().runTaskTimer(plugin,
        this::playSoundToAllPlayers, 0L, delay).getTaskId();
  }

  private void playSoundToAllPlayers() {
    dungeon.getPlayers().forEach(dungeonPlayer -> {
      Player player = dungeonPlayer.getBukkitPlayer();
      Location location = player.getLocation();
      player.playSound(location, "minecraft:opendo.heartbeat60bpm", 1.0F, 1.0F);
    });
  }

  public void cancel() {
    Bukkit.getScheduler().cancelTask(taskId);
  }

  public int getDelay() {
    return delay;
  }

}
