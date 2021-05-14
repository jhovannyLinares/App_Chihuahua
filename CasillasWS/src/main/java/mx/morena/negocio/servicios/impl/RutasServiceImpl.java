package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.AsignarCasillasDTO;
import mx.morena.negocio.dto.CasillasCatalogoDto;
import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.dto.DesasignarCasillasDTO;
import mx.morena.negocio.dto.RutaCatalogoDto;
import mx.morena.negocio.dto.RutaResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.dto.TipoCasillaDTO;
import mx.morena.negocio.dto.ZonaCrgDTO;
import mx.morena.negocio.exception.RutasException;
import mx.morena.negocio.servicios.IRutasService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Zona;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class RutasServiceImpl extends MasterService implements IRutasService {

	@Autowired
	private IRepresentanteRepository crgRepository;

	@Autowired
	private IRutasRepository rutasRepository;

	@Autowired
	private ISeccionElectoralRepository seccionElectoralRepository;

	@Override
	public List<CatalogoCrgDTO> getCatalogo(Long tipoRepresentante, Long perfil) throws RutasException {

//		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {

			List<CatalogoCrgDTO> crgDto = null;
			List<Representantes> crg = null;
			crg = crgRepository.getAllCrg(tipoRepresentante);

			if (crg != null) {

				crgDto = MapperUtil.mapAll(crg, CatalogoCrgDTO.class);

				return crgDto;

			} else {
				throw new RutasException("El Representante no fue Encontrado.", 401);
			}

//		} else {
//			throw new RutasException("Permisos Insuficientes.", 401);
//		}
	}

	@Override
	@Transactional(rollbackFor = { RutasException.class })
	public String asignarRutas(List<Long> idRutas, Long idCrg, long perfil) throws RutasException {
//		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {

			Representantes crg = crgRepository.getById(idCrg);

			if (crg != null) {

				for (Long idRuta : idRutas) {

					List<Rutas> rutasCrg = rutasRepository.findById(idRuta);
					for (Rutas rut : rutasCrg) {

						if (rut.getStatus() == 0) {

							rutasRepository.updateIdCrg(idRuta, idCrg);

						} else {
							throw new RutasException("La ruta " + rut.getId() + " ya la tiene asignada otro Crg", 400);
						}
					}
				}

			} else {
				throw new RutasException("No se encontro el Crg ingresado", 404);
			}

//		} else {
//			throw new RutasException("No cuenta con suficientes permisos", 401);
//		}

		return "Se asignaron rutas al Crg " + idCrg;
	}

	@Override
	public List<RutaResponseDTO> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla, Long perfil)
			throws RutasException {
//		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {

			List<Rutas> lstRutas = rutasRepository.getZonasByWhere(idFederal, zonaCRG, ruta, casilla);

			List<RutaResponseDTO> lstRutasDTO = null;
//			List<Rutas> lstRutas = null;
			List<TipoCasillaDTO> lstRutasResponse = null;
//
//			lstRutas = rutasRepository.getRutas(idFederal, zonaCRG, ruta, casilla);
//
			if (lstRutas != null) {
//
				lstRutasDTO = MapperUtil.mapAll(lstRutas, RutaResponseDTO.class);
//
				for (RutaResponseDTO rutas : lstRutasDTO) {

					rutas.setRuta(rutas.getId());

					lstRutas = rutasRepository.getTipoCasilla(rutas.getIdRutaRg(), rutas.getSeccionId());

					logger.debug(rutas.getIdRutaRg() + "  .   " + rutas.getSeccionId());
					
					if (lstRutas != null) {

						lstRutasResponse = MapperUtil.mapAll(lstRutas, TipoCasillaDTO.class);

						rutas.setLstTipoCasilla(lstRutasResponse);
					}

					SeccionElectoral se = seccionElectoralRepository.getById(rutas.getSeccionId());

					rutas.setIdMunicipio(se.getMunicipioId());
					rutas.setMunicipio(se.getMunicipio());

				}
//
				return lstRutasDTO;

//
			} else {
				throw new RutasException("No se encontraron resultados con los datos ingresados.", 404);
			}
//		} else {
//			throw new RutasException("No cuenta con permisos suficientes.", 401);
//		}
	}

