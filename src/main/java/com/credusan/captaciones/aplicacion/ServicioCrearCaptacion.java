package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;

@Service
public class ServicioCrearCaptacion {

    private final PersistenciaCaptacion repo;

    public ServicioCrearCaptacion(PersistenciaCaptacion repo) {
        this.repo = repo;
    }

    public Captacion create(Captacion captacion) throws Exception {
        if (captacion.getIdCaptacion() != null) {
            throw new ValidationException("El identificador de la captación no debe tener valor");
        }

        if (verificarSiEsAportesYAsociadoYaTieneUnaActiva(captacion)) {
            throw new ValidationException("El asociado ya tiene una cuenta de aportes activa");
        }

        Integer numeroCuenta = repo.getMaxNumeroCuentaByTipoCaptacion(captacion.getTipoCaptacion().getIdTipoCaptacion());

        captacion.setFechaApertura(LocalDate.now());
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setNumeroCuenta(numeroCuenta);
        captacion.setSaldo((double) 0);

        return repo.insert(captacion);
    }

    private boolean verificarSiEsAportesYAsociadoYaTieneUnaActiva(Captacion captacion) throws Exception {
        boolean captacionACrearEsAportes = captacion.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id);

        boolean tieneCaptacionAportesActiva = repo.getCuentaAportes(captacion.getAsociado().getIdAsociado()) != null;

        return captacionACrearEsAportes && tieneCaptacionAportesActiva;

    }


}
