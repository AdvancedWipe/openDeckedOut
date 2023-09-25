package com.github.advancedwipe.listener;

import com.github.advancedwipe.OpenDeckedOut;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Level;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(
        Component.text("Welcome to openDeckedOut " + event.getPlayer().getName() + "!"));
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }

    if (OpenDeckedOut.getInstance().getPlayerManager().isPlayerInGame(event.getPlayer())) {
      System.out.println("Cancled");
      OpenDeckedOut.LOGGER.log(Level.DEBUG, event.getPlayer().getName() + "tried to break a block, but was canceled");
      event.setCancelled(true);
      return;
    }
  }

}
