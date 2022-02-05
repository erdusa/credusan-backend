package com.credusan.asociados.infraestructura.jdbc.daos;

import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaBeneficiario;
import com.credusan.asociados.infraestructura.jdbc.mappers.BeneficiarioRowMapper;
import com.credusan.shared.exceptions.NotSavedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public class PersistenciaBeneficiarioJDBC implements PersistenciaBeneficiario {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public PersistenciaBeneficiarioJDBC(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Beneficiario insert(Beneficiario beneficiario) throws Exception {

        int idBeneficiario = simpleJdbcInsert.executeAndReturnKey(BeneficiarioRowMapper.getParameters(beneficiario)).intValue();

        if (idBeneficiario == 0) {
            throw new NotSavedException("beneficiario");
        }
        return getById(idBeneficiario);
    }

    @Override
    public void delete(Integer idBeneficiario) throws NotSavedException {
        String sql = "delete from beneficiario where beneid = ?";
        int filas = jdbcTemplate.update(sql, idBeneficiario);

        if (filas != 1) {
            throw new NotSavedException("beneficiario");
        }
    }

    private Beneficiario getById(Integer idBeneficiario) {
        String sql = "select * from beneficiario where beneid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeneficiarioRowMapper(), idBeneficiario);
        } catch (EmptyResultDataAccessException e) {
            return new Beneficiario();
        }
    }

    @Override
    public List<Beneficiario> getAllByIdAsociado(Integer idAsociado) throws Exception {
        String sql = "select * from beneficiario where asocid = ?";

        return jdbcTemplate.query(sql, new BeneficiarioRowMapper(), idAsociado);
    }
}
