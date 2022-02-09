package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.TestSqlUtils;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class ServicioConsultarCaptacionExtractoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioConsultarCaptacion servicioConsultarCaptacion;
    @Autowired
    ServicioCrearCaptacionExtracto servicioCrearCaptacionExtracto;
    @Autowired
    ServicioConsultarCaptacionExtracto servicioConsultarCaptacionExtracto;

    Asociado asociado;
    CaptacionExtracto captacionExtracto;
    Asociado asociadoCreado;
    Captacion captacionCreada;
    CaptacionExtracto captacionExtractoCreado;

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
        captacionExtractoCreado = servicioCrearCaptacionExtracto.create(captacionExtracto);
    }

    @Test
    void deberiaRetornarUnRegistroSinFiltrarFechas() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());
    }

    @Test
    void deberiaRetornarUnRegistroConFiltroDeFechaInicial() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        extractoDTO.setFechaInicial(LocalDate.now());
        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());

        extractoDTO.setFechaInicial(LocalDate.now().minusDays(1));
        lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());
    }

    @Test
    void deberiaRetornarUnRegistroConFiltroDeFechaFinal() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        extractoDTO.setFechaFinal(LocalDate.now());
        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());

        extractoDTO.setFechaFinal(LocalDate.now().plusDays(1));
        lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());
    }

    @Test
    void deberiaRetornarUnRegistroConFiltroDeFechaInicialYFinal() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        extractoDTO.setFechaInicial(LocalDate.now());
        extractoDTO.setFechaFinal(LocalDate.now());
        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());

        extractoDTO.setFechaInicial(LocalDate.now());
        extractoDTO.setFechaFinal(LocalDate.now().plusDays(1));
        lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());

        extractoDTO.setFechaInicial(LocalDate.now().minusDays(1));
        extractoDTO.setFechaFinal(LocalDate.now());
        lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());

        extractoDTO.setFechaInicial(LocalDate.now().minusDays(1));
        extractoDTO.setFechaFinal(LocalDate.now().plusDays(1));
        lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(1, lista.getTotalElements());
    }

    @Test
    void noDeberiaRetornarRegistrosConFechaInicialFutura() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        extractoDTO.setFechaInicial(LocalDate.now().plusDays(1));
        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(0, lista.getTotalElements());

    }

    @Test
    void noDeberiaRetornarRegistrosConFechaFinalMenorAActual() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(captacionCreada.getIdCaptacion());

        extractoDTO.setFechaFinal(LocalDate.now().minusDays(1));
        Page<CaptacionExtracto> lista = servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO);
        assertEquals(0, lista.getTotalElements());

    }

    @Test
    void deberiaRetornarErrorSiIdCaptacionEsNulo() {

        Pageable page = PageRequest.of(0, 10);
        ConsultaCaptacionExtractoDTO extractoDTO = new ConsultaCaptacionExtractoDTO();
        extractoDTO.setIdCaptacion(null);
        Exception thrown = assertThrows(Exception.class, () -> servicioConsultarCaptacionExtracto.getAllByIdCaptacionAndFechas(page, extractoDTO));
        assertEquals(ServicioConsultarCaptacionExtracto.DEBE_ESPECIFICAR_EL_IDENTIFICADOR_DE_LA_CAPTACION, thrown.getMessage());

    }
}