package com.credusan.creditos.infraestructura.jdbc.daos;

import com.credusan.creditos.dominio.modelos.CreditoLiquidacion;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import com.credusan.creditos.infraestructura.jdbc.mappers.CreditoLiquidacionRowMapper;
import com.credusan.shared.exceptions.NotSavedException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersistenciaCreditoLiquidacionJDBC implements PersistenciaCreditoLiquidacion {

    private final JdbcTemplate jdbcTemplate;

    public PersistenciaCreditoLiquidacionJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(CreditoLiquidacion creditoLiquidacion) {
        String sql = "INSERT INTO creditos.creditoliquidacion" +
                " (credid, creliqnumerocuota, creliqfechapago)" +
                " VALUES(?, ?, ?)";
        int filasInsertadas = this.jdbcTemplate.update(sql,
                creditoLiquidacion.getCredito().getIdCredito(),
                creditoLiquidacion.getNumeroCuota(),
                creditoLiquidacion.getFechaPago()
        );

        if (filasInsertadas != 1) {
            throw new NotSavedException("No se pudo insertar el registro");
        }
    }

    @Override
    public List<CreditoLiquidacion> getAllByIdCredito(Integer idCredito) {
        String sql = "SELECT credid, creliqnumerocuota, creliqfechapago, creliqfechapagada" +
                " FROM creditos.creditoliquidacion" +
                " WHERE credid = ?" +
                " ORDER BY creliqnumerocuota";
        return jdbcTemplate.query(sql, new CreditoLiquidacionRowMapper(), idCredito);
    }
}
