package com.github.advancedwipe;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenDeckedOut extends JavaPlugin implements Listener {
    FileConfiguration config = null;

    private final Map<Player, Long> playerCooldowns = new HashMap<>();


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        reloadConfig();
        this.config = getConfig();

        this.getCommand("kit").setExecutor(new CustomCommand());
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
