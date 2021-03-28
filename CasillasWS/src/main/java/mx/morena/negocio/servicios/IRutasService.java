package mx.morena.negocio.servicios;

import java.util.List;

import mx.morena.negocio.dto.AsignarCasillasDTO;
import mx.morena.negocio.dto.CasillasCatalogoDto;
import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.dto.DesasignarCasillasDTO;
import mx.morena.negocio.dto.RutaCatalogoDto;
import mx.morena.negocio.dto.RutaResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.dto.ZonaCrgDTO;
import mx.morena.negocio.exception.RutasException;

public interface IRutasService {

	public List<CatalogoCrgDTO>getCatalogo(Long isRepresentante, Long perfil) throws RutasException;
	
	public String asignarRutas(List<Long> idRutas, Long idCrg, long perfil) throws RutasException;
	
	List<RutaResponseDTO> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla, Long perfil) throws RutasException; 

/////////////////////////////////////////////////////////     catalogos
	public List<ZonaCrgDTO> getZonasByDistrito(long idPerfil, Long idDistrito);

	public List<RutaCatalogoDto> getRutaByZonaCrg(long idPerfil, Long zonaCrg);

	public List<CasillasCatalogoDto> getCasillaByRuta(long idPerfil, Long ruta);
	
	public String asignarCasillas(long idPerfil, AsignarCasillasDTO asignarCasillasDTO) throws RutasException;
	
	public String desasignarCasillas(long idPerfil, DesasignarCasillasDTO desasignarCasillasDTO) throws RutasException;

	public List<SeccionDTO> getSeccionByRutas(long idPerfil, Long idRuta) throws RutasException;
	
}
