package com.credusan.captaciones.infraestructura.jdbc.mappers;

import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CaptacionExtractoRowMapper implements RowMapper<CaptacionExtracto> {
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

    @Override
    public CaptacionExtracto mapRow(ResultSet rs, int rowNum) throws SQLException {

        CaptacionExtracto captacionExtracto = new CaptacionExtracto();
        captacionExtracto.setIdCaptacionExtracto(rs.getLong("capextid"));
        captacionExtracto.setCaptacion(new Captacion());
        captacionExtracto.getCaptacion().setIdCaptacion(rs.getInt("captid"));
        captacionExtracto.setFecha(rs.getDate("capextfecha").toLocalDate());
        captacionExtracto.setHora(rs.getTime("capexthora").toLocalTime());
        captacionExtracto.setValorDebito(rs.getDouble("capextvalordebito"));
        captacionExtracto.setValorCredito(rs.getDouble("capextvalorcredito"));
        return captacionExtracto;
    }
}
