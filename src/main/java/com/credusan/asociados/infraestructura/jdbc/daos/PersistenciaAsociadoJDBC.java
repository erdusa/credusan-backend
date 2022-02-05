package com.credusan.asociados.infraestructura.jdbc.daos;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.infraestructura.jdbc.mappers.AsociadoRowMapper;
import com.credusan.shared.exceptions.NotSavedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public class PersistenciaAsociadoJDBC implements PersistenciaAsociado {

    private static final String SELECT_CON_TIPODOCUMENTO = "SELECT *  FROM asociado INNER JOIN tipodocumento using (tipdocid) ";
    private static final String ORDER_BY_NOMBRE_APELLIDOS = " ORDER BY asocnombres, asocprimerApellido, asocsegundoApellido";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public PersistenciaAsociadoJDBC(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public Asociado insert(Asociado asociado) throws Exception {
        int idAsociado = simpleJdbcInsert.executeAndReturnKey(AsociadoRowMapper.getParameters(asociado)).intValue();

        if (idAsociado == 0) {
            throw new NotSavedException("asociado");
        }
        return getById(idAsociado);
    }

    @Override
    public Asociado update(Asociado asociado) throws Exception {

        String sql = "UPDATE public.asociado" +
                " SET tipdocid = ?,  " +
                "   asocnumerodocumento = ?, " +
                "   asocactivo = ?, " +
                "   asocfechanacimiento = ?, " +
                "   asocnombres = ?, " +
                "   asocprimerapellido = ?, " +
                "   asocsegundoapellido = ?" +
                " WHERE asocid = ?";

        int filas = jdbcTemplate.update(sql,
                asociado.getTipoDocumento().getIdTipoDocumento(),
                asociado.getNumeroDocumento(),
                asociado.getActivo(),
                asociado.getFechaNacimiento(),
                asociado.getNombres(),
                asociado.getPrimerApellido(),
                asociado.getSegundoApellido(),
                asociado.getIdAsociado());

        if (filas != 1) {
            throw new NotSavedException("asociado");
        }

        return getById(asociado.getIdAsociado());
    }

    @Override
    public Asociado getById(Integer idAsociado) throws Exception {
        String sql = SELECT_CON_TIPODOCUMENTO + " where asocid = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new AsociadoRowMapper(), idAsociado);
        } catch (EmptyResultDataAccessException e) {
            return new Asociado();
        }
    }

    @Override
    public List<Asociado> getAll(Pageable page, boolean soloActivos) throws Exception {
        String sql = SELECT_CON_TIPODOCUMENTO;

        Object[] parametros = {};

        if (soloActivos) {
            sql += " where asocactivo = ?";
            parametros = new Object[]{true};
        }

        sql += ORDER_BY_NOMBRE_APELLIDOS;

        return jdbcTemplate.query(sql, new AsociadoRowMapper(), parametros);

    }

    @Override
    public List<Asociado> getAllByNameOrSurnames(String nombres) {
        String sql = SELECT_CON_TIPODOCUMENTO +
                " WHERE asocnombres||asocprimerApellido||coalesce(asocsegundoApellido,'') ilike REPLACE('%'||?||'%', ' ','%') " +
                ORDER_BY_NOMBRE_APELLIDOS;

        return jdbcTemplate.query(sql, new AsociadoRowMapper(), nombres);
    }
}
