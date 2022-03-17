package com.credusan.creditos.dominio.modelos;

import com.credusan.asociados.dominio.modelos.Asociado;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(exclude = "idCredito")
public class Credito {

    private Integer idCredito;
    private Integer numero;
    private Integer valor;
    private Integer plazo;
    private LocalDate fechaDesembolso;
    private LocalDate fechaProximoPago;
    private Float tasaInteres;
    private Float tasaInteresMora;
    private Integer valorCuota;
    private Float valorMoraDia;
    private Asociado deudor;
    private TipoEstadoCredito tipoEstadoCredito;
    private List<CreditoLiquidacion> listaCreditoLiquidacion;
    private Integer saldo;
    private Short diasMora;

    public Integer getSaldo() {
        //TODO: cuando se implemente el extracto se debe restar el sum del valor a capital
        return valor;
    }

    public Short getDiasMora() {
        int diasMoraCalculado = 0;
        if (fechaProximoPago != null) {
            diasMoraCalculado = fechaProximoPago.until(LocalDate.now()).getDays();
        }
        return (short) Math.max(diasMoraCalculado, 0);
    }
}
