package com.credusan.creditos.dominio.puertos;

import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.shared.exceptions.NotSavedException;

import java.util.List;

public interface PersistenciaCredito {
    Credito insert(Credito credito) throws NotSavedException;

    List<Credito> getAllByIdAsociado(Integer idAsociado);

    Integer getNextConsecutivo();
}
