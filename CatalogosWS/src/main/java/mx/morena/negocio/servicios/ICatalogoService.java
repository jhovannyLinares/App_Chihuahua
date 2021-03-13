package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;

public interface ICatalogoService {

	List<EntidadDTO> getEntidades();

	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, String idEntidad);

//	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, String idEntidad);
//
////	List<DistritoLocalDTO> getLocalByFederal(long usuario, long perfil, String id);
//
	List<MunicipioDTO> getMunicipioByFederal(long usuario, long perfil, String id);
//
	List<LocalidadDTO> getLocalidadByMunicipio(long usuario, long perfil, String id);
//
//	List<SeccionDTO> getSeccionByLocalidad(long usuario, long perfil, String id);
//
////	CatalogoDTOOffline getCatalogos(long usuario, long perfil);

}
