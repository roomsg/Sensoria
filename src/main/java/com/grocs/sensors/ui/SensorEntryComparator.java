package com.grocs.sensors.ui;

import com.grocs.sensors.ui.SensorEntry;

import java.util.Comparator;

public class SensorEntryComparator implements Comparator<SensorEntry> {
    @Override
    public int compare(SensorEntry lhs, SensorEntry rhs) {
        // 1. sort based on type
        final String type1 = retrieveType(lhs);
        final String type2 = retrieveType(rhs);
        final int typeRes = type1.compareTo(type2);
        if (0 != typeRes) {
            return typeRes;
        }
        // 2. sort based on kind (section vs sensor)
        final int defKind = Boolean.valueOf(rhs.isSection()).compareTo(
                Boolean.valueOf(lhs.isSection()));
        if (0 != defKind) {
            return defKind;
        }
        // 3. sort based on default (for given type)
        if (!lhs.isSection() && !rhs.isSection()) {
            final int defRes = Boolean.valueOf(rhs.getSensorData().isDefault())
                    .compareTo(Boolean.valueOf(lhs.getSensorData().isDefault()));
            if (0 != defRes) {
                return defRes;
            }
            // 4. sort based on name
            final String name1 = lhs.getSensorData().getSensor().getName();
            final String name2 = rhs.getSensorData().getSensor().getName();
            return name1.compareTo(name2);
        }
        return 0;
    }

    private String retrieveType(SensorEntry lhs) {
        return lhs.isSection() ? lhs.getName() : lhs.getSensorData()
                .getDescription().getType();
    }
}
