package com.grocs.sensors.common;

import java.util.HashSet;
import java.util.Set;

import android.hardware.Sensor;
import android.hardware.SensorManager;

/**
 * Utility class to collect all sensorData coming from the standard
 * SensorManager. Apart from simply collecting data from the sensormanager, it
 * additionally takes care of finding / registering all default sensors as well.
 * 
 * @author ladmin
 */
class SensorCollector {
  final SensorManager fSM;
  final SensorData[] fSensors;

  /** Constructor. */
  SensorCollector(final SensorManager sm) {
    fSM = sm;
    // create our sensorArray
    fSensors = retrieveSensors();
  }

  public SensorData[] getSensors() {
    return fSensors;
  }

  public SensorData[] retrieveSensors() {
    // create bare sensorlist
    final Set<SensorData> sensors = new HashSet<SensorData>();
    // first run to get all regular sensors
    for (Sensor sensor : fSM.getSensorList(Sensor.TYPE_ALL)) {
      // get default sensor for the given sensor type
      final Sensor defSensor = fSM.getDefaultSensor(sensor.getType());
      // register sensor (+ default or not)
      sensors.add(new SensorData(sensor, defSensor == sensor));
    }
    // return result
    return sensors.toArray(new SensorData[sensors.size()]);
  }
}
