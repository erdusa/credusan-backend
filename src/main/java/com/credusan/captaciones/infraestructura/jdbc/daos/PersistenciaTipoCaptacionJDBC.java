package com.credusan.captaciones.infraestructura.jdbc.daos;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaTipoCaptacion;
import com.credusan.captaciones.infraestructura.jdbc.mappers.TipoCaptacionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersistenciaTipoCaptacionJDBC implements PersistenciaTipoCaptacion {

    private final JdbcTemplate jdbcTemplate;

    public PersistenciaTipoCaptacionJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TipoCaptacion> getAll() {
        String sql = "select * from tipocaptacion";
        return this.jdbcTemplate.query(sql, new TipoCaptacionRowMapper());
    }
}
