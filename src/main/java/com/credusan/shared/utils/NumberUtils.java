package com.credusan.shared.utils;

public class NumberUtils {

    public static final String DECENAS_MAYOR_QUE_CERO = "El número de decenas debe ser mayor que cero";

    private NumberUtils() {
    }

    public static long ceilToTens(double value, int numTens) {

        if (numTens <= 0) {
            throw new IllegalArgumentException(DECENAS_MAYOR_QUE_CERO);
        }

        long tens = (long) Math.pow(10, numTens);

        long ceiledValue = Math.round(value);
        int valueToAdd = 0;
        if (value % tens != 0) {
            valueToAdd = ceiledValue > 0 ? 1 : -1;
        }

        ceiledValue = ((ceiledValue / tens) + valueToAdd) * tens;

        return ceiledValue;
    }

}
