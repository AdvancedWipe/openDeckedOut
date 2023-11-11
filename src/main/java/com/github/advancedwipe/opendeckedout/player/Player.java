package com.github.advancedwipe.opendeckedout.player;

import com.github.advancedwipe.opendeckedout.game.Money;
import java.util.Map;
import java.util.UUID;
import org.bukkit.potion.PotionEffect;

public abstract class Player {

  private final org.bukkit.entity.Player player;
  private final SavedInventory savedInventory = new SavedInventory();

  protected Player(org.bukkit.entity.Player player) {
    this.player = player;
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

  public org.bukkit.entity.Player getPlayer() {
    return this.player;
  }

  public UUID getUuid() {
    return player.getUniqueId();
  }

  public boolean isSpectator() {
    return false;
  }

  public boolean isInGame() {
    return false;
  }

  public Map<Money, Integer> getWallet() {
   return null;
  }
}
