package com.grocs.sensors.common;

import static android.hardware.Sensor.*;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import android.util.SparseArray;

/**
 * TODO - get this info from resources ?
 */
class SensorDescriptions {
  private static final int TYPE_UNKNOWN = -2;
  static final SensorDescription UNKNOWN = new SensorDescription(SSD.UNKNOWN, SUD.UNKNOWN,
      SVD.UNKNOWN);

  // Short Sensor Descriptions
  private interface SSD {
    String ACCEL = "Accelerometer";
    String GRAV = "Gravity";
    String GYR = "Gyroscope";
    String LIGHT = "Light";
    String LIN_ACCEL = "Linear acceleration";
    String MAGN = "Magnetic field";
    String ORIENT = "Orientation";
    String PRESS = "Pressure";
    String PROXIMITY = "Proximity";
    String ROTATION = "Rotation vector";
    String TEMP = "Temperature";
    String ATEMP = "Ambient temperature";
    String REL_HUM = "Relative Humidity";
    String MOTION = "Motion";
    String STEP_DETECT = "Step Detect";
    String STEP_COUNT = "Step Count";
    String UNKNOWN = "Unknown";
  }

  // Sensor Unit Descriptions
  private interface SUD {
    String EMPTY = "";
    String UNKNOWN = "Unknown";
    //
    String ACCEL = "m/s2";
    String LIN_ACCEL = ACCEL;
    String GRAV = ACCEL;
    String GYR = "rad/s";
    String LIGHT = "lx";
    String MAGN = "μT";
    String ORIENT = "°";
    String PRESS = "mbar";
    String PROXIMITY = "cm";
    String ROTATION = EMPTY;
    String TEMP = "°C";
    String ATEMP = TEMP;
    String REL_HUM = "%";
    String COUNT = "nr";
  }

  // Sensor Value Descriptions
  private interface SVD {
    String[] GEN_3AXIS = new String[] { "X-Axis", "Y-Axis", "Z-Axis" };
    String[] LIGHT = new String[] { "Ambient light level" };
    String[] PROXIMITY = new String[] { "Distance" };
    String[] PRESS = new String[] { "Atmospheric Pressure" };
    String[] HUM = new String[] { "Relative ambient air humidity" };
    String[] TEMP = new String[] { "Ambient temperature" };
    String[] UNKNOWN = new String[] { "?", "?", "?" };
    String[] ORIENT = new String[] { "Azimuth", "TODO", "TODO" };
    String[] NUMBER = new String[] { "Amount" };
  }

  // Sensor Full Descriptions
  private interface SFD {
    ISensorDescription UNKNOWN        = new SensorDescription(SSD.UNKNOWN, SUD.UNKNOWN, SVD.UNKNOWN);
    ISensorDescription ACCEL          = new SensorDescription(SSD.ACCEL, SUD.ACCEL, SVD.GEN_3AXIS);
    ISensorDescription LIN_ACCEL      = new SensorDescription(SSD.LIN_ACCEL, SUD.ACCEL, SVD.GEN_3AXIS);
    ISensorDescription ATEMP          = new SensorDescription(SSD.ATEMP, SUD.TEMP, SVD.TEMP);
    ISensorDescription LIGHT          = new SensorDescription(SSD.LIGHT, SUD.LIGHT, SVD.LIGHT);
    ISensorDescription MAGN           = new SensorDescription(SSD.MAGN, SUD.MAGN, SVD.GEN_3AXIS);
    ISensorDescription ORIENT         = new SensorDescription(SSD.ORIENT, SUD.ORIENT, SVD.ORIENT);
    ISensorDescription PROXIMITY      = new SensorDescription(SSD.PROXIMITY, SUD.PROXIMITY, SVD.PROXIMITY);
    ISensorDescription PRESS          = new SensorDescription(SSD.PRESS, SUD.PRESS, SVD.PRESS);
    ISensorDescription ROTATION       = new SensorDescription(SSD.ROTATION, SUD.ROTATION, SVD.GEN_3AXIS);
//    ISensorDescription HUM            = new SensorDescription(SSD.HUM, SUD.HUM, SVD.HUM);
    ISensorDescription REL_HUMIDITY   = new SensorDescription(SSD.REL_HUM, SUD.REL_HUM, SVD.HUM);
    ISensorDescription TEMP           = new SensorDescription(SSD.TEMP, SUD.TEMP, SVD.TEMP);
    ISensorDescription GRAV           = new SensorDescription(SSD.GRAV, SUD.GRAV, SVD.GEN_3AXIS);
    ISensorDescription GYRO           = new SensorDescription(SSD.GYR, SUD.GYR, SVD.GEN_3AXIS);
    ISensorDescription MOTION         = new SensorDescription(SSD.MOTION, SUD.UNKNOWN, SVD.UNKNOWN);
    ISensorDescription STEP_NUMBER    = new SensorDescription(SSD.STEP_COUNT, SUD.COUNT, SVD.NUMBER);
    ISensorDescription STEP_DETECT    = new SensorDescription(SSD.STEP_DETECT, SUD.UNKNOWN, SVD.NUMBER);
  }

