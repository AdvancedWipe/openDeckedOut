package com.github.advancedwipe.opendeckedout.utils;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class PlayerUtils {

  private PlayerUtils() {
    // private
  }

  public static boolean hasPlayerMoved(@NotNull Location from, @NotNull Location to) {
    double fromX = from.getX();
    double fromY = from.getY();
    double fromZ = from.getZ();

    double toX = to.getX();
    double toY = to.getY();
    double toZ = to.getZ();

    return fromX != toX || fromY != toY || fromZ != toZ;
  }

}
