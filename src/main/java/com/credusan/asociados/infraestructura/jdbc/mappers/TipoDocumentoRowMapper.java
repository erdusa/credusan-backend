package com.credusan.asociados.infraestructura.jdbc.mappers;

import com.credusan.asociados.dominio.modelos.TipoDocumento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoDocumentoRowMapper implements RowMapper<TipoDocumento> {
    @Override
    public TipoDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setIdTipoDocumento(rs.getInt("tipdocid"));
        tipoDocumento.setDescripcion(rs.getString("tipdocdescripcion"));
        tipoDocumento.setAbreviatura(rs.getString("tipdocabreviatura"));
        return tipoDocumento;
    }
}
