package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.aplicacion.ServicioRetirarAsociado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/asociados/retirar")
public class ControladorRetirarAsociado {

    private final ServicioRetirarAsociado service;

    public ControladorRetirarAsociado(ServicioRetirarAsociado service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer idAsociado) throws Exception {
        Boolean resultado = service.retirarAsociado(idAsociado);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

}
