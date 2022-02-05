package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/asociados")
public class ControladorInsertarAsociado {

    private final ServicioCrearAsociado service;

    public ControladorInsertarAsociado(ServicioCrearAsociado service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Asociado> create(@Valid @RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.create(tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.CREATED);
    }

}
