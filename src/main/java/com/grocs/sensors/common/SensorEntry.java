package com.grocs.sensors.common;

public class SensorEntry {
    private final ISensorData fData;
    private final ISensorDescription fDescription;

    public SensorEntry(ISensorData data) {
        fData = data;
        fDescription = data.getDescription();
    }

    public SensorEntry(ISensorDescription description) {
        fData = null;
        fDescription = description;
    }

    public boolean isSection() {
        return (null == fData);
    }

    public String getName() {
        return isSection() ? fDescription.getType() : fData.getSensor().getName();
    }

    public ISensorData getSensorData() {
        return fData;
    }

    public ISensorDescription getSensorDescription() {
        return fDescription;
    }

    public String toString() {
        return "data:" + fData + ", description:" + fDescription;
    }
}