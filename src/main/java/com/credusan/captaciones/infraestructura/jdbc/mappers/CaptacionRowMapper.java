package com.credusan.captaciones.infraestructura.jdbc.mappers;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CaptacionRowMapper implements RowMapper<Captacion> {

    public static Map<String, Object> getParameters(Captacion captacion) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("captfechaapertura", captacion.getFechaApertura());
        parametros.put("captnumerocuenta", captacion.getNumeroCuenta());
        parametros.put("captsaldo", captacion.getSaldo());
        parametros.put("asocid", captacion.getAsociado().getIdAsociado());
        parametros.put("tipcapid", captacion.getTipoCaptacion().getIdTipoCaptacion());
        parametros.put("tiescaid", captacion.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion());
        return parametros;
    }

    @Override
    public Captacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Captacion captacion = new Captacion();

        captacion.setIdCaptacion(rs.getInt("captid"));
        captacion.setFechaApertura(rs.getDate("captfechaapertura").toLocalDate());
        captacion.setNumeroCuenta(rs.getInt("captnumerocuenta"));
        captacion.setSaldo(rs.getDouble("captsaldo"));

        captacion.setAsociado(new Asociado());
        captacion.getAsociado().setIdAsociado(rs.getInt("asocid"));

        captacion.setTipoCaptacion(new TipoCaptacion());
        captacion.getTipoCaptacion().setIdTipoCaptacion(rs.getInt("tipcapid"));
        captacion.getTipoCaptacion().setNombre(rs.getString("tipcapnombre"));

        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion());
        captacion.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(rs.getInt("tiescaid"));
        captacion.getTipoEstadoCaptacion().setNombre(rs.getString("tiescanombre"));
        return captacion;
    }

}
