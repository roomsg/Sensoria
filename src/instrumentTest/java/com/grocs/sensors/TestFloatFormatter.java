package com.grocs.sensors;

import junit.framework.TestCase;

public class TestFloatFormatter extends TestCase {
  public void testPrecisionOnDefaultConstructor() {
    FloatFormatter ff = new FloatFormatter();
    assertEquals("+3", ff.doConvert((float) 3.4567));
    assertEquals("-3", ff.doConvert((float) -3.4567));
    // TODO - Duh, not exactly what I was expecting
    assertEquals("+5", ff.doConvert((float) 4.5678));
    assertEquals("-5", ff.doConvert((float) -4.5678));
  }

  public void testPrecisionZero() {
    FloatFormatter ff = new FloatFormatter(0);
    assertEquals("+3", ff.doConvert((float) 3.4567));
    assertEquals("-3", ff.doConvert((float) -3.4567));
    // TODO - Duh, not exactly what I was expecting
    assertEquals("+5", ff.doConvert((float) 4.5678));
    assertEquals("-5", ff.doConvert((float) -4.5678));
  }

  public void testPrecisionOne() {
    FloatFormatter ff = new FloatFormatter(1);
    assertEquals("+2.3", ff.doConvert((float) 2.3456));
    assertEquals("-2.3", ff.doConvert((float) -2.3456));
    // TODO - Duh, not exactly what I was expecting
    assertEquals("+3.5", ff.doConvert((float) 3.4567));
    assertEquals("-3.5", ff.doConvert((float) -3.4567));
  }

  public void testPrecisionTwo() {
    FloatFormatter ff = new FloatFormatter(2);
    assertEquals("+1.23", ff.doConvert((float) 1.2345));
    assertEquals("-1.23", ff.doConvert((float) -1.2345));
    // TODO - Duh, not exactly what I was expecting
    assertEquals("+2.35", ff.doConvert((float) 2.3456));
    assertEquals("-2.35", ff.doConvert((float) -2.3456));
  }

  public void testPrecisionMinusOne() {
    try {
      new FloatFormatter(-1);
    } catch (IllegalArgumentException iae) {
      return;
    } catch (Throwable t) {
    }
    fail("exception expected here !");
  }
}
