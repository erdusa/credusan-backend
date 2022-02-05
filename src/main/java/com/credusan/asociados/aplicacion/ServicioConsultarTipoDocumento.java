package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.asociados.dominio.puertos.PersistenciaTipoDocumento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioConsultarTipoDocumento {

    private final PersistenciaTipoDocumento persistence;

    public ServicioConsultarTipoDocumento(PersistenciaTipoDocumento persistence) {
        this.persistence = persistence;
    }

    public List<TipoDocumento> getAll() throws Exception {
        return persistence.getAll();
    }
}
