package com.grocs.sensors.common;

public interface ISensorDescription {

    String getType();

    String getUnit();

    String[] getValueDescriptions();

//  final ISensorDescription UNKNOWN = SensorDescriptions.UNKNOWN;
}
