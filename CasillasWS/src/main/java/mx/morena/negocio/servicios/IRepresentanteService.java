package mx.morena.negocio.servicios;

import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;

public interface IRepresentanteService {

	public Long saveRepresentante(RepresentanteDTO representanteDTO, long perfil, long usuario) throws RepresentanteException;
	
}
