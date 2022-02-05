package com.credusan.captaciones.dominio.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ConsultaCaptacionExtractoDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Integer idCaptacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicial;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinal;
    private int numeroPagina;
    private int registrosPorPagina;

}
