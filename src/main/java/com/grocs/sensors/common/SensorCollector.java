package com.grocs.sensors.common;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to collect a filtered collection of sensorData from the SensorManager.
 * Additionaly verifies if a sensor is the default sensor for its type.
 * Note that since API 18, it seems like every sensor has been given its own type,
 * so every sensor seems to be the default sensor for its type - not very usefull...
 *
 * @author roomsg
 */
class SensorCollector {
    private final SensorData[] sensors;

    /**
     * Constructor.
     */
    SensorCollector(final SensorManager sensorManager, final SensorFilter filter) {
        // create filtered sensorSet
        final Set<SensorData> sensorSet = new HashSet<SensorData>();
        for (Sensor sensor : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            // compare with default sensor for the given type / reference equality on purpose !
            final boolean isDefault = (sensor == sensorManager.getDefaultSensor(sensor.getType()));
            // create sensorData
            final SensorData sensorData = new SensorData(sensor, isDefault);
            // register ico passing the filter
            if (filter.filter(sensorData)) {
                sensorSet.add(sensorData);
            }
        }
        // assign to our array result
        this.sensors = sensorSet.toArray(new SensorData[sensorSet.size()]);
    }

    /**
     * get resulting sensors.
     * @return filtered sensorData
     */
    SensorData[] getSensors() {
        return sensors;
    }
}
