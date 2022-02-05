package com.credusan.asociados.infraestructura.jdbc.mappers;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BeneficiarioRowMapper implements RowMapper<Beneficiario> {


    public static Map<String, Object> getParameters(Beneficiario beneficiario) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("beneid", beneficiario.getIdBeneficiario());
        parametros.put("asocid", beneficiario.getAsociado().getIdAsociado());
        parametros.put("benenombres", beneficiario.getNombres());
        parametros.put("beneprimerapellido", beneficiario.getPrimerApellido());
        parametros.put("benesegundoapellido", beneficiario.getSegundoApellido());
        parametros.put("beneporcentaje", beneficiario.getPorcentaje());

        return parametros;

    }

    @Override
    public Beneficiario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Beneficiario beneficiario = new Beneficiario();

        beneficiario.setIdBeneficiario(rs.getInt("beneid"));
        beneficiario.setAsociado(new Asociado());
        beneficiario.getAsociado().setIdAsociado(rs.getInt("asocid"));
        beneficiario.setNombres(rs.getString("benenombres"));
        beneficiario.setPrimerApellido(rs.getString("beneprimerapellido"));
        beneficiario.setSegundoApellido(rs.getString("benesegundoapellido"));
        beneficiario.setPorcentaje(rs.getInt("beneporcentaje"));

        return beneficiario;
    }
}
