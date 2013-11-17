package com.grocs.sensors;

import java.util.Collection;

import android.hardware.Sensor;

import com.grocs.sensors.common.ISensorData;

public class SensorUtils {

    /**
     */
    public static ISensorData retrieveSensor(Collection<ISensorData> sensors,
                                             Sensor sensor) {
        for (ISensorData sd : sensors) {
            if (sd.getSensor().equals(sensor)) {
                return sd;
            }
        }
        return null;
    }

    /**
     */
    public static ISensorData retrieveSensor(ISensorData[] sensors, String name) {
        for (ISensorData sd : sensors) {
            if (sd.getSensor().getName().equals(name)) {
                return sd;
            }
        }
        return null;
    }

    private SensorUtils() {
    }
}
