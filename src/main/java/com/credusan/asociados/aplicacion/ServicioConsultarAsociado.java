package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.dominio.puertos.PersistenciaBeneficiario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioConsultarAsociado {

    private final PersistenciaAsociado persistenciaAsociado;
    private final PersistenciaBeneficiario persistenciaBeneficiario;

    public ServicioConsultarAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaBeneficiario persistenciaBeneficiario) {
        this.persistenciaAsociado = persistenciaAsociado;
        this.persistenciaBeneficiario = persistenciaBeneficiario;
    }

    public Page<Asociado> getAll(Pageable pageable, boolean soloActivos) throws Exception {
        List<Asociado> lista = persistenciaAsociado.getAll(pageable, soloActivos);

        int skip = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        List<Asociado> listaRetorno = lista.stream()
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toList());

        cargarBeneficiarios(listaRetorno);

        return new PageImpl<>(listaRetorno, pageable, lista.size());
    }

    public Asociado getById(Integer idAsociado) throws Exception {
        Asociado asociado = persistenciaAsociado.getById(idAsociado);
        asociado.setBeneficiarios(persistenciaBeneficiario.getAllByIdAsociado(asociado.getIdAsociado()));
        return asociado;
    }

    public List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception {
        List<Asociado> lista = persistenciaAsociado.getAllByNameOrSurnames(nombres);
        cargarBeneficiarios(lista);
        return lista;
    }

    private void cargarBeneficiarios(List<Asociado> listaAsociados) throws Exception {
        for (Asociado asociado : listaAsociados) {
            asociado.setBeneficiarios(persistenciaBeneficiario.getAllByIdAsociado(asociado.getIdAsociado()));
        }
    }


}
