package com.credusan.asociados.infraestructura.jdbc.mappers;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AsociadoRowMapper implements RowMapper<Asociado> {
    public static Map<String, Object> getParameters(Asociado asociado) {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("asocid", asociado.getIdAsociado());
        parametros.put("tipdocid", asociado.getTipoDocumento().getIdTipoDocumento());
        parametros.put("asocactivo", asociado.getActivo());
        parametros.put("asocnumerodocumento", asociado.getNumeroDocumento());
        parametros.put("asocnombres", asociado.getNombres());
        parametros.put("asocprimerapellido", asociado.getPrimerApellido());
        parametros.put("asocsegundoapellido", asociado.getSegundoApellido());
        parametros.put("asocfechanacimiento", asociado.getFechaNacimiento());

        return parametros;

    }

    @Override
    public Asociado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Asociado asociado = new Asociado();

        asociado.setIdAsociado(rs.getInt("asocid"));
        asociado.setTipoDocumento(new TipoDocumento());
        asociado.getTipoDocumento().setIdTipoDocumento(rs.getInt("tipdocid"));
        asociado.getTipoDocumento().setAbreviatura(rs.getString("tipdocabreviatura"));
        asociado.getTipoDocumento().setDescripcion(rs.getString("tipdocdescripcion"));
        asociado.setActivo(rs.getBoolean("asocactivo"));
        asociado.setNumeroDocumento(rs.getString("asocnumerodocumento"));
        asociado.setNombres(rs.getString("asocnombres"));
        asociado.setPrimerApellido(rs.getString("asocprimerapellido"));
        asociado.setSegundoApellido(rs.getString("asocsegundoapellido"));
        asociado.setFechaNacimiento(rs.getDate("asocfechanacimiento").toLocalDate());

        return asociado;
    }
}
