package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;

public interface ICatalogoService {

	List<EntidadDTO> getEntidades();

	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, String idEntidad);

	List<MunicipioDTO> getMunicipioByFederal(long usuario, long perfil, String id);

	List<LocalidadDTO> getLocalidadByMunicipio(long usuario, long perfil, String id);

	CatalogoDTOOffline getCatalogos(long usuario, long perfil);

}
