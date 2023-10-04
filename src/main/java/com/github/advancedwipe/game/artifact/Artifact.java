package com.github.advancedwipe.game.artifact;

import com.github.advancedwipe.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Artifact {

  private final Location location;
  private final int level;
  private final String difficulty;

  public Artifact(Location location, int level, String difficulty) {
    this.location = location;
    this.level = level;
    this.difficulty = difficulty;
  }

  public ItemStack getCompass() {
    ItemStack compass = new ItemStack(Material.COMPASS, 1);
    var meta = compass.getItemMeta();
    meta.setDisplayName("Artifact Compass");
    compass.setItemMeta(meta);
    compass.addUnsafeEnchantment(Enchantment.MENDING , 1);
    return compass;
  }

  public Location getLocation() {
    return location;
  }

  public int getLevel() {
    return level;
  }

  public String getDifficulty() {
    return difficulty;
  }

  @Override
  public String toString() {
    return Utils.writeLocationToString(location) + ";" + level + ";" + difficulty;
  }
}
