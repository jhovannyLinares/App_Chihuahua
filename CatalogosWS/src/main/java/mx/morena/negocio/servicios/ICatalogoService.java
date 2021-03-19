package mx.morena.negocio.servicios;

import java.util.List;
import java.util.Map;

import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.LocalidadDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;

public interface ICatalogoService {

	List<EntidadDTO> getEntidades();

	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, Long idEntidad);

	List<MunicipioDTO> getMunicipioByFederal(long usuario, long perfil, Long id);

	List<LocalidadDTO> getLocalidadByMunicipio(long usuario, long perfil, Long id);

	CatalogoDTOOffline getCatalogos(long usuario, long perfil);

	List<CasillaDTO> getCasillas(long usuario, long perfil, Long distritoFederalId, Long municipioId, Long seccionId);

	Map<Integer, String> getRepresentantes(long perfil);

}
