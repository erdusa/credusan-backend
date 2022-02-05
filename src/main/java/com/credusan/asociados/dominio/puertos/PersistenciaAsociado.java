package com.credusan.asociados.dominio.puertos;

import com.credusan.asociados.dominio.modelos.Asociado;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersistenciaAsociado {

    Asociado insert(Asociado asociado) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    List<Asociado> getAll(Pageable page, boolean soloActivos) throws Exception;

    List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception;

    Asociado update(Asociado asociado) throws Exception;
}
