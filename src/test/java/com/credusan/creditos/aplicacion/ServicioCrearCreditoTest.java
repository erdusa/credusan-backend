package com.credusan.creditos.aplicacion;

import com.credusan.TestConfig;
import com.credusan.creditos.aplicacion.testdatabuilder.CreditoTestDataBuilder;
import com.credusan.creditos.dominio.enums.EnumTipoEstadoCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.TipoEstadoCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import com.credusan.creditos.shared.ValorCuotaCredito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class ServicioCrearCreditoTest {

    ServicioCrearCredito servicioCrearCredito;

    PersistenciaCredito persistenciaCredito;
    PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion;

    @BeforeEach
    void setUp() {

        persistenciaCredito = Mockito.mock(PersistenciaCredito.class);
        persistenciaCreditoLiquidacion = Mockito.mock(PersistenciaCreditoLiquidacion.class);

        servicioCrearCredito = new ServicioCrearCredito(persistenciaCredito, persistenciaCreditoLiquidacion);
    }

    @Test
    void debeCrearCredito() {

        Credito creditoCreado = new CreditoTestDataBuilder().build();
        creditoCreado.setIdCredito(1);
        creditoCreado.setFechaDesembolso(LocalDate.now());
        creditoCreado.setFechaProximoPago(creditoCreado.getFechaDesembolso().plusMonths(1));
        creditoCreado.setTipoEstadoCredito(new TipoEstadoCredito());
        creditoCreado.getTipoEstadoCredito().setIdTipoEstadoCredito(EnumTipoEstadoCredito.VIGENTE.id);
        creditoCreado.setNumero(Integer.valueOf(LocalDate.now().getYear() + "0001"));
        creditoCreado.setValorCuota(ValorCuotaCredito.calcular(creditoCreado.getValor(), creditoCreado.getPlazo(), creditoCreado.getTasaInteres()));
        creditoCreado.setValorMoraDia(this.calcularValorMoraDia(creditoCreado.getValorCuota(), creditoCreado.getTasaInteresMora()));

        Mockito.when(persistenciaCredito.getNextConsecutivo()).thenReturn(Integer.valueOf(LocalDate.now().getYear() + "0001"));
        Mockito.when(persistenciaCredito.insert(Mockito.any())).thenReturn(creditoCreado);
        Mockito.doNothing().when(persistenciaCreditoLiquidacion).insert(Mockito.any());

        Credito credito = new CreditoTestDataBuilder().build();
        creditoCreado = servicioCrearCredito.ejecutar(credito);

        assertEquals(1, creditoCreado.getIdCredito());
        assertEquals(creditoCreado.toString(), credito.toString());

        Mockito.verify(persistenciaCredito, Mockito.times(1)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(credito.getPlazo())).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiFaltaElPlazo() {
        Credito credito = new CreditoTestDataBuilder().conPlazo(null).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_PLAZO, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiPlazoEsCero() {
        Credito credito = new CreditoTestDataBuilder().conPlazo(0).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_PLAZO, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiPlazoEsNegativo() {
        Credito credito = new CreditoTestDataBuilder().conPlazo(-12).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_PLAZO, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiFaltaElValor() {

        Credito credito = new CreditoTestDataBuilder().conValor(null).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_VALOR, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiValorEsCero() {

        Credito credito = new CreditoTestDataBuilder().conValor(0).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_VALOR, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiValorEsNegativo() {

        Credito credito = new CreditoTestDataBuilder().conValor(-100).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));
        assertEquals(ServicioCrearCredito.FALTA_EL_VALOR, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiFaltaElDeudor() {

        Credito credito = new CreditoTestDataBuilder().conDeudor(null).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_EL_DEUDOR, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiFaltaLaTasaInteres() {
        Credito credito = new CreditoTestDataBuilder().conTasaInteres(null).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_TASA_INTERES, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiTasaInteresEsCero() {
        Credito credito = new CreditoTestDataBuilder().conTasaInteres(0F).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_TASA_INTERES, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiTasaInteresEsNegativo() {
        Credito credito = new CreditoTestDataBuilder().conTasaInteres(-1F).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.FALTA_TASA_INTERES, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    @Test
    void noDebeCrearCreditoSiTasaInteresMoraEsNegativo() {
        Credito credito = new CreditoTestDataBuilder().conTasaInteresMora(-1F).build();

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCredito.ejecutar(credito));

        assertEquals(ServicioCrearCredito.TASA_MORA_NEGATIVA, thrown.getMessage());
        Mockito.verify(persistenciaCredito, Mockito.times(0)).insert(Mockito.any());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).insert(Mockito.any());

    }

    private float calcularValorMoraDia(int valorCuota, float tasaInteresMora) {
        float valorMoraPorDia = valorCuota * (tasaInteresMora / 100) / 30;
        valorMoraPorDia = Math.round(valorMoraPorDia * 100) / 100F;
        return valorMoraPorDia;
    }
}