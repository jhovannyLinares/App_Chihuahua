package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.RepresentantesClaveDTO;
import mx.morena.negocio.dto.AsignacionRepresentantesDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.TipoRepDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IRepresentanteService {

	public Long saveRepresentante(RepresentanteDTO representanteDTO, long perfil, long usuario) throws RepresentanteException;
	
	List<TipoRepDTO> getAllTipo();

	public Long asignaRepresentante(long usuario, long perfil, AsignacionRepresentantesDTO dto) throws RepresentanteException;
	
	List<RepresentantesClaveDTO> getAllRepresentantes(String claveElector, boolean check) throws RepresentanteException;
	
}
