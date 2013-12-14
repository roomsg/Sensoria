package com.grocs.sensors.common;

import android.hardware.Sensor;

/**
 * SensorData interface, wrapper around the Android Sensor class,
 * adding some extra descriptive information
 */
public interface ISensorData {

    /**
     * get original Android Sensor
     * @return
     */
    public Sensor getSensor();

    /**
     * get current values
     * @return array of values, size of array depends on the type of sensor
     */
    public float[] getValues();

    /**
     * get global description
     * @return description
     */
    public ISensorDescription getDescription();

    /**
     * isDefault
     * @return true if the sensor is the default sensor for its type.
     */
    public boolean isDefault();

}