package com.credusan.creditos.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEstadoCredito {
    private Short idTipoEstadoCredito;
    private String nombre;

    public TipoEstadoCredito(short idTipoEstadoCredito) {
        this.idTipoEstadoCredito = idTipoEstadoCredito;
    }
}
