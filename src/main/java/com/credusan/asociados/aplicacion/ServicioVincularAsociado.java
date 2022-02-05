package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
public class ServicioVincularAsociado {

    static final String EL_ASOCIADO_ESTA_ACTIVO = "El asociado está activo";

    private final PersistenciaAsociado persistence;
    private final PersistenciaCaptacion captacionPersistence;

    public ServicioVincularAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaCaptacion captacionPersistence) {
        this.persistence = persistenciaAsociado;
        this.captacionPersistence = captacionPersistence;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean vincular(Integer idAsociado) throws Exception {

        Asociado asociado = persistence.getById(idAsociado);

        if (Boolean.TRUE.equals(asociado.getActivo())) {
            throw new ValidationException(EL_ASOCIADO_ESTA_ACTIVO);
        }

        asociado.setActivo(true);
        Asociado asociadoU = persistence.update(asociado);

        captacionPersistence.crearCuentaAportes(asociadoU);

        return asociadoU.getActivo();
    }

}
