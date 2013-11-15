package com.grocs.sensors.common;

public class FloatConvertor {
  final int fMultiplier;

  public FloatConvertor() {
    this(0);
  }

  public FloatConvertor(int precision) {
    fMultiplier = (int) Math.pow(10, precision);
  }

  public float doConvert(float f) {
    return (float) Math.round(f * fMultiplier) / fMultiplier;
  }
}
