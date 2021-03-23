package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.dto.CotResponseDTO;
import mx.morena.negocio.dto.SeccionDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class CotServiceImpl extends MasterService implements ICotService {

	@Autowired
	private IConvencidosRepository cotRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Override
	public Long save(CotDTO cotDto, long perfil, long idUsuario) throws CotException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			if (cotDto.getClaveElector().length() == 18 && cotDto.getCurp().length() == 18) {
				Convencidos existeCurp = cotRepository.getByCurp(cotDto.getCurp(), COT);
				List<Convencidos> existeClave = cotRepository.findByClaveElector(cotDto.getClaveElector());

				if (existeClave != null) {
					throw new CotException("La clave de elector ya esta en uso, intente con otra", 400);
				} else if (existeCurp != null) {
					throw new CotException("La CURP ya esta en uso, intente con otra", 400);
				} else {
					Convencidos personaCot = new Convencidos();

					MapperUtil.map(cotDto, personaCot);
					
					if(cotDto.getIsCalle()) {
						personaCot.setCalle(SIN_CALLE);
					}
					
					personaCot.setFechaRegistro(new Date(System.currentTimeMillis()));
					personaCot.setEstatus(ESTATUS_ALTA);
					personaCot.setFechaSistema(new Timestamp(new Date().getTime()));
					personaCot.setIdEstado(cotDto.getIdEstado());
					personaCot.setIdFederal(cotDto.getIdDistritoFederal());
					personaCot.setIdMunicipio(cotDto.getIdMunicipio());
					personaCot.setIdSeccion(cotDto.getIdSeccion());
					personaCot.setUsuario(idUsuario);
					personaCot.setTipo(COT);

					cotRepository.save(personaCot);

					return cotRepository.getIdMax();

				}

			} else {
				throw new CotException("CURP o Clave no valida, debe tener 18 caracteres", 400);
			}

		} else {
			throw new CotException("No cuenta con suficientes permisos", 401);
		}

	}

	@Override
	@Transactional(rollbackFor={CotException.class})
	public String asignarSecciones(List<Long> idSecciones, Long idCot, long perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			
			Convencidos cot = cotRepository.getByIdAndEstatus(idCot, ESTATUS_ALTA, COT);
			
			if (cot != null) {

			List<SeccionElectoral> electorals = seccionRepository.findByCotId(idCot, COT);

			if (electorals != null) {
				for (SeccionElectoral seccionElectoral : electorals) {
					seccionRepository.updateIdCot(seccionElectoral.getId(), 0l);
				}
			}

			for (Long idSeccion : idSecciones) {

					List<SeccionElectoral> secciones = seccionRepository.findById(idSeccion);

					for (SeccionElectoral sec : secciones) {

						if (sec.getCot().equals(0l)) {
							seccionRepository.updateIdCot(idSeccion, idCot);
						} else {
							throw new CotException("La seccion " + sec.getId() + "-" + sec.getDescripcion()
							+ " ya la tiene asignada otro Cot", 400);
						}

					}

				}

			} else {
				throw new CotException("No se encontro el COT ingresado", 404);
			}

		} else {
			throw new CotException("No cuenta con suficientes permisos", 401);
		}

		return "Se asignaron secciones al COT";

	}

	@Override
	@Transactional
	public String suspender(Long idCot, long perfil, long idUsuario) throws CotException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {

			Convencidos cot = cotRepository.getByIdAndEstatus(idCot, ESTATUS_ALTA, COT);

			if (cot != null) {
				List<SeccionElectoral> secciones = seccionRepository.findByCotId(idCot, COT);

				final String campoFecha = "fecha_baja";
				cotRepository.updateStatusCot(idCot, ESTATUS_SUSPENDIDO, new Date(System.currentTimeMillis()), COT, campoFecha);

				String info = "";
				if (secciones != null) {
					
					for (SeccionElectoral seccion : secciones) {
						seccionRepository.updateIdCot(seccion.getId(), 0l);
					}
					info = ", se han liberado las secciones del COT";
				} else {
					info = ", no hay secciones por liberar";
				}

				return "COT suspendido" + info;

			} else {
				throw new CotException("No se encontraron datos.", 404);
			}

		} else {
			throw new CotException("Permisos insuficientes.", 401);
		}
	}

	@Override
	@Transactional
	public String activar(Long idCot, long perfil) throws CotException {
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			Convencidos cot = cotRepository.getByIdAndEstatus(idCot, ESTATUS_SUSPENDIDO, COT);

			if (cot != null) {

				final String campoFecha = "fecha_reactivacion";
				cotRepository.updateStatusCot(idCot, ESTATUS_ALTA, new Date(System.currentTimeMillis()), COT, campoFecha);

				return "Se reactivo COT";

			} else {
				throw new CotException("No se encontro el COT ingresado", 404);
			}

		} else {
			throw new CotException("Permisos insuficientes.", 401);
		}
	}

	@Override
	public List<CotResponseDTO> getCots(long perfil, Long idDistrito, Long idMunicipio) throws CotException {
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			List<Convencidos> cots = new ArrayList<Convencidos>();
			List<CotResponseDTO> cotsDto = new ArrayList<CotResponseDTO>();
			
			if (idDistrito != null && idMunicipio == null) {
				cots = cotRepository.getByDistritoFederal(idDistrito, COT);
				
			} else if (idDistrito != null && idMunicipio != null) {
				cots = cotRepository.getByDfAndMpio(idDistrito, idMunicipio, COT);
				
			} else {
				throw new CotException("Ingrese un distrito", 400);
			}
			
			if (cots != null) {
				cotsDto = cots.stream().map(this::convertirADto).collect(Collectors.toList());
				return cotsDto;
			} else {
				throw new CotException("No se encontraron datos", 404);
			}
				
		} else {
			throw new CotException("Permisos insuficientes", 401);
		}

	}
	
	public CotResponseDTO convertirADto(Convencidos cot) {
		CotResponseDTO cotDto = new CotResponseDTO();
		
		cotDto.setId(cot.getId());
		cotDto.setNombre(cot.getNombre());
		cotDto.setApellidoPaterno(cot.getApellidoPaterno());
		cotDto.setApellidoMaterno(cot.getApellidoMaterno());
		cotDto.setIdEstado(cot.getIdEstado());
		cotDto.setEstado(cot.getNombreEstado());
		cotDto.setIdDistritoFederal(cot.getIdFederal());
		cotDto.setDistritoFederal(cot.getNombreDistrito());
		cotDto.setIdMunicipio(cot.getIdMunicipio());
		cotDto.setMunicipio(cot.getNombreMunicipio());
		cotDto.setIdSeccion(cot.getIdSeccion());
		cotDto.setClaveElector(cot.getClaveElector());
		cotDto.setCalle(cot.getCalle());
		cotDto.setNumInterior(cot.getNumInterior());
		cotDto.setNumExterior(cot.getNumExterior());
		cotDto.setColonia(cot.getColonia());
		cotDto.setCp(cot.getCp());
		cotDto.setTelCasa(cot.getTelCasa());
		cotDto.setTelCelular(cot.getTelCelular());
		cotDto.setCorreo(cot.getCorreo());
		cotDto.setCurp(cot.getCurp());
		cotDto.setBanco(cot.getBanco());
		cotDto.setClabeInterbancaria(cot.getClabeInterbancaria());
		cotDto.setFechaRegistro(cot.getFechaRegistro());
		cotDto.setSeccion(cot.getIdSeccion().toString());
		
		List<SeccionDTO> listaSecciones = new ArrayList<SeccionDTO>();
		List<SeccionElectoral> secciones = seccionRepository.findByCotId(cot.getId(), COT);
		
		if(secciones != null) {
			listaSecciones = secciones.stream().map(this::convertirSeccion).collect(Collectors.toList());
		}
		
		cotDto.setSecciones(listaSecciones);
		if (cot.getEstatus() == ESTATUS_ALTA) {
			cotDto.setEstatus("Activo");
		} else {
			cotDto.setEstatus("Suspendido");
		}
		
		return cotDto;
	}
	
	public SeccionDTO convertirSeccion(SeccionElectoral seccion) {
		SeccionDTO seccionDTO = new SeccionDTO();
		seccionDTO.setId(seccion.getId());
		seccionDTO.setDescripcion(seccion.getDescripcion());
		return seccionDTO;
	}
	
	@Override
	public List<SeccionDTO> seccionesSinAsignar(long perfil, Long idMunicipio) throws CotException {
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			if (idMunicipio != null) {
				List<SeccionElectoral> secciones = seccionRepository.getSeccionesLibresByMpo(idMunicipio);
				List<SeccionDTO> seccionesDto = new ArrayList<SeccionDTO>();
				
				if (secciones != null) {
					seccionesDto = secciones.stream().map(this::convertirSeccion).collect(Collectors.toList());
					return seccionesDto;
				} else {
					throw new CotException("No se encontraron datos", 404);
				}
				
			} else {
				throw new CotException("Ingrese el municipio", 400);
			}
			
		} else {
			throw new CotException("Permisos insuficientes.", 401);
		}
	}
}