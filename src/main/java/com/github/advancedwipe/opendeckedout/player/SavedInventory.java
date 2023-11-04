package com.github.advancedwipe.opendeckedout.player;

import java.util.Collection;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class SavedInventory {
  private Component listName;
  private Component displayName;
  private ItemStack[] armor;
  private ItemStack[] inventory;
  private float xp;
  private int level = 0;
  private int foodLevel = 0;
  private Collection<PotionEffect> effects;
  private GameMode mode;
  private Object platformScoreboard;
  private Location initalLocation;


  public Component getListName() {
    return listName;
  }

  public void setListName(Component listName) {
    this.listName = listName;
  }

  public Component getDisplayName() {
    return displayName;
  }

  public void setDisplayName(Component displayName) {
    this.displayName = displayName;
  }

  public ItemStack[] getArmor() {
    return armor;
  }

  public void setArmor(ItemStack[] armor) {
    this.armor = armor;
  }

  public ItemStack[] getInventory() {
    return inventory;
  }

  public void setInventory(ItemStack[] inventory) {
    this.inventory = inventory;
  }

  public float getExp() {
    return xp;
  }

  public void setXp(float xp) {
    this.xp = xp;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public void setFoodLevel(int foodLevel) {
    this.foodLevel = foodLevel;
  }

  public Collection<PotionEffect> getPotionEffects() {
    return effects;
  }

  public void setEffects(Collection<PotionEffect> effects) {
    this.effects = effects;
  }

  public GameMode getGamemode() {
    return mode;
  }

  public void setMode(GameMode mode) {
    this.mode = mode;
  }

  public Object getPlatformScoreboard() {
    return platformScoreboard;
  }

  public void setPlatformScoreboard(Object platformScoreboard) {
    this.platformScoreboard = platformScoreboard;
  }

  public Location getInitalLocation() {
    return initalLocation;
  }

  public void setInitalLocation(Location initalLocation) {
    this.initalLocation = initalLocation;
  }
}
