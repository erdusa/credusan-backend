package com.credusan.captaciones.infraestructura.jdbc.daos;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import com.credusan.captaciones.infraestructura.jdbc.mappers.CaptacionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PersistenciaCaptacionJDBC implements PersistenciaCaptacion {

    private static final String SELECT_COMUN = "select * from captacion c" +
            " inner join tipocaptacion tc using(tipcapid) " +
            " inner join tipoestadocaptacion tec using (tiescaid)";
    private static final String ORDERBY_COMUN = " ORDER BY c.tipcapid, c.captnumerocuenta";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public PersistenciaCaptacionJDBC(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public Captacion insert(Captacion captacion) throws Exception {

        final Map<String, Object> parametros = CaptacionRowMapper.getParameters(captacion);

        int idCaptacion = simpleJdbcInsert.executeAndReturnKey(parametros).intValue();

        if (idCaptacion == 0) {

            throw new ValidationException("No se pudo insertar el registro");
        }

        return this.getById(idCaptacion);
    }

    @Override
    public Captacion update(Captacion captacion) throws Exception {

        String sql = " UPDATE public.captacion" +
                " SET tipcapid = ?, captnumerocuenta = ?, captfechaapertura = ?, captsaldo = ? , asocid = ?, tiescaid = ?" +
                " WHERE captid = ?";

        int rows = jdbcTemplate.update(sql,
                captacion.getTipoCaptacion().getIdTipoCaptacion(),
                captacion.getNumeroCuenta(),
                captacion.getFechaApertura(),
                captacion.getSaldo(),
                captacion.getAsociado().getIdAsociado(),
                captacion.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion(),
                captacion.getIdCaptacion()
        );

        if (rows != 1) {
            throw new ValidationException("No se pudo actualizar el registro");
        }

        return this.getById(captacion.getIdCaptacion());
    }

    @Override
    public Captacion crearCuentaAportes(Asociado asociado) throws Exception {
        Captacion captacion = new Captacion();
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(this.getMaxNumeroCuentaByTipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setSaldo((double) 0);
        captacion.setAsociado(asociado);
        return this.insert(captacion);
    }

    @Override
    public Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion) {
        String sql = " select max(captnumerocuenta) captnumerocuenta" +
                " from captacion" +
                " where tipcapid = ?";

        Integer numeroCuenta = jdbcTemplate.queryForObject(sql, Integer.class, idTipoCaptacion);

        return (Objects.requireNonNullElse(numeroCuenta, 0)) + 1;
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception {
        String sql = SELECT_COMUN + " where asocid = ?" + ORDERBY_COMUN;
        return jdbcTemplate.query(sql, new CaptacionRowMapper(), idAsociado);
    }

    @Override
    public Captacion getById(Integer idCaptacion) {
        String sql = SELECT_COMUN + " where captid = ?" + ORDERBY_COMUN;
        return jdbcTemplate.queryForObject(sql, new CaptacionRowMapper(), idCaptacion);
    }

    @Override
    public Captacion getCuentaAportes(Integer idAsociado) {
        String sql = SELECT_COMUN + " where asocid = ? and c.tiescaid = ? and c.tipcapid = ?";
        return jdbcTemplate.queryForObject(sql, new CaptacionRowMapper(), idAsociado, EnumTipoEstadoCaptacion.ACTIVA.id, EnumTipoCaptacion.APORTES.id);
    }
}
