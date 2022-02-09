package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.TestSqlUtils;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class ServicioCrearCaptacionExtractoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioConsultarCaptacion servicioConsultarCaptacion;
    @Autowired
    ServicioCrearCaptacionExtracto servicioCrearCaptacionExtracto;

    Asociado asociado;
    CaptacionExtracto captacionExtracto;
    Asociado asociadoCreado;
    Captacion captacionCreada;

    @BeforeEach
    void setUp() throws Exception {
        TestSqlUtils.executeQuery("test-data-captacion.sql");

        asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5)
        );
        asociadoCreado = servicioCrearAsociado.create(asociado);
        captacionCreada = servicioConsultarCaptacion.getAllByIdAsociado(asociadoCreado.getIdAsociado()).get(0);

        captacionExtracto = new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 200000,
                (double) 0
        );

        captacionExtracto.setCaptacion(captacionCreada);
    }

    @Test
    void deberiaCrearCaptacionExtracto() throws Exception {

        CaptacionExtracto captacionExtractoCreado = servicioCrearCaptacionExtracto.create(captacionExtracto);

        assertEquals(captacionExtracto.toString(), captacionExtractoCreado.toString());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiValorDebitoEsNegativo() {

        captacionExtracto.setValorDebito(-200000D);
        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacionExtracto.create(captacionExtracto));

        assertEquals(ServicioCrearCaptacionExtracto.EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiValorCreditoEsNegativo() {

        captacionExtracto.setValorCredito(-200000D);
        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacionExtracto.create(captacionExtracto));

        assertEquals(ServicioCrearCaptacionExtracto.EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiDebitoYCreditoSonCero() {

        captacionExtracto.setValorDebito(0D);
        captacionExtracto.setValorCredito(0D);
        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacionExtracto.create(captacionExtracto));


        assertEquals(ServicioCrearCaptacionExtracto.EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiDebitoYCreditoTienenValor() {

        captacionExtracto.setValorDebito(100D);
        captacionExtracto.setValorCredito(100D);
        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacionExtracto.create(captacionExtracto));

        assertEquals(ServicioCrearCaptacionExtracto.NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiSaldoQuedaNegativo() {

        captacionExtracto.setValorDebito(0D);
        captacionExtracto.setValorCredito(captacionCreada.getSaldo() + 1);
        Exception thrown = assertThrows(Exception.class, () -> servicioCrearCaptacionExtracto.create(captacionExtracto));

        assertEquals(ServicioCrearCaptacionExtracto.EL_SALDO_NO_PUEDE_QUEDAR_NEGATIVO, thrown.getMessage());
    }
}