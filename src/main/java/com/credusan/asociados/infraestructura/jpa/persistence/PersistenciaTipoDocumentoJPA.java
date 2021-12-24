package com.credusan.asociados.infraestructura.jpa.persistence;

import com.credusan.asociados.infraestructura.jpa.daos.RepositorioTipoDocumento;
import com.credusan.asociados.infraestructura.jpa.entities.EntidadTipoDocumento;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.asociados.dominio.puertos.TipoDocumentoPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersistenciaTipoDocumentoJPA implements TipoDocumentoPersistence {

    private RepositorioTipoDocumento repo;

    public PersistenciaTipoDocumentoJPA(RepositorioTipoDocumento repo) {
        this.repo = repo;
    }

    @Override
    public List<TipoDocumento> getAll() {
        return repo.findAll().stream()
                .map(EntidadTipoDocumento::toTipoDocumento)
                .collect(Collectors.toList());
    }
}
