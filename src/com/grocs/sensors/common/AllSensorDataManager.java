package com.grocs.sensors.common;

import android.hardware.SensorManager;

/**
 * This class is ...
 * 
 * @author ladmin
 */
public class AllSensorDataManager extends AbstractSensorDataManager {
  static final String TAG = AllSensorDataManager.class.getSimpleName();

  public AllSensorDataManager(final SensorManager sm) {
    super(sm);
  }

  @Override
  boolean filter(final ISensorData data) {
    return true;
  }
}
