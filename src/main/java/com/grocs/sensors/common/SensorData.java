package com.grocs.sensors.common;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import android.hardware.Sensor;

class SensorData implements ISensorData {
    private final Sensor sensor;
    private final boolean isDefault;
    private final ISensorDescription description;
    private final AtomicBoolean dirty = new AtomicBoolean();;
    private final float[] fValues;
    private int accuracy;

    SensorData(final Sensor sensor, final boolean defaultSensor) {
        this.sensor = sensor;
        this.isDefault = defaultSensor;
        //
        description = SensorDescriptions.getDescription(sensor.getType());
        // use the description to find out how many values are to be expected
        fValues = new float[description.getValueDescriptions().length];
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#getSensor()
     */
    @Override
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#getDescription()
     */
    @Override
    public ISensorDescription getDescription() {
        return description;
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#getValues()
     */
    @Override
    public float[] getValues() {
        return fValues;
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#isDefault()
     */
    @Override
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * clear the dirty flag
     * @return previous value of the dirty flag
     */
    boolean clearDirty() {
        return dirty.getAndSet(false);
    }

    /**
     * @param values new values
     * @return true ico changes, false in the other case
     */
    synchronized boolean update(final float[] values) {
        for (int i = 0; i < fValues.length && i < values.length; ++i) {
            if (fValues[i] != values[i]) {
                fValues[i] = values[i];
                dirty.set(true);
            }
        }
        return dirty.get();
    }

    synchronized boolean update(int accuracy) {
        if (accuracy == this.accuracy) {
            return false;
        }
        this.accuracy = accuracy;
        dirty.set(true);
        return true;
    }

    @Override
    public String toString() {
        return "SensorData [Sensor=" + sensor.getName() + ", fValues="
                + Arrays.toString(fValues) + "]";
    }
}