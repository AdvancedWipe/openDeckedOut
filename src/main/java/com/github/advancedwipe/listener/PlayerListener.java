package com.github.advancedwipe.listener;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.game.Dungeon;
import com.github.advancedwipe.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Level;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendMessage(
        Component.text("Welcome to openDeckedOut " + event.getPlayer().getName() + "!"));
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }

    if (OpenDeckedOut.getInstance().getPlayerManager().isPlayerInGame(event.getPlayer())) {
      OpenDeckedOut.LOGGER.log(Level.DEBUG,
          event.getPlayer().getName() + "tried to break a block, but was canceled");
      event.setCancelled(true);
      return;
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();

    var dungeonPlayer = OpenDeckedOut.getInstance().getPlayerManager().getPlayer(player)
        .orElse(null);

    if (dungeonPlayer == null) {
      return;
    }

    Dungeon dungeon = dungeonPlayer.getDungeon();

    if (dungeon == null) {
      return;
    }

    var sensors = dungeon.getSensors();

    if (sensors == null) {
      return;
    }

    if (!PlayerUtils.hasPlayerMoved(event.getFrom(), event.getTo())) {
      // Player has not changed the location they just looked around
      return;
    }

    sensors.forEach(sensor -> {
      if (isPlayerInsideCircle(player, sensor.getLocation())) {
        if (player.isSneaking()) {
          return;
        }
        dungeon.activatedSensor(player, sensor.getLocation());
      }
    });
  }

  private boolean isPlayerInsideCircle(Player player, Location center) {
    double circleRadius = 7.0;
    Location playerLocation = player.getLocation();
    double distance = playerLocation.distance(center);
    return distance <= circleRadius;
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    var playerManager = OpenDeckedOut.getInstance().getPlayerManager();

    if (playerManager.isPlayerInGame(player)) {
      playerManager.getPlayerOrCreate(player).changeGame(null);
    }
  }

}
