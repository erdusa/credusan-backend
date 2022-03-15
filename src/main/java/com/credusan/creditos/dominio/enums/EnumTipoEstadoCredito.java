package com.credusan.creditos.dominio.enums;

public enum EnumTipoEstadoCredito {
    VIGENTE((short) 1),
    SALDADO((short) 2);

    public final short id;

    EnumTipoEstadoCredito(short id) {
        this.id = id;
    }
}
