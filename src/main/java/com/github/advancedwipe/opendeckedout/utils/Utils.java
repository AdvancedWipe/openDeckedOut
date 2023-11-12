package com.github.advancedwipe.opendeckedout.utils;

import com.github.advancedwipe.opendeckedout.game.PlayerSensor;
import com.github.advancedwipe.opendeckedout.game.artifact.Artifact;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.core.util.Integers;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code Utils} class provides a set of static utility methods for common tasks.
 * These methods are designed to be reusable across different parts of the application.
 *
 * <p>Note: This class contains only static methods and is not intended to be instantiated.
 */
public class Utils {

  private Utils() {
    // private
  }

  /**
   * Converts a list of Location objects to a list of their string representations.
   *
   * @param list the list of Location objects to be converted
   * @return a List of strings representing the locations
   * @see #writeLocationToString(Location)
   */
  public static @NotNull List<String> writeLocationListToStringList(@NotNull List<Location> list) {
    List<String> stringList = new ArrayList<>();
    for (Location location : list) {
      stringList.add(writeLocationToString(location));
    }
    return stringList;
  }

  /**
   * Converts a Location object to its string representation.
   *
   * <p>This method takes a Location object and extracts its X, Y, Z coordinates, yaw, and pitch to
   * construct a string representation of the location using these values.
   *
   * @param location the Location object to be converted
   * @return a string representation of the location
   * @see #writeLocationToString(double, double, double, float, float)
   */
  public static @NotNull String writeLocationToString(@NotNull Location location) {
    return writeLocationToString(location.getX(),
        location.getY(),
        location.getZ(),
        location.getYaw(),
        location.getPitch());
  }

  /**
   * Creates a string representation of a location using its coordinates and orientation angles.
   *
   * <p>This method takes individual components of a location, including X, Y, Z coordinates,
   * yaw, and pitch, and constructs a string representation by concatenating them with semicolons.
   *
   * @param x     the X-coordinate of the location
   * @param y     the Y-coordinate of the location
   * @param z     the Z-coordinate of the location
   * @param yaw   the yaw (horizontal rotation) angle of the location
   * @param pitch the pitch (vertical rotation) angle of the location
   * @return a string representation of the location in the format "x;y;z;yaw;pitch"
   */
  @Contract(pure = true)
  public static @NotNull String writeLocationToString(double x, double y, double z, float yaw,
      float pitch) {
    return x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
  }

  /**
   * Converts a list of string representations of locations to a list of Location objects in a
   * specific world.
   *
   * <p>This method takes a List of strings, each representing a location in the format
   * "x;y;z;yaw;pitch", and converts them into a List of Location objects in the specified world. It
   * utilizes the readStringToLocation method for the conversion. If any string in the input list is
   * invalid or does not conform to the expected format, the corresponding Location object in the
   * result list will be null.
   *
   * @param world the World in which the new Location objects will be created
   * @param list  the list of string representations of locations to be converted
   * @return a List of Location objects representing the specified coordinates and orientations,
   *         with null entries for invalid strings in the input list
   * @see #readStringToLocation(World, String)
   */
  public static @NotNull List<Location> readStringListToLocationList(World world,
      @NotNull List<String> list) {
    List<Location> locationList = new ArrayList<>();
    for (String string : list) {
      locationList.add(readStringToLocation(world, string));
    }
    return locationList;
  }

