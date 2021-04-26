package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;

public interface IReporteSeguimientoVotoService {

	List<ReporteSeguimintoVotoDTO> getSeguimeitoVoto(Long Perfil, Long Usuario) throws SeguimientoVotoException, IOException;
}