  private static final SparseArray<ISensorDescription> descriptionMap;
  private static final ISensorDescription[] descriptions;

  static {
    descriptionMap = new SparseArray<ISensorDescription>();
    descriptionMap.put(TYPE_UNKNOWN, SFD.UNKNOWN);
    //
    descriptionMap.put(TYPE_ACCELEROMETER, SFD.ACCEL);
    descriptionMap.put(TYPE_LIGHT, SFD.LIGHT);
    descriptionMap.put(TYPE_MAGNETIC_FIELD, SFD.MAGN);
    descriptionMap.put(TYPE_ORIENTATION, SFD.ORIENT);
    descriptionMap.put(TYPE_PROXIMITY, SFD.PROXIMITY);
    descriptionMap.put(TYPE_TEMPERATURE, SFD.TEMP);
    descriptionMap.put(TYPE_GRAVITY, SFD.GRAV);
    descriptionMap.put(TYPE_GYROSCOPE, SFD.GYRO);
    descriptionMap.put(TYPE_LINEAR_ACCELERATION, SFD.ACCEL);
    descriptionMap.put(TYPE_PRESSURE, SFD.PRESS);
    descriptionMap.put(TYPE_ROTATION_VECTOR, SFD.ROTATION);
    descriptionMap.put(TYPE_AMBIENT_TEMPERATURE, SFD.ATEMP);
    descriptionMap.put(TYPE_RELATIVE_HUMIDITY, SFD.REL_HUMIDITY);
    // From API-18
    descriptionMap.put(TYPE_GAME_ROTATION_VECTOR, SFD.ROTATION);
    descriptionMap.put(TYPE_GYROSCOPE_UNCALIBRATED, SFD.GYRO);
    descriptionMap.put(TYPE_MAGNETIC_FIELD_UNCALIBRATED, SFD.MAGN);
    descriptionMap.put(TYPE_SIGNIFICANT_MOTION, SFD.MOTION);
    // From API-19
    descriptionMap.put(TYPE_GEOMAGNETIC_ROTATION_VECTOR, SFD.ROTATION);
    descriptionMap.put(TYPE_STEP_COUNTER, SFD.STEP_NUMBER);
    descriptionMap.put(TYPE_STEP_DETECTOR, SFD.STEP_DETECT);
    
    Set<ISensorDescription> descs = new HashSet<ISensorDescription>();
    for (int i = 0; i < descriptionMap.size(); ++i) {
      descs.add(descriptionMap.valueAt(i));
    }
    descriptions = descs.toArray(new ISensorDescription[descs.size()]);
  }

  public static ISensorDescription getDescription(final int type) {
    final ISensorDescription res = descriptionMap.get(type);
    return (null != res ? res : SFD.UNKNOWN);
  }

  public static ISensorDescription[] getDescriptions() {
    return descriptions;
  }
}

class SensorDescription implements ISensorDescription {
  private final String typeName;
  private final String unit;
  private final String[] valueDescriptions;

  SensorDescription(String typeName, String unit, String[] valueDescriptions) {
    this.typeName = typeName;
    this.unit = unit;
    this.valueDescriptions = valueDescriptions;
  }

  @Override
  public String getType() {
    return typeName;
  }

  @Override
  public String getUnit() {
    return unit;
  }

  @Override
  public String[] getValueDescriptions() {
    return valueDescriptions;
  }
  

  @Override
  public String toString() {
    return "SensorDescription{" +
                "typeName='" + typeName + '\'' +
                ", unit='" + unit + '\'' +
                ", valueDescriptions=" + Arrays.toString(valueDescriptions) +
                '}';
    }
}
