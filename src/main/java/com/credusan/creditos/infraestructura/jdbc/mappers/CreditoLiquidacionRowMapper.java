package com.credusan.creditos.infraestructura.jdbc.mappers;

import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.CreditoLiquidacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditoLiquidacionRowMapper implements RowMapper<CreditoLiquidacion> {

    @Override
    public CreditoLiquidacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        CreditoLiquidacion creditoLiquidacion = new CreditoLiquidacion();
        creditoLiquidacion.setCredito(new Credito());
        creditoLiquidacion.getCredito().setIdCredito(rs.getInt("credid"));
        creditoLiquidacion.setNumeroCuota(rs.getInt("creliqnumerocuota"));
        creditoLiquidacion.setFechaPago(rs.getDate("creliqfechapago").toLocalDate());
        Date fechaPagada = rs.getDate("creliqfechapagada");
        creditoLiquidacion.setFechaPagada(fechaPagada != null ? fechaPagada.toLocalDate() : null);

        return creditoLiquidacion;
    }

}
