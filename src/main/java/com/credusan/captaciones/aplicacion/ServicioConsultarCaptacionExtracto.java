package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServicioConsultarCaptacionExtracto {
    static final String DEBE_ESPECIFICAR_EL_IDENTIFICADOR_DE_LA_CAPTACION = "Debe especificar el identificador de la captación";

    private final PersistenciaCaptacionExtracto repo;

    public ServicioConsultarCaptacionExtracto(PersistenciaCaptacionExtracto repo) {
        this.repo = repo;
    }

    public Page<CaptacionExtracto> getAllByIdCaptacionAndFechas(Pageable pageable, ConsultaCaptacionExtractoDTO extractoDTO) throws Exception {
        if (extractoDTO.getIdCaptacion() == null) {
            throw new ValidationException(DEBE_ESPECIFICAR_EL_IDENTIFICADOR_DE_LA_CAPTACION);
        }

        extractoDTO.setNumeroPagina(pageable.getPageNumber());
        extractoDTO.setRegistrosPorPagina(pageable.getPageSize());

        List<CaptacionExtracto> lista = repo.getAllByIdCaptacionAndFechas(extractoDTO);

        long skip = (long) extractoDTO.getNumeroPagina() * extractoDTO.getRegistrosPorPagina();
        int limit = extractoDTO.getRegistrosPorPagina();

        List<CaptacionExtracto> listaRetorno = lista.stream()
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toList());

        return new PageImpl<>(listaRetorno, pageable, lista.size());

    }

}
