package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.exception.RutasException;

public interface IRutasService {

	public List<CatalogoCrgDTO>getCatalogo(Long perfil) throws RutasException;
	
	public String asignarRutas(List<Long> idRutas, Long idCrg, long perfil) throws RutasException;
}
