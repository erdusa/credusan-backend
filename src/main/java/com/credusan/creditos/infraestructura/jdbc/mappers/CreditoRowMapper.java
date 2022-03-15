package com.credusan.creditos.infraestructura.jdbc.mappers;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.modelos.TipoEstadoCredito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreditoRowMapper implements RowMapper<Credito> {

    public static Map<String, Object> getParameters(Credito credito) {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("crednumero", credito.getNumero());
        parametros.put("credvalor", credito.getValor());
        parametros.put("credplazo", credito.getPlazo());
        parametros.put("credfechadesembolso", credito.getFechaDesembolso());
        parametros.put("credfechaproximopago", credito.getFechaProximoPago());
        parametros.put("credtasainteres", credito.getTasaInteres());
        parametros.put("credtasainteresmora", credito.getTasaInteresMora());
        parametros.put("credvalorcuota", credito.getValorCuota());
        parametros.put("credvalormoradia", credito.getValorMoraDia());
        parametros.put("tiescrid", credito.getTipoEstadoCredito().getIdTipoEstadoCredito());
        parametros.put("asocid", credito.getDeudor().getIdAsociado());

        return parametros;
    }

    @Override
    public Credito mapRow(ResultSet rs, int rowNum) throws SQLException {
        Credito credito = new Credito();

        credito.setIdCredito(rs.getInt("credid"));
        credito.setNumero(rs.getInt("crednumero"));
        credito.setValor(rs.getInt("credvalor"));
        credito.setPlazo(rs.getInt("credplazo"));
        credito.setFechaDesembolso(rs.getDate("credfechadesembolso").toLocalDate());
        credito.setFechaProximoPago(rs.getDate("credfechaproximopago").toLocalDate());
        credito.setTasaInteres(rs.getFloat("credtasainteres"));
        credito.setTasaInteresMora(rs.getFloat("credtasainteresmora"));
        credito.setValorCuota(rs.getInt("credvalorcuota"));
        credito.setValorMoraDia(rs.getFloat("credvalormoradia"));
        credito.setTipoEstadoCredito(new TipoEstadoCredito(rs.getShort("tiescrid"), rs.getString("tiescrnombre")));
        credito.setDeudor(new Asociado());
        credito.getDeudor().setIdAsociado(rs.getInt("asocid"));

        return credito;
    }
}
