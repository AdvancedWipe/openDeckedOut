package com.github.advancedwipe.opendeckedout.player;

import com.github.advancedwipe.opendeckedout.game.Money;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.bukkit.potion.PotionEffect;

public abstract class Player {

  private final org.bukkit.entity.Player bukkitPlayer;
  private final SavedInventory savedInventory = new SavedInventory();

  protected Player(org.bukkit.entity.Player player) {
    this.bukkitPlayer = player;
  }

  public void saveInventory() {
    savedInventory.setInventory(bukkitPlayer.getInventory().getContents());
    savedInventory.setArmor(bukkitPlayer.getInventory().getArmorContents());
    savedInventory.setXp(bukkitPlayer.getExp());
    savedInventory.setEffects(bukkitPlayer.getActivePotionEffects());
    savedInventory.setMode(bukkitPlayer.getGameMode());
    savedInventory.setLevel(bukkitPlayer.getLevel());
    savedInventory.setFoodLevel(bukkitPlayer.getFoodLevel());
    savedInventory.setPlatformScoreboard(bukkitPlayer.getScoreboard().getObjectives());

    // Is needed to teleport player back to the location where they entered the join command
    savedInventory.setInitalLocation(bukkitPlayer.getLocation());

    // Finally clear the players current inventory
    bukkitPlayer.getInventory().clear();
  }

  public void restoreInventory() {
    var currentInventory = bukkitPlayer.getInventory();
    currentInventory.clear();

    bukkitPlayer.getInventory().setContents(savedInventory.getInventory());
    bukkitPlayer.getInventory().setArmorContents(savedInventory.getArmor());
    bukkitPlayer.setFoodLevel(savedInventory.getFoodLevel());
    bukkitPlayer.setLevel(savedInventory.getLevel());
    bukkitPlayer.setExp(savedInventory.getExp());
    bukkitPlayer.setGameMode(savedInventory.getGamemode());

    for (PotionEffect effect : bukkitPlayer.getActivePotionEffects()) {
      bukkitPlayer.removePotionEffect(effect.getType());
    }
    bukkitPlayer.addPotionEffects(savedInventory.getPotionEffects());

    bukkitPlayer.teleport(savedInventory.getInitalLocation());
  }

  public org.bukkit.entity.Player getBukkitPlayer() {
    return this.bukkitPlayer;
  }

  public UUID getUuid() {
    return bukkitPlayer.getUniqueId();
  }

  public boolean isSpectator() {
    return false;
  }

  public boolean isInGame() {
    return false;
  }

  public Map<Money, Integer> getWallet() {
   return Collections.emptyMap();
  }
}
