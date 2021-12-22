package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.services.AsociadoFinderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/asociados")
public class AsociadoGetController {

    private AsociadoFinderService service;

    public AsociadoGetController(AsociadoFinderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Asociado>> getAll(Pageable page) throws Exception {
        Page<Asociado> lista = service.getAll(page);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asociado> getById(@PathVariable("id") Integer idAsociado) throws Exception {
        Asociado asociado = service.getById(idAsociado);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

    @GetMapping("/name/{names}")
    public ResponseEntity<List<Asociado>> getByNameOrSurnames(@PathVariable("names") String nombres) throws Exception {
        List<Asociado> asociado = service.getAllByNameOrSurnames(nombres);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

}
