package com.credusan.creditos.shared;

import com.credusan.shared.utils.NumberUtils;

public class ValorCuotaCredito {

    public static final String PLAZO_MAYOR_QUE_CERO = "El plazo debe ser mayor que cero";
    public static final String TASA_INTERES_NEGATIVA = "La tasa de interés no puede ser negativa";
    public static final String VALOR_CREDITO_MAYOR_QUE_CERO = "El valor del crédito debe ser mayor que cero";

    private ValorCuotaCredito() {
    }

    public static int calcular(int valorCredito, int plazo, float tasaInteres) {

        validarArgumentos(valorCredito, plazo, tasaInteres);

        double factorMultiplicador = obtenerFactorMultiplicador(plazo, tasaInteres);
        double valorCuota = valorCredito * factorMultiplicador;

        return (int) NumberUtils.ceilToTens(valorCuota, 3);
    }

    private static void validarArgumentos(int valorCredito, int plazo, float tasaInteres) {

        if (valorCredito <= 0) {
            throw new IllegalArgumentException(VALOR_CREDITO_MAYOR_QUE_CERO);
        }

        if (plazo <= 0) {
            throw new IllegalArgumentException(PLAZO_MAYOR_QUE_CERO);
        }

        if (tasaInteres < 0) {
            throw new IllegalArgumentException(TASA_INTERES_NEGATIVA);
        }
    }

    private static double obtenerFactorMultiplicador(int plazo, float tasaInteres) {

        if (tasaInteres == 0) {
            return 1.0 / plazo;
        }

        double factorInteres = Math.pow(1 + tasaInteres / 100, plazo);
        return (factorInteres * tasaInteres / 100) / (factorInteres - 1);
    }

}
