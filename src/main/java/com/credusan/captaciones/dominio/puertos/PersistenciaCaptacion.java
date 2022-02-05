package com.credusan.captaciones.dominio.puertos;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.captaciones.dominio.modelos.Captacion;

import java.util.List;

public interface PersistenciaCaptacion {

    Captacion insert(Captacion captacion) throws Exception;

    Captacion update(Captacion captacion) throws Exception;

    Captacion crearCuentaAportes(Asociado asociado) throws Exception;

    Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion);

    List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception;

    Captacion getById(Integer idCaptacion);

    Captacion getCuentaAportes(Integer idAsociado) throws Exception;

}
