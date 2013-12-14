package com.grocs.sensors.common;

public interface ISensorDescription {

//    String getRawType();

    String getType();

    String getUnit();

    String[] getValueDescriptions();
}
