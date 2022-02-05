package com.credusan.asociados.dominio.puertos;

import com.credusan.asociados.dominio.modelos.Beneficiario;

import java.util.List;

public interface PersistenciaBeneficiario {

    Beneficiario insert(Beneficiario beneficiario) throws Exception;

    void delete(Integer idBeneficiario) throws Exception;

    List<Beneficiario> getAllByIdAsociado(Integer idAsociado) throws Exception;
}
