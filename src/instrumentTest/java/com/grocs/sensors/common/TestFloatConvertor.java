package com.grocs.sensors.common;

import junit.framework.TestCase;

public class TestFloatConvertor extends TestCase {

  public void testConstructorNoArg() {
    FloatConvertor fc = new FloatConvertor();
    assertEquals(1, fc.doConvert(1), 0.1);
    assertEquals(1, fc.doConvert((float) 1.49), 0.1);
    assertEquals(2, fc.doConvert((float) 1.50), 0.1);
    assertEquals(2, fc.doConvert((float) 1.51), 0.1);
  }

  public void testConstructorArgPresision1() {
    FloatConvertor fc = new FloatConvertor(1);
    assertEquals(1.0, fc.doConvert(1), 0.01);
    assertEquals(1.2, fc.doConvert((float) 1.24), 0.01);
    assertEquals(1.3, fc.doConvert((float) 1.25), 0.01);
    assertEquals(1.3, fc.doConvert((float) 1.26), 0.01);
  }

  public void testConstructorArgPresision2() {
    FloatConvertor fc = new FloatConvertor(2);
    assertEquals(1.0, fc.doConvert(1), 0.001);
    assertEquals(1.23, fc.doConvert((float) 1.234), 0.001);
    assertEquals(1.24, fc.doConvert((float) 1.235), 0.001);
    assertEquals(1.24, fc.doConvert((float) 1.236), 0.001);
  }
}
