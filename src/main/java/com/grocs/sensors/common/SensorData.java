package com.grocs.sensors.common;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import android.hardware.Sensor;

public class SensorData implements ISensorData {
    private final Sensor fSensor;
    private final ISensorDescription fDescription;
    private final boolean bIsDefault;
    private final AtomicBoolean bDirty;
    private final float[] fValues;
    private int fAccuracy;

    public SensorData(Sensor sensor, boolean defaultSensor) {
        fSensor = sensor;
        fDescription = SensorDescriptions.getDescription(sensor.getType());
        // use the description to find out how many values are to be expected
        fValues = new float[fDescription.getValueDescriptions().length];
        bDirty = new AtomicBoolean();
        bIsDefault = defaultSensor;
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#getSensor()
     */
    @Override
    public Sensor getSensor() {
        return fSensor;
    }

    /**
     * @see com.grocs.sensors.common.ISensorData#getDescription()
     */
    @Override
    public ISensorDescription getDescription() {
        return fDescription;
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
        return bIsDefault;
    }

    public boolean getAndSetDirty(boolean dirty) {
        return bDirty.getAndSet(dirty);
    }

    /**
     * @param values new values
     * @return true ico changes, false in the other case
     */
    public synchronized boolean update(final float[] values) {
        for (int i = 0; i < fValues.length && i < values.length; ++i) {
            if (fValues[i] != values[i]) {
                fValues[i] = values[i];
                bDirty.set(true);
            }
        }
        return bDirty.get();
    }

    public synchronized boolean update(int accuracy) {
        if (accuracy == fAccuracy) {
            return false;
        }
        fAccuracy = accuracy;
        bDirty.set(true);
        return true;
    }

    @Override
    public String toString() {
        return "SensorData [Sensor=" + fSensor.getName() + ", fValues="
                + Arrays.toString(fValues) + "]";
    }
}