  /**
   * Converts a string representation of a location to a Location object in a specific world.
   *
   * <p>This method takes a string representation of a location, where the components are separated
   * by
   * semicolons, and converts it into a Location object in the specified world. The expected format
   * of the string is "x;y;z;yaw;pitch". If the string contains exactly five components,
   * representing X, Y, Z coordinates, yaw, and pitch, respectively, a new Location object is
   * created and returned. Otherwise, null is returned.
   *
   * @param world    the World in which the new Location object will be created
   * @param location the string representation of the location to be converted
   * @return a Location object representing the specified coordinates and orientation, or null if
   *         the format is invalid
   */
  public static @Nullable Location readStringToLocation(World world, @NotNull String location) {
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

  /**
   * Converts a Location object to a string representation of its X, Y, and Z coordinates.
   *
   * <p>This method takes a Location object and extracts its X, Y, and Z coordinates, then
   * constructs a
   * string representation by concatenating them with commas.
   *
   * @param location the Location object to be converted
   * @return a string representation of the X, Y, and Z coordinates of the location in the format
   *         "x, y, z"
   */
  public static @NotNull String locationToXyz(@NotNull Location location) {
    return location.getX() + ", " + location.getY() + ", " + location.getZ();
  }


  /**
   * Converts a list of PlayerSensor objects to a list of string representations of their
   * locations.
   *
   * <p>This method takes a List of PlayerSensor objects, extracts their locations, and then
   * utilizes
   * the writeLocationListToStringList method to convert the list of locations to a List of string
   * representations.
   *
   * @param sensors the list of PlayerSensor objects to be converted
   * @return a List of string representations of the locations of the PlayerSensors
   * @see #writeLocationListToStringList(List)
   */
  public static @NotNull List<String> writeSensorListToString(@NotNull List<PlayerSensor> sensors) {
    List<Location> locations = new ArrayList<>();
    sensors.forEach(sensor -> locations.add(sensor.getLocation()));

    return writeLocationListToStringList(locations);
  }

  /**
   * Converts a list of string representations of locations to a list of PlayerSensor objects in a
   * specific world.
   *
   * <p>This method takes a List of strings, each representing a location in the format
   * "x;y;z;yaw;pitch", and converts them into a List of PlayerSensor objects in the specified
   * world. It utilizes the readStringListToLocationList method to convert the list of strings to a
   * list of locations, and then creates a new PlayerSensor object for each location.
   *
   * @param world the World in which the new PlayerSensor objects will be created
   * @param list  the list of string representations of locations to be converted
   * @return a List of PlayerSensor objects representing sensors located at the specified
   *         coordinates
   * @see #readStringListToLocationList(World, List)
   * @see PlayerSensor
   */
  public static @NotNull List<PlayerSensor> readStringToSensorList(World world, List<String> list) {
    List<PlayerSensor> sensors = new ArrayList<>();
    List<Location> locations = readStringListToLocationList(world, list);

    for (Location location : locations) {
      sensors.add(new PlayerSensor(location));
    }

    return sensors;
  }

  /**
   * Converts a list of Artifact objects to a list of their string representations.
   *
   * <p>This method takes a List of Artifact objects and utilizes their toString method to convert
   * each
   * artifact to its string representation. The resulting strings are then added to a new List,
   * which is returned.
   *
   * @param artifacts the list of Artifact objects to be converted
   * @return a List of strings representing the artifacts
   * @see Artifact#toString()
   */
  public static @NotNull List<String> writeArtifactListToString(@NotNull List<Artifact> artifacts) {
    List<String> artifactsAsStrings = new ArrayList<>();
    artifacts.forEach(artifact -> artifactsAsStrings.add(artifact.toString()));
    return artifactsAsStrings;
  }

  /**
   * Converts a list of string representations of artifacts to a list of Artifact objects in a
   * specific world.
   *
   * <p>This method takes a List of strings, each representing an artifact in the format
   * "x;y;z;yaw;pitch;level;difficulty", and converts them into a List of Artifact objects in the
   * specified world. It utilizes the readStringToLocation method to convert the location part of
   * each string and extracts the level and difficulty values for constructing the Artifact objects.
   * If any string in the input list is invalid or does not conform to the expected format, the
   * corresponding Artifact object in the result list will not be created.
   *
   * @param world the World in which the new Artifact objects will be created
   * @param list  the list of string representations of artifacts to be converted
   * @return a List of Artifact objects representing artifacts located at the specified coordinates,
   *         with associated level and difficulty
   * @see #readStringToLocation(World, String)
   * @see Artifact
   */
  public static @NotNull List<Artifact> readStringListToArtifactList(World world,
      @NotNull List<String> list) {
    List<Artifact> artifacts = new ArrayList<>();
    list.forEach(artifact -> {
      int endOfLocation = findNthSemicolonIndex(artifact, 5);
      String location = artifact.substring(0, endOfLocation);
      var artifactVariables = artifact.split(";");

      artifacts.add(new Artifact(readStringToLocation(world, location),
          Integers.parseInt(artifactVariables[5]), artifactVariables[6]));
    });

    return artifacts;
  }

  private static int findNthSemicolonIndex(String str, int n) {
    // Base case: If n is 0 or the string is empty, return -1 (not found)
    if (n <= 0 || str.isEmpty()) {
      return -1;
    }

    // Find the index of the first semicolon in the remaining substring
    int semicolonIndex = str.indexOf(';');

    // If a semicolon was found, recursively search for the (n-1)th semicolon
    // in the remaining substring
    if (semicolonIndex != -1) {
      return semicolonIndex + findNthSemicolonIndex(str.substring(semicolonIndex + 1), n - 1) + 1;
    }

    // If no semicolon was found, return -1 (not found)
    return -1;
  }
}
