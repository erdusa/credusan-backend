package com.credusan.captaciones.infraestructura.jdbc.mappers;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoCaptacionRowMapper implements RowMapper<TipoCaptacion> {

    @Override
    public TipoCaptacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoCaptacion tipoCaptacion = new TipoCaptacion();
        tipoCaptacion.setIdTipoCaptacion(rs.getInt("tipcapid"));
        tipoCaptacion.setNombre(rs.getString("tipcapnombre"));
        return tipoCaptacion;
    }
}
