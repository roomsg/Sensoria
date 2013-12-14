package com.grocs.sensors.common;

/**
 * FloatConvertor class is taking care of rounding floats (based on given precision)
 */
class FloatConvertor {
    private final int multiplier;

    /**
     * Creates FloatConvertor with a given precision
     * @param precision, amount of digits behind the comma
     */
    FloatConvertor(final int precision) {
        multiplier = (int) Math.pow(10, precision);
    }

    /**
     * Creates FloatConvertor with a 0 precision
     */
    FloatConvertor() {
        this(0);
    }

    /**
     * Convert to a given precision
     * @param f float to convert
     * @return converted float
     */
    float doConvert(float f) {
        return (float) Math.round(f * multiplier) / multiplier;
    }
}
