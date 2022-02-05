package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioConsultarCaptacion {

    private final PersistenciaCaptacion repo;

    public ServicioConsultarCaptacion(PersistenciaCaptacion repo) {
        this.repo = repo;
    }

    public List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception {
        return repo.getAllByIdAsociado(idAsociado);
    }

    public Captacion getCuentaAportes(Integer idAsociado) throws Exception {
        return repo.getCuentaAportes(idAsociado);
    }
}
