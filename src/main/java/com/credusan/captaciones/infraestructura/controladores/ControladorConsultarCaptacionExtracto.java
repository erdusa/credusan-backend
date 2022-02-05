package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioConsultarCaptacionExtracto;
import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extractocaptacion")
public class ControladorConsultarCaptacionExtracto {
    ServicioConsultarCaptacionExtracto servicio;

    public ControladorConsultarCaptacionExtracto(ServicioConsultarCaptacionExtracto servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<Page<CaptacionExtracto>> getAllByIdCaptacionAndFechas(Pageable pageable, ConsultaCaptacionExtractoDTO extractoDTO) throws Exception {
        return new ResponseEntity<>(servicio.getAllByIdCaptacionAndFechas(pageable, extractoDTO), HttpStatus.OK);
    }
}
