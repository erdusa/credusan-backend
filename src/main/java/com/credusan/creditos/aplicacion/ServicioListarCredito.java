package com.credusan.creditos.aplicacion;

import com.credusan.creditos.dominio.enums.EnumTipoEstadoCredito;
import com.credusan.creditos.dominio.modelos.Credito;
import com.credusan.creditos.dominio.puertos.PersistenciaCredito;
import com.credusan.creditos.dominio.puertos.PersistenciaCreditoLiquidacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioListarCredito {

    private final PersistenciaCredito persistenciaCredito;
    private final PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion;

    public ServicioListarCredito(PersistenciaCredito persistenciaCredito, PersistenciaCreditoLiquidacion persistenciaCreditoLiquidacion) {
        this.persistenciaCredito = persistenciaCredito;
        this.persistenciaCreditoLiquidacion = persistenciaCreditoLiquidacion;
    }

    public List<Credito> getAllSaldadosByIdAsociado(int idAsociado) {
        List<Credito> listaCreditos = this.persistenciaCredito.getAllByIdAsociado(idAsociado);
        listaCreditos = filtrarPorEstado(listaCreditos, EnumTipoEstadoCredito.SALDADO.id);
        asignarTablaLiquidacion(listaCreditos);
        return listaCreditos;
    }

    public List<Credito> getAllVigentesByIdAsociado(int idAsociado) {
        List<Credito> listaCreditos = this.persistenciaCredito.getAllByIdAsociado(idAsociado);
        listaCreditos = filtrarPorEstado(listaCreditos, EnumTipoEstadoCredito.VIGENTE.id);
        asignarTablaLiquidacion(listaCreditos);
        return listaCreditos;
    }

    private void asignarTablaLiquidacion(List<Credito> listaCreditos) {
        listaCreditos.forEach(c -> c
                .setListaCreditoLiquidacion(persistenciaCreditoLiquidacion
                        .getAllByIdCredito(c.getIdCredito())));
    }

    private List<Credito> filtrarPorEstado(List<Credito> listaCreditos, Short idTipoEstadoCredito) {
        return listaCreditos.stream()
                .filter(c -> c.getTipoEstadoCredito()
                        .getIdTipoEstadoCredito()
                        .equals(idTipoEstadoCredito))
                .collect(Collectors.toList());
    }
}
