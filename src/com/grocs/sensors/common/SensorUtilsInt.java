package com.grocs.sensors.common;

import java.util.Collection;

import android.hardware.Sensor;

public class SensorUtilsInt {

  /**
   */
  public static SensorData retrieveSensor(Collection<SensorData> sensors,
      Sensor sensor) {
    for (SensorData sd : sensors) {
      if (sd.getSensor().equals(sensor)) {
        return sd;
      }
    }
    return null;
  }

  /**
   */
  public static SensorData retrieveSensor(SensorData[] sensors, String name) {
    for (SensorData sd : sensors) {
      if (sd.getSensor().getName().equals(name)) {
        return sd;
      }
    }
    return null;
  }

  private SensorUtilsInt() {
  }

  public static ISensorDescription getDescription(final int type) {
    return SensorDescriptions.getDescription(type);
  }

  public static int getNrOfExpectedValues(final Sensor sensor) {
    final ISensorDescription description = SensorDescriptions
        .getDescription(sensor.getType());
    return description.getValueDescriptions().length;
  }

  public static int getMaxNrOfExpectedValues() {
    int res = 0;
    for (ISensorDescription desc : SensorDescriptions.getDescriptions()) {
      res = Math.max(res, desc.getValueDescriptions().length);
    }
    // TODO -get rid of this HC value
    return res;
  }
}
