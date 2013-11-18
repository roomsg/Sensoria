package com.grocs.sensors.common;

/**
 * Created by ladmin on 18/11/2013.
 */
public interface SensorFilter {
    boolean filter(ISensorData data);

    static class AllSensorFilter implements  SensorFilter {
        @Override
        public boolean filter(ISensorData data) {
            return true;
        }
    }

    static class NameSensorFilter implements SensorFilter {
        private final String name;

        public NameSensorFilter(final String name) {
            this.name = name;
        }
        @Override
        public boolean filter(ISensorData data) {
            return data.getSensor().getName().equals(name);
        }
    }
}
