package com.credusan.creditos.aplicacion.testdatabuilder;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.creditos.dominio.enums.EnumTipoEstadoCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.TipoEstadoCredito;

public class CreditoTestDataBuilder {

    private Integer idCredito;
    private Integer valor;
    private Integer plazo;
    private Asociado deudor;
    private Float tasaInteres;
    private Float tasaInteresMora;
    private Short idTipoEstadoCredito;

    public CreditoTestDataBuilder() {
        this.plazo = 12;
        this.valor = 1_000_000;
        this.deudor = new Asociado();
        this.deudor.setIdAsociado(1);
        this.tasaInteres = 1F;
        this.tasaInteresMora = 2F;
        this.idTipoEstadoCredito = EnumTipoEstadoCredito.VIGENTE.id;
    }

    public Credito build() {
        Credito credito = new Credito();

        credito.setIdCredito(idCredito);
        credito.setPlazo(plazo);
        credito.setValor(valor);
        credito.setDeudor(deudor);
        credito.setTasaInteres(tasaInteres);
        credito.setTasaInteresMora(tasaInteresMora);
        credito.setTipoEstadoCredito(new TipoEstadoCredito());
        credito.getTipoEstadoCredito().setIdTipoEstadoCredito(idTipoEstadoCredito);

        return credito;
    }

    public CreditoTestDataBuilder conIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
        return this;
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

    public CreditoTestDataBuilder conIdTipoEstadoCredito(Short idTipoEstadoCredito) {
        this.idTipoEstadoCredito = idTipoEstadoCredito;
        return this;
    }
}
