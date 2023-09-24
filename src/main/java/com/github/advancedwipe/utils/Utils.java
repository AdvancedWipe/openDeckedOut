package com.github.advancedwipe.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Utils {

  public static String writeLocationToString(Location location) {
    return writeLocationToString(location.getX(),
        location.getY(),
        location.getZ(),
        location.getYaw(),
        location.getPitch());
  }

  public static String writeLocationToString(double x, double y, double z, float yaw, float pitch) {
    return x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
  }

  /**
   * Parse a String to a Location
   * @param world The world in which the location is
   * @param location The location as String which should be parsed
   * @return A new Location or return null if split String is not of length 5
   */
  public static Location readStringToLocation(World world, String location) {
    String[] split = location.split(";");
    double x;
    double y;
    double z;
    float yaw;
    float pitch;

    if (split.length == 5) {
      x = Double.parseDouble(split[0]);
      y = Double.parseDouble(split[1]);
      z = Double.parseDouble(split[2]);
      yaw = Float.parseFloat(split[3]);
      pitch = Float.parseFloat(split[4]);
      return new Location(world, x, y, z, yaw, pitch);
    } else {
      return null;
    }
  }

}
