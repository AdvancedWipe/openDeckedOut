package com.github.advancedwipe.player;

import com.github.advancedwipe.cards.Card;
import com.github.advancedwipe.cards.CardManager;
import com.github.advancedwipe.game.Dungeon;
import com.github.advancedwipe.game.Game;
import com.github.advancedwipe.game.Money;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

public class DungeonPlayer implements Player {

  private final org.bukkit.entity.Player player;
  private final UUID uuid;
  private Dungeon game;
  private SavedInventory savedInventory = new SavedInventory();

  public DungeonPlayer(org.bukkit.entity.Player player) {
    this.player = player;
    this.uuid = player.getUniqueId();
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  public org.bukkit.entity.Player getPlayer() {
    return player;
  }

  @Override
  public boolean isSpectator() {
    return false;
  }

  @Override
  public boolean isInGame() {
    return game != null;
  }

  @Override
  public Map<Money, Integer> getWallet() {
    return null;
  }

  public @Nullable Dungeon getDungeon() {
    return game;
  }

  public void changeGame(Dungeon game) {
    if (this.game == null && game != null) { // On join
      // TODO Maybe save inventory before joining to restore it after game
      this.game = game;
      this.game.internalJoinPlayer(this);
    } else if (this.game != null && game == null) { // On leave
      this.game.internalLeavePlayer(this);
      this.game = null;
      // TODO clean up values or restore inventory
    }
  }

  public void saveInventory() {
    savedInventory.setInventory(player.getInventory().getContents());
    savedInventory.setArmor(player.getInventory().getArmorContents());
    savedInventory.setXp(player.getExp());
    savedInventory.setEffects(player.getActivePotionEffects());
    savedInventory.setMode(player.getGameMode());
    savedInventory.setLevel(player.getLevel());
    savedInventory.setFoodLevel(player.getFoodLevel());
    savedInventory.setPlatformScoreboard(player.getScoreboard().getObjectives());

    // Is needed to teleport player back to the location where they entered the join command
    savedInventory.setInitalLocation(player.getLocation());

    // Finally clear the players current inventory
    player.getInventory().clear();
  }

  public void restoreInventory() {
    var currentInventory = player.getInventory();
    currentInventory.clear();

    player.getInventory().setContents(savedInventory.getInventory());
    player.getInventory().setArmorContents(savedInventory.getArmor());
    player.setFoodLevel(savedInventory.getFoodLevel());
    player.setLevel(savedInventory.getLevel());
    player.setExp(savedInventory.getExp());
    player.setGameMode(savedInventory.getGamemode());

    for (PotionEffect effect : player.getActivePotionEffects()) {
      player.removePotionEffect(effect.getType());
    }
    player.addPotionEffects(savedInventory.getPotionEffects());

    player.teleport(savedInventory.getInitalLocation());
  }
}
