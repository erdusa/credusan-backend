package com.credusan.creditos.dominio.modelos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditoLiquidacion {

    private Credito credito;
    private Integer numeroCuota;
    private LocalDate fechaPago;
    private LocalDate fechaPagada;

}
