package com.credusan.asociados.infraestructura.jdbc.daos;

import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.asociados.dominio.puertos.PersistenciaTipoDocumento;
import com.credusan.asociados.infraestructura.jdbc.mappers.TipoDocumentoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersistenciaTipoDocumentoJDBC implements PersistenciaTipoDocumento {

    private final JdbcTemplate jdbcTemplate;

    public PersistenciaTipoDocumentoJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TipoDocumento> getAll() throws Exception {
        return jdbcTemplate.query("select * from tipodocumento", new TipoDocumentoRowMapper());
    }
}
