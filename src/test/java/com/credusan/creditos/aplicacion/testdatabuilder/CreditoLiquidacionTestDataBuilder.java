package com.credusan.creditos.aplicacion.testdatabuilder;

import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.CreditoLiquidacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditoLiquidacionTestDataBuilder {

    private Credito credito;
    private Integer numeroCuota;
    private LocalDate fechaPago;
    private LocalDate fechaPagada;

    public CreditoLiquidacionTestDataBuilder() {
        this.credito = new Credito();
        this.credito.setIdCredito(1);
        this.numeroCuota = 1;
        this.fechaPago = LocalDate.now();
    }

    public CreditoLiquidacion build() {
        CreditoLiquidacion creditoLiquidacion = new CreditoLiquidacion();
        creditoLiquidacion.setCredito(this.credito);
        creditoLiquidacion.setNumeroCuota(this.numeroCuota);
        creditoLiquidacion.setFechaPago(this.fechaPago);
        creditoLiquidacion.setFechaPagada(this.fechaPagada);

        return creditoLiquidacion;
    }


    public List<CreditoLiquidacion> buildList(int numeroCuotas) {
        List<CreditoLiquidacion> lista = new ArrayList<>();

        CreditoLiquidacion creditoLiquidacion;

        for (int i = 0; i < numeroCuotas; i++) {
            creditoLiquidacion = new CreditoLiquidacion();
            creditoLiquidacion.setCredito(this.credito);
            creditoLiquidacion.setNumeroCuota(this.numeroCuota + i);
            creditoLiquidacion.setFechaPago(this.fechaPago.plusMonths(i));

            lista.add(creditoLiquidacion);
        }

        return lista;
    }

    public CreditoLiquidacionTestDataBuilder conIdCredito(Integer idCredito) {
        this.credito = new Credito();
        this.credito.setIdCredito(idCredito);
        return this;
    }

    public CreditoLiquidacionTestDataBuilder conNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
        return this;
    }

    public CreditoLiquidacionTestDataBuilder conFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
        return this;
    }

    public CreditoLiquidacionTestDataBuilder conFechaPagada(LocalDate fechaPagada) {
        this.fechaPagada = fechaPagada;
        return this;
    }
}
