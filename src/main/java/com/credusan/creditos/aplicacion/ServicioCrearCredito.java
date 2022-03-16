package com.credusan.creditos.aplicacion;

import com.credusan.creditos.dominio.enums.EnumTipoEstadoCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.CreditoLiquidacion;
import com.credusan.creditos.dominio.modelos.TipoEstadoCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import com.credusan.creditos.shared.ValorCuotaCredito;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class ServicioCrearCredito {

    public static final String NO_DESEMBOLSOS_DESPUES_DEL_28 = "No se pueden hacer desembolsos de créditos después del 28 del mes";
    public static final String FALTA_EL_PLAZO = "No se especificó el plazo del crédito";
    public static final String FALTA_EL_VALOR = "No se especificó el valor del crédito";
    public static final String FALTA_EL_DEUDOR = "No se especificó el deudor del crédito";
    public static final String FALTA_TASA_INTERES = "La tasa de interés debe ser mayor o igual que cero";
    public static final String TASA_MORA_NEGATIVA = "La tasa de mora no puede ser negativa";

    private final PersistenciaCredito persistenciaCredito;
    private final PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion;

    public ServicioCrearCredito(PersistenciaCredito persistenciaCredito, PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion) {
        this.persistenciaCredito = persistenciaCredito;
        this.persistenciaCreditoLiquidacion = persistenciaCreditoLiquidacion;
    }

    @Transactional(rollbackFor = Exception.class)
    public Credito ejecutar(Credito credito) {

        this.validarPrecondiciones(credito);

        LocalDate hoy = LocalDate.now();
        int numero = persistenciaCredito.getNextConsecutivo();
        int valorCuota = ValorCuotaCredito.calcular(credito.getValor(), credito.getPlazo(), credito.getTasaInteres());
        float valorMoraDia = this.calcularValorMoraDia(valorCuota, credito.getTasaInteresMora());

        credito.setNumero(numero);
        credito.setFechaDesembolso(hoy);
        credito.setFechaProximoPago(hoy.plusMonths(1));
        credito.setTipoEstadoCredito(new TipoEstadoCredito(EnumTipoEstadoCredito.VIGENTE.id));
        credito.setValorCuota(valorCuota);
        credito.setValorMoraDia(valorMoraDia);

        Credito creditoCreado = persistenciaCredito.insert(credito);

        insertarLiquidacion(creditoCreado);

        return creditoCreado;
    }

    private void validarPrecondiciones(Credito credito) {

        if (LocalDate.now().getDayOfMonth() > 28) {
            throw new ValidationException(NO_DESEMBOLSOS_DESPUES_DEL_28);
        }

        if (Objects.requireNonNullElse(credito.getPlazo(), 0) <= 0) {
            throw new ValidationException(FALTA_EL_PLAZO);
        }

        if (Objects.requireNonNullElse(credito.getValor(), 0) <= 0) {
            throw new ValidationException(FALTA_EL_VALOR);
        }

        if (credito.getTasaInteres() == null || credito.getTasaInteres() < 0) {
            throw new ValidationException(FALTA_TASA_INTERES);
        }

        if (Objects.requireNonNullElse(credito.getTasaInteresMora(), 0F) < 0) {
            throw new ValidationException(TASA_MORA_NEGATIVA);
        }

        if (credito.getDeudor() == null || credito.getDeudor().getIdAsociado() == null) {
            throw new ValidationException(FALTA_EL_DEUDOR);
        }
    }

    private float calcularValorMoraDia(int valorCuota, float tasaInteresMora) {
        float valorMoraPorDia = valorCuota * (tasaInteresMora / 100) / 30;
        valorMoraPorDia = Math.round(valorMoraPorDia * 100) / 100F;
        return valorMoraPorDia;
    }

    private void insertarLiquidacion(Credito credito) {

        CreditoLiquidacion creditoLiquidacion = new CreditoLiquidacion();
        creditoLiquidacion.setCredito(credito);

        for (int i = 1; i <= credito.getPlazo(); i++) {
            creditoLiquidacion.setNumeroCuota(i);
            creditoLiquidacion.setFechaPago(LocalDate.now().plusMonths(i));
            persistenciaCreditoLiquidacion.insert(creditoLiquidacion);
        }
    }

}
