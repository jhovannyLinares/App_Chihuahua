package mx.morena.negocio.servicios;

import java.io.IOException;
import java.util.List;

import mx.morena.negocio.dto.CasillasDTO;
import mx.morena.negocio.dto.CierreCasillaDTO;
import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.exception.CotException;

public interface IInstalacionCasillaService {

	Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;

	Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException, IOException;
	
	List<CasillasDTO> getCasillasAsignadas(Long idUsuario) throws CotException;

	String horaCierre(long usuario, CierreCasillaDTO dto, long perfil)throws CotException, IOException;
}
