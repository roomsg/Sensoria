package com.grocs.sensors.common;

import static android.hardware.Sensor.*;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

/*
 * TODO - get this info from resources ?
 */
class SensorDescriptions {
  static final int TYPE_UNKNOWN = -2;
  static final Desc UNKNOWN = new Desc(TYPE_UNKNOWN, SSD.UNKNOWN, SUD.UNKNOWN,
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
    String PROX = "Proximity";
    String ROTAT = "Rotation vector";
    String TEMP = "Temperature";
    String ATEMP = "Ambient temperature";
    String REL_HUMID = "Relative Humidity";
    String MOTION = "Motion";
    String STEP_DETECT = "Step Detect";
    String STEP_COUNT = "Step Count";
    String UNKNOWN = "Unknown";
  }

  // Sensor Unit Descriptions
  private interface SUD {
    String ACCEL = "m/s2";
    String LIN_ACCEL = ACCEL;
    String GRAV = ACCEL;
    String GYR = "rad/s";
    String LIGHT = "lx";
    String MAGN = "μT";
    String ORIENT = "°";
    String PRESS = "mbar";
    String PROX = "cm";
    String ROTAT = "";
    String TEMP = "°C";
    String ATEMP = TEMP;
    String REL_HUMID = "%";
    String COUNT = "nr";
    String UNKNOWN = "Unknown";
  }

  // Sensor Value Descriptions
  private interface SVD {
    String[] GEN_3AXIS = new String[] { "X-Axis", "Y-Axis", "Z-Axis" };
    String[] LIGHT = new String[] { "Ambient light level" };
    String[] PROX = new String[] { "Distance" };
    String[] PRESS = new String[] { "Atmospheric Pressure" };
    String[] HUM = new String[] { "Relative ambient air humidity" };
    String[] TEMP = new String[] { "Ambient temperature" };
    String[] UNKNOWN = new String[] { "?", "?", "?" };
    String[] ORIENT = new String[] { "Azimuth", "TODO", "TODO" };
    String[] NUMBER = new String[] { "Amount" };
  }

  // private static final Map<Integer, ISensorDescription> fDescriptions;
  private static final SparseArray<ISensorDescription> fDescriptions;

  static {
    List<Desc> descs = new ArrayList<Desc>();
    descs
        .add(new Desc(TYPE_ACCELEROMETER, SSD.ACCEL, SUD.ACCEL, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_LIGHT, SSD.LIGHT, SUD.LIGHT, SVD.LIGHT));
    descs.add(new Desc(TYPE_MAGNETIC_FIELD, SSD.MAGN, SUD.MAGN, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_ORIENTATION, SSD.ORIENT, SUD.ORIENT, SVD.ORIENT));
    descs.add(new Desc(TYPE_PROXIMITY, SSD.PROX, SUD.PROX, SVD.PROX));
    descs.add(new Desc(TYPE_TEMPERATURE, SSD.TEMP, SUD.TEMP, SVD.TEMP));
    descs.add(new Desc(TYPE_GRAVITY, SSD.GRAV, SUD.GRAV, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_GYROSCOPE, SSD.GYR, SUD.GYR, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_LINEAR_ACCELERATION, SSD.LIN_ACCEL, SUD.LIN_ACCEL,
        SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_PRESSURE, SSD.PRESS, SUD.PRESS, SVD.PRESS));
    descs.add(new Desc(TYPE_ROTATION_VECTOR, SSD.ROTAT, SUD.ROTAT,
        SVD.GEN_3AXIS));
    descs
        .add(new Desc(TYPE_AMBIENT_TEMPERATURE, SSD.ATEMP, SUD.ATEMP, SVD.TEMP));
    descs.add(new Desc(TYPE_RELATIVE_HUMIDITY, SSD.REL_HUMID, SUD.REL_HUMID,
        SVD.HUM));
    // From API18
    descs.add(new Desc(TYPE_GAME_ROTATION_VECTOR, SSD.ROTAT, SUD.ROTAT, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_GYROSCOPE_UNCALIBRATED, SSD.GYR, SUD.GYR, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_MAGNETIC_FIELD_UNCALIBRATED, SSD.MAGN, SUD.MAGN, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_SIGNIFICANT_MOTION, SSD.MOTION, SUD.UNKNOWN, SVD.UNKNOWN));
    // From API19
    descs.add(new Desc(TYPE_GEOMAGNETIC_ROTATION_VECTOR, SSD.ROTAT, SUD.ROTAT, SVD.GEN_3AXIS));
    descs.add(new Desc(TYPE_STEP_COUNTER, SSD.STEP_COUNT, SUD.COUNT, SVD.NUMBER));
    descs.add(new Desc(TYPE_STEP_DETECTOR, SSD.STEP_DETECT, SUD.UNKNOWN, SVD.UNKNOWN));
    //
    descs.add(new Desc(TYPE_UNKNOWN, SSD.UNKNOWN, SUD.UNKNOWN, SVD.UNKNOWN));

    // fDescriptions = new HashMap<Integer, ISensorDescription>();
    fDescriptions = new SparseArray<ISensorDescription>();
    for (Desc desc : descs) {
      fDescriptions.put(desc.fType, desc);
    }
  }

  public static ISensorDescription getDescription(final int type) {
    final ISensorDescription res = fDescriptions.get(type);
    return (null != res ? res : ISensorDescription.UNKNOWN);
  }

  public static ISensorDescription[] getDescriptions() {
    final int size = fDescriptions.size();
    ISensorDescription[] res = new ISensorDescription[size];
    for (int i = 0; i < size; ++i) {
      res[i] = fDescriptions.valueAt(i);
    }
    return res;
  }
}

class Desc implements ISensorDescription {
  final int fType;
  final String fName;
  final String fUnit;
  final String[] fValueDescriptions;

  Desc(int type, String name, String unit, String[] valueDescriptions) {
    fType = type;
    fName = name;
    fUnit = unit;
    fValueDescriptions = valueDescriptions;
  }

  @Override
  public String getType() {
    return fName;
  }

  @Override
  public String getUnit() {
    return fUnit;
  }

  @Override
  public String[] getValueDescriptions() {
    return fValueDescriptions;
  }
}
