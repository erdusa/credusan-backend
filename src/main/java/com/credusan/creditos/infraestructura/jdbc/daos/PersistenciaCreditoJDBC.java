package com.credusan.creditos.infraestructura.jdbc.daos;

import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.infraestructura.jdbc.mappers.CreditoRowMapper;
import com.credusan.shared.exceptions.NotSavedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersistenciaCreditoJDBC implements PersistenciaCredito {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public PersistenciaCreditoJDBC(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public Credito insert(Credito credito) {
        Map<String, Object> parametros = CreditoRowMapper.getParameters(credito);

        int idCredito = this.simpleJdbcInsert.executeAndReturnKey(parametros).intValue();

        if (idCredito == 0) {
            throw new NotSavedException("No se pudo insertar el registro");
        }

        return this.getById(idCredito);
    }

    @Override
    public List<Credito> getAllByIdAsociado(Integer idAsociado) {

        String sql = "SELECT * " +
                " FROM creditos.credito" +
                " INNER JOIN creditos.tipoestadocredito USING(tiescrid)" +
                " WHERE asocid = ?" +
                " ORDER BY crednumero";

        return this.jdbcTemplate.query(sql, new CreditoRowMapper(), idAsociado);
    }

    @Override
    public Integer getNextConsecutivo() {

        LocalDate fechaInicioAnioActual = LocalDate.of(LocalDate.now().getYear(), 1, 1);

        String sql = "SELECT max(crednumero) FROM creditos.credito" +
                " WHERE credfechadesembolso >= ? ";

        Integer numeroCredito = this.jdbcTemplate.queryForObject(sql, Integer.class, fechaInicioAnioActual);

        if (numeroCredito == null) {

            numeroCredito = Integer.valueOf(LocalDate.now().getYear() + "0000");
        }

        return numeroCredito + 1;
    }

    private Credito getById(int idCredito) {
        String sql = "SELECT * FROM creditos.credito" +
                " INNER JOIN creditos.tipoestadocredito USING(tiescrid)" +
                " WHERE credid = ?";

        return this.jdbcTemplate.queryForObject(sql, new CreditoRowMapper(), idCredito);
    }
}
