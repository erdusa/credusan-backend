package com.credusan.captaciones.dominio.modelos;

import com.credusan.asociados.dominio.modelos.Asociado;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Captacion {
    private static final int TAMANIO_NUMERO_CUENTA_COMPLETO = 5;

    private Integer idCaptacion;
    private TipoCaptacion tipoCaptacion;
    private Integer numeroCuenta;
    private Asociado asociado;
    private TipoEstadoCaptacion tipoEstadoCaptacion;
    private LocalDate fechaApertura;
    private Double saldo;

    public Captacion(TipoCaptacion tipoCaptacion, Integer numeroCuenta, Asociado asociado, TipoEstadoCaptacion tipoEstadoCaptacion, LocalDate fechaApertura, Double saldo) {
        this.tipoCaptacion = tipoCaptacion;
        this.numeroCuenta = numeroCuenta;
        this.asociado = asociado;
        this.tipoEstadoCaptacion = tipoEstadoCaptacion;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
    }

    public String getNumeroCuentaCompleto() {
        return this.tipoCaptacion.getIdTipoCaptacion().toString()
                + StringUtils.leftPad(numeroCuenta.toString(), TAMANIO_NUMERO_CUENTA_COMPLETO, "0");
    }

    @Override
    public String toString() {
        return "Captacion{" +
                "tipoCaptacion=" + (tipoCaptacion != null ? tipoCaptacion.getIdTipoCaptacion() : 0) +
                ", numeroCuenta=" + numeroCuenta +
                ", asociado=" + (asociado != null ? asociado.getIdAsociado() : 0) +
                ", tipoEstadoCaptacion=" + (tipoEstadoCaptacion != null ? tipoEstadoCaptacion.getIdTipoEstadoCaptacion() : 0) +
                ", fechaApertura=" + fechaApertura +
                ", saldo=" + saldo +
                '}';
    }
}
