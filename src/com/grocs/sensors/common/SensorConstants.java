package com.grocs.sensors.common;

public interface SensorConstants {
  // Property constants
  String PROP_REFRESH_RATE = "interval";
  String PROP_PRECISION = "precision";
  String PROP_SENSOR_NAME = "sensor_name";
  // Defaults constants
  int DEF_REFRESH_RATE = 200;
  int DEF_PRECISION = 1;
  String DEF_REFRESH_RATE_STR = String.valueOf(DEF_REFRESH_RATE);
  String DEF_PRECISION_STR = String.valueOf(DEF_PRECISION);;
}
