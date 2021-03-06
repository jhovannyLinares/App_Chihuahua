package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.CasillaDTO;
import mx.morena.negocio.dto.DistritoFederalDTO;
import mx.morena.negocio.dto.EleccionDTO;
import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.negocio.dto.IncidenciaDTO;
import mx.morena.negocio.dto.MotivosTerminoVotacionDTO;
import mx.morena.negocio.dto.MunicipioDTO;
import mx.morena.negocio.dto.RepresentacionPartidosDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.dto.SeccionUserDTO;
import mx.morena.negocio.dto.TipoActasDTO;
import mx.morena.negocio.dto.offline.CatalogoDTOOffline;
import mx.morena.negocio.exception.CatalogoException;
import mx.morena.negocio.servicios.impl.CargoDTO;

public interface ICatalogoService {

	List<EntidadDTO> getEntidades();

	List<DistritoFederalDTO> getFederalByEntidad(long usuario, long perfil, Long idEntidad);

	List<MunicipioDTO> getMunicipioByFederal(long usuario, long perfil, Long id) throws CatalogoException;

//	List<LocalidadDTO> getLocalidadByMunicipio(long usuario, long perfil, Long id);

	CatalogoDTOOffline getCatalogos(long usuario, long perfil) throws CatalogoException;

	List<CasillaDTO> getCasillas(long usuario, long perfil, Long distritoFederalId, Long municipioId, Long seccionId, Boolean isLibres);

	List<RepresentanteDTO> getRepresentantes(long perfil);

	List<SeccionDTO> getSeccionesByMunicipio(long idUsuario, long idPerfil, Long idMunicipio);

	List<CargoDTO> getCargos(Long tipoRepresentante) throws CatalogoException;

	List<DistritoFederalDTO> getLocalByEntidad(long usuario, long perfil, Long idEntidad);

//	List<ZonaDTO> getZonas(long usuario, long idPerfil, Long idFederal) throws CatalogoException;
	
	List<IncidenciaDTO> getIncidencias()throws CatalogoException;
	
	List<EleccionDTO>getEleccion()throws CatalogoException;
	
	List<SeccionUserDTO>getSeccionByBrigadista(Long idUsuario)throws CatalogoException;
	
	List<TipoActasDTO>getTipoActas(Long idPerfil)throws CatalogoException;

	List<RepresentacionPartidosDTO> getRepresentacionPartidos()throws CatalogoException;
	
	List<MotivosTerminoVotacionDTO> getMotivos()throws CatalogoException;
}
