package com.credusan.creditos.infraestructura.controladores;

import com.credusan.creditos.aplicacion.ServicioListarCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/creditos")
public class ControladorListarCreditos {

    private final ServicioListarCredito servicioListarCredito;

    public ControladorListarCreditos(ServicioListarCredito servicioListarCredito) {
        this.servicioListarCredito = servicioListarCredito;
    }

    @GetMapping("/asociado/saldados/{idAsociado}")
    public ResponseEntity<List<Credito>> getAllSaldadosCreditosByIdAsociado(@PathVariable int idAsociado) {
        return new ResponseEntity<>(servicioListarCredito.getAllSaldadosByIdAsociado(idAsociado), HttpStatus.OK);
    }

    @GetMapping("/asociado/vigentes/{idAsociado}")
    public ResponseEntity<List<Credito>> getAllVigentesCreditosByIdAsociado(@PathVariable int idAsociado) {
        return new ResponseEntity<>(servicioListarCredito.getAllVigentesByIdAsociado(idAsociado), HttpStatus.OK);
    }
}
