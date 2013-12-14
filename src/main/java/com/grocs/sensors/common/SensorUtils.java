package com.grocs.sensors.common;

import java.util.Collection;

import android.hardware.Sensor;

/**
 * Utility class collection some util fucntions for Sensor / SensorData
 */
public class SensorUtils {

    /**
     */
    static SensorData retrieveSensor(SensorData[] sensors, Sensor sensor) {
        for (SensorData sd : sensors) {
            if (sd.getSensor().equals(sensor)) {
                return sd;
            }
        }
        return null;
    }

    /**
     */
    static SensorData retrieveSensor(SensorData[] sensors, String name) {
        for (SensorData sd : sensors) {
            if (sd.getSensor().getName().equals(name)) {
                return sd;
            }
        }
        return null;
    }

    public static ISensorDescription getDescription(final int type) {
        return SensorDescriptions.getDescription(type);
    }

    static int getNrOfExpectedValues(final Sensor sensor) {
        final ISensorDescription description = SensorDescriptions
                .getDescription(sensor.getType());
        return description.getValueDescriptions().length;
    }

    static int getMaxNrOfExpectedValues() {
        int maxNr = 0;
        for (ISensorDescription desc : SensorDescriptions.getDescriptions()) {
            maxNr = Math.max(maxNr, desc.getValueDescriptions().length);
        }
        return maxNr;
    }

    /**
     * private constructor for utility class.
     */
    private SensorUtils() {
    }
}
