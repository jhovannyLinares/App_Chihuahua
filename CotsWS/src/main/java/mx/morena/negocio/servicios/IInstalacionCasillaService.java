package mx.morena.negocio.servicios;

import java.io.IOException;

import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.exception.CotException;

public interface IInstalacionCasillaService {

	Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;

	Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;

}
