package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CatalogoDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.DistritoLocalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.SeccionDTO;

public interface ICatalogoService {
	
	List<EntidadDTO> getEntidades();

	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, Long idEntidad);

	List<DistritoLocalDTO> getLocalByFederal(long usuario, long perfil, Long id);
	
	List<MunicipioDTO> getMunicipioByLocal(long usuario, long perfil, Long id);
	
	List<LocalidadDTO> getLocalidadByMunicipio(long usuario, long perfil, Long id);
	
	List<SeccionDTO> getSeccionByLocalidad(long usuario, long perfil, Long id);

	CatalogoDTO getCatalogos(long usuario, long perfil);

	
}
