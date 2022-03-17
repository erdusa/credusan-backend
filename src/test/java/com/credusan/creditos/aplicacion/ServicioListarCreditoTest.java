package com.credusan.creditos.aplicacion;

import com.credusan.creditos.aplicacion.testdatabuilder.CreditoLiquidacionTestDataBuilder;
import com.credusan.creditos.aplicacion.testdatabuilder.CreditoTestDataBuilder;
import com.credusan.creditos.dominio.enums.EnumTipoEstadoCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicioListarCreditoTest {

    ServicioListarCredito servicioListarCredito;
    PersistenciaCredito persistenciaCredito;
    PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion;

    @BeforeEach
    void setUp() {
        persistenciaCredito = Mockito.mock(PersistenciaCredito.class);
        persistenciaCreditoLiquidacion = Mockito.mock(PersistenciaCreditoLiquidacion.class);
        servicioListarCredito = new ServicioListarCredito(persistenciaCredito, persistenciaCreditoLiquidacion);
    }

    @Test
    void debeListarCreditosParaAsociadoConCreditos() {

        List<Credito> listaCreditos = new ArrayList<>();

        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(1).build());
        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(2)
                .conIdTipoEstadoCredito(EnumTipoEstadoCredito.SALDADO.id).build());
        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(3)
                .conIdTipoEstadoCredito(EnumTipoEstadoCredito.SALDADO.id).build());

        Mockito.when(persistenciaCredito.getAllByIdAsociado(Mockito.anyInt())).thenReturn(listaCreditos);
        Mockito.when(persistenciaCreditoLiquidacion.getAllByIdCredito(2)).thenReturn(new CreditoLiquidacionTestDataBuilder().buildList(12));
        Mockito.when(persistenciaCreditoLiquidacion.getAllByIdCredito(3)).thenReturn(new CreditoLiquidacionTestDataBuilder().buildList(18));

        List<Credito> listaCreditosRetornados = servicioListarCredito.getAllSaldadosByIdAsociado(Mockito.anyInt());

        assertEquals(2, listaCreditosRetornados.size());
        assertEquals(12, listaCreditosRetornados.get(0).getListaCreditoLiquidacion().size());
        assertEquals(18, listaCreditosRetornados.get(1).getListaCreditoLiquidacion().size());

        Mockito.verify(persistenciaCredito, Mockito.times(1)).getAllByIdAsociado(Mockito.anyInt());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(2)).getAllByIdCredito(Mockito.anyInt());

    }

    @Test
    void noDebeListarCreditosParaAsociadosSinCreditos() {

        Mockito.when(persistenciaCredito.getAllByIdAsociado(Mockito.anyInt())).thenReturn(new ArrayList<>());

        List<Credito> listaCredito = servicioListarCredito.getAllSaldadosByIdAsociado(Mockito.anyInt());

        assertEquals(0, listaCredito.size());

        Mockito.verify(persistenciaCredito, Mockito.times(1)).getAllByIdAsociado(Mockito.anyInt());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).getAllByIdCredito(Mockito.anyInt());

    }

    @Test
    void debeListarCreditosVigentesParaAsociadoConCreditos() {

        List<Credito> listaCreditos = new ArrayList<>();

        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(1).build());
        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(2).build());
        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(3)
                .conIdTipoEstadoCredito(EnumTipoEstadoCredito.SALDADO.id).build());

        Mockito.when(persistenciaCredito.getAllByIdAsociado(Mockito.anyInt())).thenReturn(listaCreditos);
        Mockito.when(persistenciaCreditoLiquidacion.getAllByIdCredito(1))
                .thenReturn(new CreditoLiquidacionTestDataBuilder().buildList(6));
        Mockito.when(persistenciaCreditoLiquidacion.getAllByIdCredito(2))
                .thenReturn(new CreditoLiquidacionTestDataBuilder().buildList(12));

        List<Credito> listaCreditosRetornados = servicioListarCredito.getAllVigentesByIdAsociado(Mockito.anyInt());

        assertEquals(2, listaCreditosRetornados.size());
        assertEquals(6, listaCreditosRetornados.get(0).getListaCreditoLiquidacion().size());
        assertEquals(12, listaCreditosRetornados.get(1).getListaCreditoLiquidacion().size());

        Mockito.verify(persistenciaCredito, Mockito.times(1)).getAllByIdAsociado(Mockito.anyInt());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(2)).getAllByIdCredito(Mockito.anyInt());

    }

    @Test
    void noDebeListarCreditosVigentesParaAsociadosSinCreditos() {

        List<Credito> listaCreditos = new ArrayList<>();

        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(1)
                .conIdTipoEstadoCredito(EnumTipoEstadoCredito.SALDADO.id).build());
        listaCreditos.add(new CreditoTestDataBuilder().conIdCredito(2)
                .conIdTipoEstadoCredito(EnumTipoEstadoCredito.SALDADO.id).build());

        Mockito.when(persistenciaCredito.getAllByIdAsociado(Mockito.anyInt())).thenReturn(listaCreditos);

        List<Credito> listaCredito = servicioListarCredito.getAllVigentesByIdAsociado(Mockito.anyInt());

        assertEquals(0, listaCredito.size());

        Mockito.verify(persistenciaCredito, Mockito.times(1)).getAllByIdAsociado(Mockito.anyInt());
        Mockito.verify(persistenciaCreditoLiquidacion, Mockito.times(0)).getAllByIdCredito(Mockito.anyInt());

    }
}