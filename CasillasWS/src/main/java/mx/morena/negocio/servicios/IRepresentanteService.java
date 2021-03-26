package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.RepresentantesClaveDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.TipoRepDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IRepresentanteService {

	public Long saveRepresentante(RepresentanteDTO representanteDTO, long perfil, long usuario) throws RepresentanteException;
	
	List<TipoRepDTO> getAllTipo();
	
	List<RepresentantesClaveDTO> getAllRepresentantes(String claveElector, boolean check) throws RepresentanteException;
	
}
