package com.grocs.sensors.ui;

public class FloatFormatter {
    private final String fFormatString;

    /**
     * Constructor. Create instance with default precision (0);
     */
    public FloatFormatter() {
        this(0);
    }

    /**
     * Constructor.
     *
     * @param precision precision of formatter, 0 or bigger
     */
    public FloatFormatter(int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException();
        }
        fFormatString = "%+." + precision + "f";
    }

    /**
     * @param f float to convert
     * @return string representation with given precision
     */
    public String doConvert(float f) {
        return String.format(fFormatString, f);
    }
}
