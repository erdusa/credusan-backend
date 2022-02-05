package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.dominio.puertos.PersistenciaBeneficiario;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioActualizarAsociado {

    static final String LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100 = "Los porcentajes asignados a los beneficiarios deben sumar 100";
    static final String NO_EXISTE_EL_ASOCIADO = "No existe el asociado";
    static final String NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO = "No puede inactivar el asociado, debería retirarlo";

    private final PersistenciaAsociado persistenciaAsociado;
    private final PersistenciaBeneficiario persistenciaBeneficiario;

    public ServicioActualizarAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaBeneficiario persistenciaBeneficiario) {
        this.persistenciaAsociado = persistenciaAsociado;
        this.persistenciaBeneficiario = persistenciaBeneficiario;
    }

    @Transactional(rollbackFor = Exception.class)
    public Asociado update(Integer idAsociado, Asociado asociado) throws Exception {

        Asociado asociadoU = persistenciaAsociado.getById(idAsociado);

        if (asociadoU.getIdAsociado() == null) {
            throw new ValidationException(NO_EXISTE_EL_ASOCIADO);
        }

        if (verificarSiElPorcentajeBeneficiariosEstaErrado(asociado)) {
            throw new ValidationException(LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100);
        }

        boolean esInactivarAsociado = asociadoU.getActivo() && !asociado.getActivo();
        if (esInactivarAsociado) {
            throw new ValidationException(NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO);
        }

        asociadoU = persistenciaAsociado.update(asociado);

        asociadoU.setBeneficiarios(asociado.getBeneficiarios());
        procesarBeneficiarios(asociadoU);

        asociadoU = persistenciaAsociado.update(asociado);
        asociadoU.setBeneficiarios(persistenciaBeneficiario.getAllByIdAsociado(asociado.getIdAsociado()));

        return asociadoU;
    }

    private boolean verificarSiElPorcentajeBeneficiariosEstaErrado(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().isEmpty()) {
            return false;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje != 100);
    }

    private void procesarBeneficiarios(Asociado asociado) throws Exception {

        List<Beneficiario> listaBeneficiariosEliminar = obtenerBeneficiariosAEliminar(asociado.getBeneficiarios(), asociado.getIdAsociado());

        for (Beneficiario beneficiario : listaBeneficiariosEliminar) {
            persistenciaBeneficiario.delete(beneficiario.getIdBeneficiario());
        }

        if (asociado.getBeneficiarios() == null) {
            return;
        }

        for (Beneficiario beneficiario : asociado.getBeneficiarios()) {
            beneficiario.setAsociado(asociado);
            if (beneficiario.getIdBeneficiario() == null) {
                persistenciaBeneficiario.insert(beneficiario);
            }

        }

    }

    private List<Beneficiario> obtenerBeneficiariosAEliminar(List<Beneficiario> listaEntrante, Integer idAsociado) throws Exception {
        List<Beneficiario> listaBeneficiariosActuales = persistenciaBeneficiario.getAllByIdAsociado(idAsociado);

        if (listaEntrante == null || listaBeneficiariosActuales == null) {
            return listaBeneficiariosActuales;
        }

        return listaBeneficiariosActuales.stream().filter(b1 -> listaEntrante.stream()
                        .noneMatch(b2 -> b1.getIdBeneficiario().equals(b2.getIdBeneficiario())))
                .collect(Collectors.toList());

    }
}
