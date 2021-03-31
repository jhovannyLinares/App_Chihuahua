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
import mx.morena.negocio.dto.ReporteCotDTO;
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
				
				Convencidos existeCurp = cotRepository.getByCurp(cotDto.getCurp());
				
				List<Convencidos> existeClave = cotRepository.findByClaveElectorVal(cotDto.getClaveElector());

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
							throw new CotException("La seccion " + sec.getId()
							+ " ya se encuentra asignada, favor de revisar", 400);
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
		} else {
			listaSecciones = null;
		}
		
		cotDto.setSecciones(listaSecciones);
		if (cot.getEstatus() == ESTATUS_ALTA) {
			cotDto.setEstatus("Activo");
		} else {
			cotDto.setEstatus("Suspendido");
		}
		
		if (cot.getCalle().equals(SIN_CALLE)) {
			cotDto.setIsCalle(true);
		} else {
			cotDto.setIsCalle(false);
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

	@Override
	public String update(CotDTO cotDto, long perfil, Long id) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			if (id != null && cotDto != null) {
				if (cotDto.getClaveElector().trim().length() == 18 && cotDto.getCurp().trim().length() == 18) {
					Convencidos existeCot = cotRepository.getByIdAndTipoA(id, COT);
					if (existeCot != null) {

						final String campoClave = "clave_elector";
						final String campoCurp = "curp";

						boolean existeCurp = datosDuplicados(cotDto.getCurp(), campoCurp, id);
						boolean existeClave = datosDuplicados(cotDto.getClaveElector(), campoClave, id);

						if (!existeCurp && !existeClave) {
							Convencidos personaCot = new Convencidos();

							MapperUtil.map(cotDto, personaCot);

							if (cotDto.getIsCalle()) {
								personaCot.setCalle(SIN_CALLE);
							}

							// personaCot.setTipo(cotDto.getTipo().charAt(0));
							personaCot.setId(id);
							personaCot.setIdEstado(cotDto.getIdEstado());
							personaCot.setIdFederal(cotDto.getIdDistritoFederal());
							personaCot.setIdMunicipio(cotDto.getIdMunicipio());
							personaCot.setIdSeccion(cotDto.getIdSeccion());
							personaCot.setTipo(COT);

							System.out.println(personaCot);
							cotRepository.update(personaCot);

							return "Cot editado correctamente " + cotDto.getNombre();

						} else if (existeCurp) {
							throw new CotException("Curp duplicada, favor de validar", 400);
						} else {
							throw new CotException("Clave de elector duplicada, favor de validar", 400);
						}

					} else {
						throw new CotException("No existe el COT ingresado", 404);
					}
				} else {
					throw new CotException("CURP o Clave no valida, debe tener 18 caracteres", 400);
				}

			} else {
				throw new CotException("Ingrese todos los datos", 400);
			}

		} else {
			throw new CotException("No cuenta con suficientes permisos", 401);
		}
	}
	
	private boolean datosDuplicados(String valorCampo, String campo, Long id){
        Convencidos existeDuplicado = cotRepository.findByClaveOCurp(campo, valorCampo, id);
        
        if (existeDuplicado ==  null) {
			return false;
		}
        
        return true;
    }

	@Override
	public boolean getByCurp(String curp) throws CotException {
		if (curp.trim().length() == 18) {
			Convencidos convencido = cotRepository.getByCurp(curp);
			if (convencido == null) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new CotException("El numero de caracteres ingresado en la curp es incorrecto",
					400);
		}
	}

	@Override
	public List<ReporteCotDTO> getReporte() throws CotException {
		
		List<ReporteCotDTO> repDto= new ArrayList<ReporteCotDTO>();
		ReporteCotDTO cot = new ReporteCotDTO();
		
		cot.setIdDistritoFederal(1L);
		cot.setDistritoFederal("Chuhuahua");
		cot.setSecciones("1,2,3,4,5");
		cot.setCots("CLAVEELECTORAL1234");
		cot.setMetaCots(10L);
		cot.setPorcentajeCobertura(10l);
		cot.setPorcetajeAvance(50L);
		cot.setCubiertas(10L);
		repDto.add(cot);
		
		cot = new ReporteCotDTO();
		cot.setCots("Cot 1");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(2L);
        cot.setMetaCots(20L);
        cot.setPorcentajeCobertura(30L);
        cot.setPorcetajeAvance(50L);
        cot.setSecciones("1,2,3");
        repDto.add(cot);

        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 2");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 3");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 4");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 5");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 6");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 7");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 8");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
        cot = new ReporteCotDTO(); 
        cot.setCots("Cot 9");
        cot.setCubiertas(10L);
        cot.setDistritoFederal("JUAREZ");
        cot.setIdDistritoFederal(1L);
        cot.setMetaCots(10L);
        cot.setPorcentajeCobertura(20L);
        cot.setPorcetajeAvance(30L);
        cot.setSecciones("4,5,6");
        repDto.add(cot);
        
		
		
		
		return repDto;
	}
}