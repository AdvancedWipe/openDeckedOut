package com.github.advancedwipe.player;

import com.github.advancedwipe.OpenDeckedOut;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.entity.Player;

public class PlayerManager {
  private final OpenDeckedOut plugin;
  private final List<DeckedOutPlayer> players = new ArrayList<>();

  public PlayerManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
  }

  public boolean isPlayerInGame(Player player) {
    return getPlayer(player).map(p -> p.getGame() != null).orElse(false);
  }



  public Optional<DeckedOutPlayer> getPlayer(Player player) {
    return players.stream()
        .filter(deckedOutPlayer -> deckedOutPlayer.getUuid().equals(player.getUniqueId()))
        .findFirst();
  }

  public DeckedOutPlayer getPlayerOrCreate(Player player) {
    return getPlayer(player).orElseGet(()-> {
      DeckedOutPlayer deckedOutPlayer = new DeckedOutPlayer(player);
      players.add(deckedOutPlayer);
      return deckedOutPlayer;
    });
  }
}
