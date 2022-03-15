package com.credusan.creditos.dominio.modelos;

import com.credusan.asociados.dominio.modelos.Asociado;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

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


}
