package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.dominio.puertos.PersistenciaBeneficiario;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ServicioCrearAsociado {

    private final PersistenciaAsociado persistenciaAsociado;
    private final PersistenciaCaptacion persistenciaCaptacion;
    private final PersistenciaBeneficiario persistenciaBeneficiario;

    public ServicioCrearAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaCaptacion persistenciaCaptacion, PersistenciaBeneficiario persistenciaBeneficiario) {
        this.persistenciaAsociado = persistenciaAsociado;
        this.persistenciaCaptacion = persistenciaCaptacion;
        this.persistenciaBeneficiario = persistenciaBeneficiario;
    }

    @Transactional(rollbackFor = Exception.class)
    public Asociado create(Asociado asociado) throws Exception {

        if (verificarSiTieneIdAsociado(asociado)) {
            throw new ValidationException("El identificador del asociado no debe tener valor");
        }

        if (verificarSiElPorcentajeBeneficiariosNoEsCeroNiCien(asociado)) {
            throw new ValidationException("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }

        asociado.setActivo(true);
        Asociado asociadoCreado = persistenciaAsociado.insert(asociado);

        insertarBeneficiarios(asociadoCreado, asociado.getBeneficiarios());

        persistenciaCaptacion.crearCuentaAportes(asociadoCreado);

        asociadoCreado.setBeneficiarios(persistenciaBeneficiario.getAllByIdAsociado(asociadoCreado.getIdAsociado()));

        return asociadoCreado;
    }

    private void insertarBeneficiarios(Asociado asociado, List<Beneficiario> listaBeneficiarios) throws Exception {
        if (asociado.getBeneficiarios() == null) {
            return;
        }
        for (Beneficiario beneficiario : listaBeneficiarios) {
            beneficiario.setAsociado(asociado);
            persistenciaBeneficiario.insert(beneficiario);
        }

    }

    private boolean verificarSiTieneIdAsociado(@NonNull Asociado asociado) {
        return asociado.getIdAsociado() != null;
    }

    private boolean verificarSiElPorcentajeBeneficiariosNoEsCeroNiCien(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().isEmpty()) {
            return false;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje != 100);
    }
}
