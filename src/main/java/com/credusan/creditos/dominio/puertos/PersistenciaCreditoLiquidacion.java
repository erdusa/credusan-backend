package com.credusan.creditos.dominio.puertos;

import com.credusan.creditos.dominio.modelos.CreditoLiquidacion;

import java.util.List;

public interface PersistenciaCreditoLiquidacion {

    void insert(CreditoLiquidacion creditoLiquidacion);

    List<CreditoLiquidacion> getAllByIdCredito(Integer idCredito);

}
