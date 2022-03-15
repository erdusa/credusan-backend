package com.credusan.creditos.infraestructura.jdbc;

import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import com.credusan.creditos.infraestructura.jdbc.daos.PersistenciaCreditoJDBC;
import com.credusan.creditos.infraestructura.jdbc.daos.PersistenciaCreditoLiquidacionJDBC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class CreditosPersistenciaConfig {

    @Bean
    public PersistenciaCredito getPersistenciaCredito(DataSource dataSource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName("creditos")
                .withTableName("credito")
                .usingGeneratedKeyColumns("credid");
        return new PersistenciaCreditoJDBC(new JdbcTemplate(dataSource), simpleJdbcInsert);
    }

    @Bean
    public PersistenciaCreditoLiquidacion getPersistenciaCreditoLiquidacion(DataSource dataSource) {
        return new PersistenciaCreditoLiquidacionJDBC(new JdbcTemplate(dataSource));
    }
}
