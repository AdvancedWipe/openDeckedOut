package com.github.advancedwipe.opendeckedout.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.advancedwipe.opendeckedout.game.PlayerSensor;
import com.github.advancedwipe.opendeckedout.game.artifact.Artifact;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilsTest {

  private final double x1 = 10.5;
  private final double y1 = 20.3;
  private final double z1 = 30.7;
  private final float yaw1 = 45.0f;
  private final float pitch1 = 30.0f;
  private final Location location1 = new Location(null, x1, y1, z1, yaw1, pitch1);
  private final double x2 = 5.0;
  private final double y2 = 15.2;
  private final double z2 = 25.6;
  private final float yaw2 = 90.0f;
  private final float pitch2 = 60.0f;
  private final Location location2 = new Location(null, x2, y2, z2, yaw2, pitch2);
  private final World world = mock(World.class);
  private final String locationString1 = x1 + ";" + y1 + ";" + z1 + ";" + yaw1 + ";" + pitch1;
  private final String locationString2 = x2 + ";" + y2 + ";" + z2 + ";" + yaw2 + ";" + pitch2;

  private @NotNull PlayerSensor createMockSensor(double x, double y, double z) {
    PlayerSensor mockSensor = mock(PlayerSensor.class);
    Location mockLocation = new Location(world, x, y, z);
    when(mockSensor.getLocation()).thenReturn(mockLocation);
    return mockSensor;
  }


  @Test
  void writeLocationToString_ShouldReturnExpectedString_WithValues() {
    String result = Utils.writeLocationToString(x1, y1, z1, yaw1, pitch1);

    // Assert
    String expected = x1 + ";" + y1 + ";" + z1 + ";" + yaw1 + ";" + pitch1;
    assertEquals(expected, result);
  }

  @Test
  void writeLocationToString_ShouldReturnExpectedString_WithLocation() {
    Location location = new Location(null, x1, y1, z1, yaw1, pitch1);

    String result = Utils.writeLocationToString(location);

    String expected = x1 + ";" + y1 + ";" + z1 + ";" + yaw1 + ";" + pitch1;
    assertEquals(expected, result);
  }

  @Test
  void writeLocationListToStringList_ShouldReturnExpectedList() {
    List<Location> locationList = new ArrayList<>();
    locationList.add(location1);
    locationList.add(location2);

    List<String> result = Utils.writeLocationListToStringList(locationList);

    List<String> expected = new ArrayList<>();
    expected.add(x1 + ";" + y1 + ";" + z1 + ";" + yaw1 + ";" + pitch1);
    expected.add(x2 + ";" + y2 + ";" + z2 + ";" + yaw2 + ";" + pitch2);

    assertEquals(expected, result);
  }

  @Test
  void readStringToLocation_ShouldReturnLocation_WithValidInput() {
    Location result = Utils.readStringToLocation(world, locationString1);

    assertNotNull(result);
    assertEquals(10.5, result.getX());
    assertEquals(20.3, result.getY());
    assertEquals(30.7, result.getZ());
    assertEquals(45.0f, result.getYaw());
    assertEquals(30.0f, result.getPitch());
  }

  @Test
  void readStringToLocation_ShouldReturnNull_WithInvalidInput() {
    String invalidLocationString = "invalid_format";

    Location result = Utils.readStringToLocation(world, invalidLocationString);

    assertNull(result);
  }


  @Test
  void readStringListToLocationList_ShouldReturnLocationList_WithValidInputs() {
    List<String> stringList = List.of(locationString1, locationString2);

    List<Location> result = Utils.readStringListToLocationList(world, stringList);

    assertNotNull(result);
    assertEquals(2, result.size());

    Location location1 = result.get(0);
    assertEquals(x1, location1.getX());
    assertEquals(y1, location1.getY());
    assertEquals(z1, location1.getZ());
    assertEquals(yaw1, location1.getYaw());
    assertEquals(pitch1, location1.getPitch());

    Location location2 = result.get(1);
    assertEquals(x2, location2.getX());
    assertEquals(y2, location2.getY());
    assertEquals(z2, location2.getZ());
    assertEquals(yaw2, location2.getYaw());
    assertEquals(pitch2, location2.getPitch());
  }

  @Test
  void readStringListToLocationList_ShouldReturnEmptyList_WithEmptyInput() {
    List<String> emptyList = new ArrayList<>();

    List<Location> result = Utils.readStringListToLocationList(world, emptyList);

    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  void readStringListToLocationList_ShouldReturnNullLocations_WithInvalidInputs() {
    World world = mock(World.class);
    List<String> invalidList = List.of("invalid_format", "another_invalid_format");

    List<Location> result = Utils.readStringListToLocationList(world, invalidList);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertNull(result.get(0));
    assertNull(result.get(1));
  }

  @Test
  void locationToXYZ_ShouldReturnExpectedString_WithValidInput() {

    String result = Utils.locationToXyz(location1);

    assertEquals(x1 + ", " + y1 + ", " + z1, result);
  }

  @Test
  void locationToXYZ_ShouldReturnNull_WithNullInput() {
    Assertions.assertThrows(NullPointerException.class, () -> Utils.locationToXyz(null));
  }

  @Test
  void writeSensorListToString_ShouldReturnEmptyList_WithEmptyInput() {
    List<PlayerSensor> emptySensorList = new ArrayList<>();

    List<String> result = Utils.writeSensorListToString(emptySensorList);

    assertTrue(result.isEmpty());
  }

  @Test
  void writeSensorListToString_ShouldReturnExpectedList_WithValidInput() {
    List<PlayerSensor> validSensorList = new ArrayList<>();
    validSensorList.add(createMockSensor(x1, y1, z1));
    validSensorList.add(createMockSensor(x2, y2, z2));

    List<String> result = Utils.writeSensorListToString(validSensorList);

    assertEquals(2, result.size());
    assertEquals(x1 + ";" + y1 + ";" + z1 + ";0.0;0.0", result.get(0));
    assertEquals(x2 + ";" + y2 + ";" + z2 + ";0.0;0.0", result.get(1));
  }

  @Test
  void writeSensorListToString_ShouldReturnEmptyList_WithNullInput() {
    Assertions.assertThrows(NullPointerException.class, () -> Utils.writeSensorListToString(null));
  }

  @Test
  void readStringToSensorList_ShouldReturnEmptyList_WithEmptyInput() {
    List<String> emptyList = new ArrayList<>();

    List<PlayerSensor> result = Utils.readStringToSensorList(world, emptyList);

    assertTrue(result.isEmpty());
  }

  @Test
  void readStringToSensorList_ShouldReturnExpectedList_WithValidInput() {
    List<String> validStringList = List.of(x1 + ";" + y1 + ";" + z1 + ";0.0;0.0",
        x2 + ";" + y2 + ";" + z2 + ";0.0;0.0");

    List<PlayerSensor> result = Utils.readStringToSensorList(world, validStringList);

    assertEquals(2, result.size());
    assertEquals(new Location(world, x1, y1, z1), result.get(0).getLocation());
    assertEquals(new Location(world, x2, y2, z2), result.get(1).getLocation());
  }

  @Test
  void writeArtifactListToString_ShouldReturnEmptyList_WithEmptyInput() {
    List<Artifact> emptyArtifactList = new ArrayList<>();

    List<String> result = Utils.writeArtifactListToString(emptyArtifactList);

    assertTrue(result.isEmpty());
  }

}
