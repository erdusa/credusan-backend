package com.credusan.captaciones.infraestructura.jdbc.daos;

import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import com.credusan.captaciones.infraestructura.jdbc.mappers.CaptacionExtractoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersistenciaCaptacionExtractoJDBC implements PersistenciaCaptacionExtracto {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public PersistenciaCaptacionExtractoJDBC(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public CaptacionExtracto insert(CaptacionExtracto captacionExtracto) {

        Map<String, Object> parametros = CaptacionExtractoRowMapper.getParameters(captacionExtracto);

        long idCaptacionExtracto = simpleJdbcInsert.executeAndReturnKey(parametros).longValue();

        if (idCaptacionExtracto == 0) {
            throw new ValidationException("No se pudo insertar el registro");
        }

        return getById(idCaptacionExtracto);
    }

    private CaptacionExtracto getById(long idCaptacionExtracto) {

        String sql = "select * from captacionextracto where capextid = ?";

        return jdbcTemplate.queryForObject(sql, new CaptacionExtractoRowMapper(), idCaptacionExtracto);

    }

    @Override
    public List<CaptacionExtracto> getAllByIdCaptacionAndFechas(ConsultaCaptacionExtractoDTO extractoDTO) {
        String sql = "select * from captacionextracto " +
                " where captid = ?" +
                " and capextfecha between ? and ?" +
                " order by capextfecha desc, capexthora desc";

        if (extractoDTO.getFechaInicial() == null) {
            extractoDTO.setFechaInicial(LocalDate.of(1, 1, 1));
        }
        if (extractoDTO.getFechaFinal() == null) {
            extractoDTO.setFechaFinal(LocalDate.now());
        }

        return jdbcTemplate.query(sql, new CaptacionExtractoRowMapper(),
                extractoDTO.getIdCaptacion(),
                extractoDTO.getFechaInicial(),
                extractoDTO.getFechaFinal());
    }
}
