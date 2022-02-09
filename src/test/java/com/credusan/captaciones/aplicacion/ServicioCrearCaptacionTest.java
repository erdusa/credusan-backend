package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.TestSqlUtils;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class ServicioCrearCaptacionTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioCrearCaptacion servicioCrearCaptacion;

    Asociado asociado;
    Captacion captacion;
    Asociado asociadoCreado;

    @BeforeEach
    void setUp() throws Exception {
        TestSqlUtils.executeQuery("test-data-captacion.sql");

        asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));
        asociadoCreado = servicioCrearAsociado.create(asociado);

        captacion = new Captacion(
                new TipoCaptacion(EnumTipoCaptacion.AHORROS.id),
                0,
                asociado,
                new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id),
                LocalDate.now(),
                (double) 0
        );
    }

    @Test
    void noDeberiaCrearAportesSiTieneUnaActiva() {
        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacion.create(captacion));

        assertEquals("El asociado ya tiene una cuenta de aportes activa", thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionSiIdCaptacionTieneValorAsignado() {
        captacion.setIdCaptacion(1);

        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacion.create(captacion));

        assertEquals("El identificador de la captación no debe tener valor", thrown.getMessage());
    }

    @Test
    void deberiaCrearCaptacion() throws Exception {

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = servicioCrearCaptacion.create(captacion);

        assertEquals(captacion.toString(), captacionCreada.toString());
    }

}