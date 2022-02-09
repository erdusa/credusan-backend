package com.credusan.asociados.aplicacion;

import com.credusan.TestConfig;
import com.credusan.TestSqlUtils;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.captaciones.aplicacion.ServicioCrearCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ServicioRetirarAsociadoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioRetirarAsociado servicioRetirarAsociado;
    @Autowired
    ServicioCrearCaptacion servicioCrearCaptacion;

    Asociado asociadoCreado;
    DataSource dataSource;

    @BeforeEach
    void setUp() throws Exception {
        TestSqlUtils.executeQuery("test-data-asociado.sql");

        Asociado asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));

        asociadoCreado = servicioCrearAsociado.create(asociado);
    }

    @Test
    void deberiaRetirarAsociado() throws Exception {

        assertTrue(servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado()));
    }

    @Test
    void noDeberiaRetirarAsociadoSiEstaInactivo() throws Exception {
        servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado());

        Exception thrown = assertThrows(Exception.class, () -> servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado()));

        assertEquals(ServicioRetirarAsociado.EL_ASOCIADO_NO_ESTA_ACTIVO, thrown.getMessage());
    }

    @Test
    void noDeberiaRetirarAsociadoSiTieneCaptacionesActivas() throws Exception {
        Captacion captacion = new Captacion(
                new TipoCaptacion(EnumTipoCaptacion.AHORROS.id),
                0,
                asociadoCreado,
                new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id),
                LocalDate.now(),
                (double) 0
        );
        servicioCrearCaptacion.create(captacion);

        Exception thrown = assertThrows(Exception.class, () -> servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado()));

        assertEquals(ServicioRetirarAsociado.EL_ASOCIADO_TIENE_CAPTACIONES_ACTIVAS, thrown.getMessage());
    }
}