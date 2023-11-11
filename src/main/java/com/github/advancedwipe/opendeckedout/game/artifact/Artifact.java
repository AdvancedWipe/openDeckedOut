package com.github.advancedwipe.opendeckedout.game.artifact;

import com.github.advancedwipe.opendeckedout.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * The Artifact class represents an item of interest or value that can be found
 * at a specific location.
 * It encapsulates information about the artifact's location, level of rarity, and the difficulty
 * associated with obtaining it.
 */
public class Artifact {

  private final Location location;
  private final int level;
  private final String difficulty;

  /**
   * Constructs an Artifact with the specified parameters.
   *
   * @param location   the location where the artifact is found
   * @param level      the level or rarity of the artifact
   * @param difficulty the difficulty associated with obtaining the artifact
   */
  public Artifact(Location location, int level, String difficulty) {
    this.location = location;
    this.level = level;
    this.difficulty = difficulty;
  }

  /**
   * Get the compass that points to the artifact location.
   */
  public ItemStack getCompass() {
    ItemStack compass = new ItemStack(Material.COMPASS, 1);
    var meta = compass.getItemMeta();
    meta.displayName(Component.text("Artifact Compass"));
    compass.setItemMeta(meta);
    compass.addUnsafeEnchantment(Enchantment.MENDING, 1);
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
