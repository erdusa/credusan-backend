package com.credusan.creditos.infraestructura.controladores;

import com.credusan.creditos.aplicacion.ServicioCrearCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditos")
public class ControladorCrearCredito {

    private final ServicioCrearCredito servicio;

    public ControladorCrearCredito(ServicioCrearCredito servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Credito> create(@RequestBody Credito credito) {
        Credito creditoResponse = servicio.ejecutar(credito);
        return new ResponseEntity<>(creditoResponse, HttpStatus.CREATED);
    }
}
