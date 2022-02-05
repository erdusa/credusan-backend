package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ServicioSaldarCaptacion {

    static final String NO_SE_PUEDE_SALDAR_LA_CUENTA_DE_APORTES = "No se puede saldar la cuenta de aportes";
    static final String LA_CAPTACION_YA_ESTA_SALDADA = "La captación ya está saldada";

    private final PersistenciaCaptacion repo;
    private final PersistenciaCaptacionExtracto persistenciaCaptacionExtracto;

    public ServicioSaldarCaptacion(PersistenciaCaptacion repo, PersistenciaCaptacionExtracto persistenciaCaptacionExtracto) {
        this.repo = repo;
        this.persistenciaCaptacionExtracto = persistenciaCaptacionExtracto;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saldar(Integer idCaptacion) throws Exception {
        Captacion captacion = repo.getById(idCaptacion);
        boolean esAportes = captacion.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id);
        if (esAportes) {
            throw new ValidationException(NO_SE_PUEDE_SALDAR_LA_CUENTA_DE_APORTES);
        }

        boolean estaSaldada = captacion.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.SALDADA.id);
        if (estaSaldada) {
            throw new ValidationException(LA_CAPTACION_YA_ESTA_SALDADA);
        }

        this.retirarSaldoTotal(captacion);

        captacion.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id);
        captacion.setSaldo(0D);
        Captacion captacionSaldada = repo.update(captacion);

        return captacionSaldada.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.SALDADA.id);
    }

    private void retirarSaldoTotal(Captacion captacion) {

        if (captacion.getSaldo() == 0) {
            return;
        }

        CaptacionExtracto captacionExtracto = new CaptacionExtracto();
        captacionExtracto.setCaptacion(captacion);
        captacionExtracto.setFecha(LocalDate.now());
        captacionExtracto.setHora(LocalTime.now());
        captacionExtracto.setValorCredito(captacion.getSaldo());
        captacionExtracto.setValorDebito(0D);

        persistenciaCaptacionExtracto.insert(captacionExtracto);

    }
}
