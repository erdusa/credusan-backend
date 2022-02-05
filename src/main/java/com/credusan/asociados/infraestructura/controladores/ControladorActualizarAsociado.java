package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.aplicacion.ServicioActualizarAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/asociados")
public class ControladorActualizarAsociado {

    private final ServicioActualizarAsociado service;

    public ControladorActualizarAsociado(ServicioActualizarAsociado service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Asociado> update(@RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.update(tAsociadoRequest.getIdAsociado(), tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.OK);
    }

}