/////////////////////////////////////////////////////////     catalogos
	@Override
	public List<ZonaCrgDTO> getZonasByDistrito(long idPerfil, Long idDistrito) {

		List<ZonaCrgDTO> lstDto = new ArrayList<ZonaCrgDTO>();

//		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {

			List<Zona> lstRutas = rutasRepository.getZonasByDistrito(idDistrito);

			ZonaCrgDTO dto = null;
			for (Zona zona : lstRutas) {
				dto = new ZonaCrgDTO();

				dto.setIdDistrito(zona.getIdDistrito());
				dto.setNombreDistrito(zona.getNombreDistrito());
				dto.setZonaCrg(zona.getId());
				dto.setIdZonaCrg(zona.getZonaCrg() + "-" + zona.getIdZonaCrg());

				lstDto.add(dto);
			}
			return lstDto;
//		}
//		return null;
	}

	@Override
	public List<RutaCatalogoDto> getRutaByZonaCrg(long idPerfil, Long zonaCrg) {

		List<RutaCatalogoDto> lstDto = null;

//		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {

			Zona zona = rutasRepository.getZonasByid(zonaCrg);

			List<Rutas> lstRutas = rutasRepository.getRutasByZonas(zona.getZonaCrg(), zona.getIdZonaCrg());
			lstDto = MapperUtil.mapAll(lstRutas, RutaCatalogoDto.class);
			logger.debug("***  rutas by zona " + lstDto.size());
			return lstDto;
//		}
//		return null;
	}

	@Override
	public List<CasillasCatalogoDto> getCasillaByRuta(long idPerfil, Long rutaid) {
		List<CasillasCatalogoDto> lstDto = null;
//		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {

			Rutas ruta = rutasRepository.getRutaByid(rutaid);

			List<Rutas> lstRutas = rutasRepository.getCasillaByRuta(ruta.getIdRutaRg());
			lstDto = MapperUtil.mapAll(lstRutas, CasillasCatalogoDto.class);
			logger.debug("***  casillas by ruta " + lstDto.size());
			return lstDto;
//		}
//		return null;
	}

	@Override
	@Transactional(rollbackFor = { RutasException.class })
	public String asignarCasillas(long idPerfil, AsignarCasillasDTO casillaDto) throws RutasException {
//		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {
			if (!casillaDto.getIdCasillas().isEmpty() && casillaDto.getIdRuta() != null && casillaDto.getIdRuta() > 0) {
				// Se valida que exista la ruta
				Rutas ruta = rutasRepository.getRutaById(casillaDto.getIdRuta());

				if (ruta != null) {

					String info = "";
					final int BAJA_CASILLA = 0;

					for (Long casilla : casillaDto.getIdCasillas()) {
						// Se valida que existan las casillas sin asignar
						Rutas existeCasilla = rutasRepository.getCasillaByIdAndEstatus(casilla, BAJA_CASILLA);

						if (existeCasilla != null) {
							rutasRepository.asignarCasillas(casilla, ruta.getRuta(), ruta.getIdRutaRg());
							rutasRepository.cambiarEstatusCasilla(casilla, BAJA_CASILLA);
							info += ", " + casilla.toString();

						} else {
							throw new RutasException("No se encontro la casilla " + casilla, 404);
						}
					}

					return "Se asignaron las casillas" + info;

				} else {
					throw new RutasException("No se encontro la ruta ingresada", 404);
				}
			} else {
				throw new RutasException("Ingrese por lo menos una casilla o ruta", 400);
			}

//		} else {
//			throw new RutasException("Permisos insuficientes", 401);
//		}
	}

	@Override
	@Transactional(rollbackFor = { RutasException.class })
	public String desasignarCasillas(long idPerfil, DesasignarCasillasDTO casillas) throws RutasException {
//		if (idPerfil == PERFIL_ESTATAL || idPerfil == PERFIL_FEDERAL) {

			if (casillas.getIdCasillas() != null && !casillas.getIdCasillas().isEmpty()) {

				String info = "";
				final int ALTA_CASILLA = 1;

				for (Long casilla : casillas.getIdCasillas()) {
					Rutas ruta = rutasRepository.getCasillaByIdAndEstatus(casilla, ALTA_CASILLA);

					if (ruta != null) {
						rutasRepository.desasignarCasillas(casilla);
						rutasRepository.cambiarEstatusCasilla(casilla, ALTA_CASILLA);
						info += ", " + casilla.toString();
					} else {
						throw new RutasException("No se encontro la casilla " + casilla, 404);
					}
				}

				return "Se desasignaron las casillas" + info;

			} else {
				throw new RutasException("Ingrese por lo menos una casilla", 400);
			}
//		} else {
//			throw new RutasException("Permisos insuficientes", 401);
//		}

	}

	@Override
	public List<SeccionDTO> getSeccionByRutas(long idPerfil, Long idRuta) throws RutasException {

		Rutas ruta = rutasRepository.getRutaById(idRuta);
		if (ruta != null) {
			
			List<Rutas> rutas = rutasRepository.getRutasByIdRutaRG(ruta.getIdRutaRg());
			
			List<SeccionDTO> seccionDTOs = new ArrayList<SeccionDTO>();
			
			SeccionDTO dto = null;
			
			for (Rutas ru : rutas) {
				dto = new SeccionDTO();
				dto.setId(ru.getSeccionId());
				dto.setSeccion(ru.getSeccionId()+"");
				seccionDTOs.add(dto);
			}
			return seccionDTOs;
		} else {
			throw new RutasException("Ingrese por lo menos una ruta valida", 400);
		}
	}

}
