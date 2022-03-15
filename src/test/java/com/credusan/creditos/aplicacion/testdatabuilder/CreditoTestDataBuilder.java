package com.credusan.creditos.aplicacion.testdatabuilder;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.creditos.dominio.modelos.Credito;

public class CreditoTestDataBuilder {

    private Integer valor;
    private Integer plazo;
    private Asociado deudor;
    private Float tasaInteres;
    private Float tasaInteresMora;

    public CreditoTestDataBuilder() {
        this.plazo = 12;
        this.valor = 1_000_000;
        this.deudor = new Asociado();
        this.deudor.setIdAsociado(1);
        this.tasaInteres = 1F;
        this.tasaInteresMora = 2F;
    }

    public Credito build() {
        Credito credito = new Credito();

        credito.setPlazo(plazo);
        credito.setValor(valor);
        credito.setDeudor(deudor);
        credito.setTasaInteres(tasaInteres);
        credito.setTasaInteresMora(tasaInteresMora);

        return credito;
    }

    public CreditoTestDataBuilder conPlazo(Integer plazo) {
        this.plazo = plazo;
        return this;
    }

    public CreditoTestDataBuilder conValor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public CreditoTestDataBuilder conDeudor(Integer idAsociado) {
        this.deudor.setIdAsociado(idAsociado);
        return this;
    }

    public CreditoTestDataBuilder conTasaInteres(Float tasaInteres) {
        this.tasaInteres = tasaInteres;
        return this;
    }

    public CreditoTestDataBuilder conTasaInteresMora(Float tasaInteresMora) {
        this.tasaInteresMora = tasaInteresMora;
        return this;
    }
}
