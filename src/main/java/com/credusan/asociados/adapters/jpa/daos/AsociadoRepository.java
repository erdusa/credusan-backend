package com.credusan.asociados.adapters.jpa.daos;

import com.credusan.asociados.adapters.jpa.entities.AsociadoEntity;
import com.credusan.shared.repository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsociadoRepository extends GenericRepository<AsociadoEntity, Integer> {

    @Query(
            value = "SELECT * FROM asociado WHERE asocnombres||asocprimerApellido||coalesce(asocsegundoApellido,'') ilike REPLACE('%'||:nombres||'%', ' ','%') order by asocnombres, asocprimerApellido, asocsegundoApellido",
            nativeQuery = true
    )
    List<AsociadoEntity> findAllByNames(@Param("nombres") String nombres);
}
