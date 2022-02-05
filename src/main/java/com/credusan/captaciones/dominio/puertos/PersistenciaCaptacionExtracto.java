package com.credusan.captaciones.dominio.puertos;


import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;

import java.util.List;

public interface PersistenciaCaptacionExtracto {

    CaptacionExtracto insert(CaptacionExtracto captacionExtracto);

    List<CaptacionExtracto> getAllByIdCaptacionAndFechas(ConsultaCaptacionExtractoDTO extractoDTO) throws Exception;
}
