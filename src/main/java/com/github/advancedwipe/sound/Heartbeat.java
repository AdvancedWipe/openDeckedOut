package com.github.advancedwipe.sound;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.game.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Heartbeat {

  private final Dungeon dungeon;
  private int taskId;
  private int delay;

  public Heartbeat(Dungeon dungeon, int delay) {
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
    taskId = Bukkit.getScheduler().runTaskTimer(OpenDeckedOut.getInstance(),
        this::playSoundToAllPlayers, 0L, delay).getTaskId();
  }

  private void playSoundToAllPlayers() {
    dungeon.getPlayers().forEach(dungeonPlayer -> {
      Player player = dungeonPlayer.getPlayer();
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
