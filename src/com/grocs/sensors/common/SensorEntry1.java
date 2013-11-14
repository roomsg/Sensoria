package com.grocs.sensors.common;

public class SensorEntry1 {
  private final ISensorData fData;
  private final ISensorDescription fDescription;

  public SensorEntry1(ISensorData data) {
    fData = data;
    fDescription = data.getDescription();
  }

  public SensorEntry1(ISensorDescription description) {
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
}