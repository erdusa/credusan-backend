package com.credusan.creditos.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValorCuotaCreditoTest {

    @Test
    void debeCalcularCuotaCorrectamente() {
        assertEquals(343_000, ValorCuotaCredito.calcular(2_000_000, 6, 0.75f));
        assertEquals(223_000, ValorCuotaCredito.calcular(2_000_000, 10, 2));
        assertEquals(128_000, ValorCuotaCredito.calcular(2_000_000, 18, 1.5f));
        assertEquals(106_000, ValorCuotaCredito.calcular(2_000_000, 24, 2));
        assertEquals(58_000, ValorCuotaCredito.calcular(2_000_000, 60, 2));
        assertEquals(45_000, ValorCuotaCredito.calcular(2_000_000, 120, 2));
    }

    @Test
    void debeCalcularCuotaConTasaCero() {
        assertEquals(167_000, ValorCuotaCredito.calcular(1_000_000, 6, 0));
        assertEquals(84_000, ValorCuotaCredito.calcular(1_000_000, 12, 0));
        assertEquals(56_000, ValorCuotaCredito.calcular(1_000_000, 18, 0));
        assertEquals(167_000, ValorCuotaCredito.calcular(10_000_000, 60, 0));
        assertEquals(84_000, ValorCuotaCredito.calcular(10_000_000, 120, 0));
    }

    @Test
    void debeValidarArgumentos() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> ValorCuotaCredito.calcular(0, 1, 1));
        assertEquals(ValorCuotaCredito.VALOR_CREDITO_MAYOR_QUE_CERO, thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> ValorCuotaCredito.calcular(-1, 1, 1));
        assertEquals(ValorCuotaCredito.VALOR_CREDITO_MAYOR_QUE_CERO, thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> ValorCuotaCredito.calcular(1, 0, 1));
        assertEquals(ValorCuotaCredito.PLAZO_MAYOR_QUE_CERO, thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> ValorCuotaCredito.calcular(1, -1, 1));
        assertEquals(ValorCuotaCredito.PLAZO_MAYOR_QUE_CERO, thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> ValorCuotaCredito.calcular(1, 1, -1));
        assertEquals(ValorCuotaCredito.TASA_INTERES_NEGATIVA, thrown.getMessage());
    }

}