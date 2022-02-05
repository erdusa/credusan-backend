package com.credusan.captaciones.infraestructura.jdbc.mappers;

import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CaptacionExtractoRowMapper implements RowMapper<CaptacionExtracto> {

    @Override
    public CaptacionExtracto mapRow(ResultSet rs, int rowNum) throws SQLException {

        CaptacionExtracto captacionExtracto = new CaptacionExtracto();

        captacionExtracto.setIdCaptacionExtracto(rs.getLong("capextid"));
        captacionExtracto.setFecha(rs.getDate("capextfecha").toLocalDate());
        captacionExtracto.setHora(rs.getTime("capexthora").toLocalTime());
        captacionExtracto.setValorDebito(rs.getDouble("capextvalordebito"));
        captacionExtracto.setValorCredito(rs.getDouble("capextvalorcredito"));

        captacionExtracto.setCaptacion(new Captacion());
        captacionExtracto.getCaptacion().setIdCaptacion(rs.getInt("captid"));
        captacionExtracto.getCaptacion().setNumeroCuenta(rs.getInt("captnumerocuenta"));
        captacionExtracto.getCaptacion().setTipoCaptacion(new TipoCaptacion());
        captacionExtracto.getCaptacion().getTipoCaptacion().setIdTipoCaptacion(rs.getInt("tipcapid"));

        return captacionExtracto;
    }

    public static Map<String, Object> getParameters(CaptacionExtracto captacionExtracto) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("capextid", captacionExtracto.getIdCaptacionExtracto());
        parametros.put("captid", captacionExtracto.getCaptacion().getIdCaptacion());
        parametros.put("capextfecha", captacionExtracto.getFecha());
        parametros.put("capexthora", captacionExtracto.getHora());
        parametros.put("capextvalordebito", captacionExtracto.getValorDebito());
        parametros.put("capextvalorcredito", captacionExtracto.getValorCredito());
        return parametros;
    }
}
