package com.credusan.shared.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {

    @Test
    void NoDebeRedondearPara0Decenas() {

        Exception thrown = assertThrows(Exception.class, () -> NumberUtils.ceilToTens(1, 0));
        assertEquals(NumberUtils.DECENAS_MAYOR_QUE_CERO, thrown.getMessage());
    }

    @Test
    void debeRedondearCeroACero() {

        for (int i = 1; i <= 3; i++) {
            assertEquals(0, NumberUtils.ceilToTens(0, i));
        }

    }

    @Test
    void debeRedondearValoresPositivos() {

        for (int i = 1; i <= 6; i++) {
            int decenas = (int) Math.pow(10, i);
            for (int j = 0; j <= decenas * 10; j += 10) {
                int valorEsperado = ((j / decenas) + 1) * decenas;

                assertEquals(valorEsperado, NumberUtils.ceilToTens(j + 1, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j + 5, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j + 9, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j + 10, i));
            }
        }

    }

    @Test
    void debeRedondearValoresNegativos() {

        for (int i = 1; i <= 6; i++) {
            int decenas = (int) Math.pow(10, i) * -1;
            for (int j = 0; j >= decenas * 10; j -= 10) {
                int valorEsperado = ((j / decenas) + 1) * decenas;

                assertEquals(valorEsperado, NumberUtils.ceilToTens(j - 1, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j - 5, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j - 9, i));
                assertEquals(valorEsperado, NumberUtils.ceilToTens(j - 10, i));
            }
        }

    }
}