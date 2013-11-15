package com.grocs.sensors.common;

import android.hardware.SensorManager;
import android.util.Log;

/**
 * This class is a ...
 * 
 * @author ladmin
 */
public class SingleSensorDataManager extends AbstractSensorDataManager {
  static final String TAG = SingleSensorDataManager.class.getSimpleName();
  private final String fSensorName;

  /**
   * Constructor.
   * 
   * @param sm
   *          Android sensor manager
   * @param sensorName
   *          name of sensor to follow
   */
  public SingleSensorDataManager(final SensorManager sm, final String sensorName) {
    super(sm);
    fSensorName = sensorName;
    Log.i(TAG, "fSensorName:" + fSensorName);
  }

  @Override
  boolean filter(final ISensorData data) {
    if (null == data || null == fSensorName) {
      return false;
    }
    return fSensorName.equals(data.getSensor().getName());
  }
}
