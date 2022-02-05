package com.credusan.captaciones.aplicacion;

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
public class ServicioCrearCaptacionExtracto {

    static final String EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor débito no puede ser menor que cero";
    static final String EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor crédito no puede ser menor que cero";
    static final String EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO = "El valor débito o crédito debe ser mayor que cero";
    static final String NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO = "No puede asignar valor débito y crédito al mismo tiempo";
    static final String EL_SALDO_NO_PUEDE_QUEDAR_NEGATIVO = "El saldo no puede quedar negativo";

    private final PersistenciaCaptacionExtracto repo;
    private final PersistenciaCaptacion persistenciaCaptacion;

    public ServicioCrearCaptacionExtracto(PersistenciaCaptacionExtracto repo, PersistenciaCaptacion persistenciaCaptacion) {
        this.repo = repo;
        this.persistenciaCaptacion = persistenciaCaptacion;
    }

    @Transactional(rollbackFor = Exception.class)
    public CaptacionExtracto create(CaptacionExtracto captacionExtracto) throws Exception {

        if (captacionExtracto.getValorDebito() > 0 && captacionExtracto.getValorCredito() > 0) {
            throw new ValidationException(NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO);
        }

        if (captacionExtracto.getValorDebito() < 0) {
            throw new ValidationException(EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO);
        }

        if (captacionExtracto.getValorCredito() < 0) {
            throw new ValidationException(EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO);
        }

        if (captacionExtracto.getValorDebito() <= 0 && captacionExtracto.getValorCredito() <= 0) {
            throw new ValidationException(EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO);
        }

        double saldoSumar = captacionExtracto.getValorDebito() - captacionExtracto.getValorCredito();
        Captacion captacion = this.actualizarSaldoCaptacion(captacionExtracto.getCaptacion().getIdCaptacion(), saldoSumar);

        captacionExtracto.setCaptacion(captacion);
        captacionExtracto.setFecha(LocalDate.now());
        captacionExtracto.setHora(LocalTime.now());

        return repo.insert(captacionExtracto);

    }

    private Captacion actualizarSaldoCaptacion(int idCaptacion, double saldoSumar) throws Exception {

        Captacion captacion = persistenciaCaptacion.getById(idCaptacion);

        captacion.setSaldo(captacion.getSaldo() + saldoSumar);

        if (captacion.getSaldo() < 0) {
            throw new ValidationException(EL_SALDO_NO_PUEDE_QUEDAR_NEGATIVO);
        }
        return persistenciaCaptacion.update(captacion);
    }
}
