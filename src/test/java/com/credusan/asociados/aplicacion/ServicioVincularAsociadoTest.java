package com.credusan.asociados.aplicacion;

import com.credusan.TestConfig;
import com.credusan.TestSqlUtils;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ServicioVincularAsociadoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioRetirarAsociado servicioRetirarAsociado;
    @Autowired
    ServicioVincularAsociado servicioVincularAsociado;

    Asociado asociadoCreado;

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

        servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado());
    }

    @Test
    void deberiaVincularAsociado() throws Exception {
        assertTrue(servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado()));
    }

    @Test
    void noDeberiaVincularAsociadoSiEstaActivo() throws Exception {
        servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado());

        Exception thrown = assertThrows(Exception.class, () -> servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado()));

        assertEquals(ServicioVincularAsociado.EL_ASOCIADO_ESTA_ACTIVO, thrown.getMessage());
    }
}