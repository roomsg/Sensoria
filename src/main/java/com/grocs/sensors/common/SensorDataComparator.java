package com.grocs.sensors.common;

import java.util.Comparator;

public class SensorDataComparator implements Comparator<ISensorData> {
    @Override
    public int compare(ISensorData lhs, ISensorData rhs) {
        // 1. sort based on type
        final String type1 = lhs.getDescription().getType();
        final String type2 = rhs.getDescription().getType();
        final int typeRes = type1.compareTo(type2);
        if (0 != typeRes) {
            return typeRes;
        }
        // 2. sort based on default (for given type)
        final int defRes = Boolean.valueOf(rhs.isDefault()).compareTo(
                Boolean.valueOf(lhs.isDefault()));
        if (0 != defRes) {
            return defRes;
        }
        // 3. sort based on name
        final String name1 = lhs.getSensor().getName();
        final String name2 = rhs.getSensor().getName();
        return name1.compareTo(name2);
    }
}
