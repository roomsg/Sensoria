package com.grocs.sensors.common;

import android.hardware.Sensor;

public interface ISensorData {

    public Sensor getSensor();

    public float[] getValues();

    public ISensorDescription getDescription();

    public boolean isDefault();

}