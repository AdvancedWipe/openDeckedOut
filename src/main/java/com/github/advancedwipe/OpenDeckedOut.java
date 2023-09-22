package com.github.advancedwipe;

import com.github.advancedwipe.commands.MainCommand;
import com.github.advancedwipe.commands.MainCommandCompleter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OpenDeckedOut extends JavaPlugin implements Listener {

  private static final Logger LOGGER = LogManager.getLogger(OpenDeckedOut.class.getSimpleName());
  private final Map<Player, Long> playerCooldowns = new HashMap<>();
  FileConfiguration config = null;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

    if (!loadConfig()) {
      LOGGER.log(Level.WARN, "Could not load config file! Disabling plugin.");
      this.setEnabled(false);
      return;
    }

    this.loadCommands();
  }

  private void loadCommands() {
    this.getCommand("opendeckedout").setExecutor(new MainCommand());
    this.getCommand("opendeckedout").setTabCompleter(new MainCommandCompleter());
  }

  public boolean loadConfig() {
    LOGGER.log(Level.INFO, "Loading configuration...");
    saveDefaultConfig();
    reloadConfig();
    this.config = getConfig();
    return true;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
  }

  @EventHandler
  public void onRedstoneChange(BlockRedstoneEvent event) {
    // Check if the changed block is a Shulker Box (Shulker)
    Block block = event.getBlock();
    if (block.getType() == Material.SCULK_SENSOR) {
      Location location = block.getLocation();
      int radius = 10;

      List<Player> nearbyPlayers = getPlayersInRadius(location, radius);
      // Send a message to each nearby player
      for (Player player : nearbyPlayers) {
        if (isCooldownExpired(player)) {
          System.out.println(config.getInt("check_interval_ticks"));
          player.sendMessage("You are near a Shulker Box!");
          setCooldown(player);
        }
      }
    }
  }

  private List<Player> getPlayersInRadius(Location location, int radius) {
    List<Player> nearbyPlayers = new ArrayList<>();

    for (Player player : Bukkit.getOnlinePlayers()) {
      Location playerLocation = player.getLocation();
      if (playerLocation.getWorld().equals(location.getWorld()) &&
          playerLocation.distanceSquared(location) <= radius * radius) {
        nearbyPlayers.add(player);
      }
    }

    return nearbyPlayers;
  }

  private boolean isCooldownExpired(Player player) {
    if (!playerCooldowns.containsKey(player)) {
      return true;
    }
    long lastMessageTime = playerCooldowns.get(player);
    long currentTime = System.currentTimeMillis();
    return currentTime - lastMessageTime >= this.config.getInt("cooldownTime");
  }

  private void setCooldown(Player player) {
    playerCooldowns.put(player, System.currentTimeMillis());
  }
}
