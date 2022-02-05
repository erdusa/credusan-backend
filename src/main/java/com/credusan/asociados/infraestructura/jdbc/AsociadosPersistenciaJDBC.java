package com.credusan.asociados.infraestructura.jdbc;

import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.dominio.puertos.PersistenciaBeneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaTipoDocumento;
import com.credusan.asociados.infraestructura.jdbc.daos.PersistenciaAsociadoJDBC;
import com.credusan.asociados.infraestructura.jdbc.daos.PersistenciaBeneficiarioJDBC;
import com.credusan.asociados.infraestructura.jdbc.daos.PersistenciaTipoDocumentoJDBC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class AsociadosPersistenciaJDBC {

    @Bean
    public PersistenciaTipoDocumento getTipoDocumentoBean(DataSource dataSource) {
        return new PersistenciaTipoDocumentoJDBC(new JdbcTemplate(dataSource));
    }

    @Bean
    public PersistenciaAsociado getAsociadoBean(DataSource dataSource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("asociado")
                .usingGeneratedKeyColumns("asocid");
        return new PersistenciaAsociadoJDBC(new JdbcTemplate(dataSource), simpleJdbcInsert);
    }

    @Bean
    public PersistenciaBeneficiario getBeneficiarioBean(DataSource dataSource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("beneficiario")
                .usingGeneratedKeyColumns("beneid");
        return new PersistenciaBeneficiarioJDBC(new JdbcTemplate(dataSource), simpleJdbcInsert);
    }
}
