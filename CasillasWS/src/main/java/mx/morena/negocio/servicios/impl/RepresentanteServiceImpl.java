package mx.morena.negocio.servicios.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.AsignacionRepresentantesDTO;
import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.dto.RepresentantesClaveDTO;
import mx.morena.negocio.dto.TipoRepDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.RepresentanteClaveElectoral;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class RepresentanteServiceImpl extends MasterService implements IRepresentanteService {

	@Autowired
	private IRepresentanteRepository representanteRepository;

	private static String sinClave = "No cuenta con Clave Elector";
	private static String sinCalle = "No se cuenta con calle";

	@Override
	public Long saveRepresentante(RepresentanteDTO representante, long perfil, long idUsuario)
			throws RepresentanteException {
		Long resp = null;

		Map<Integer, String> rep = getTipoRepresentante(perfil);

		if (rep.containsKey(representante.getTipo())) {

			resp = guardarRepresentante(representante, idUsuario, representante.getTipo());

		} else {

			throw new RepresentanteException("Permisos insuficientes para dar de alta el tipo de representante", 401);
		}

		return resp;
	}

	private Map<Integer, String> getTipoRepresentante(long perfil) {

		Map<Integer, String> representantes = new HashMap<Integer, String>();

		if (perfil < PERFIL_RC) {
			representantes.put(REP_RC, "Representante RC");
		}

		if (perfil < PERFIL_RG) {
			representantes.put(REP_RG, "Representante RG");
		}

		if (perfil < PERFIL_MUNICIPAL) {
			representantes.put(REP_MUNICIPAL, "Representante Municipal");
		}

		if (perfil < PERFIL_LOCAL) {
			representantes.put(REP_LOCAL, "Representante Local");
			representantes.put(REP_CRG, "Representante CRG");
		}

		if (perfil < PERFIL_FEDERAL) {
			representantes.put(REP_FEDERAL, "Representante Federal");
		}

		return representantes;
	}

	public Long guardarRepresentante(RepresentanteDTO representanteDTO, long idUsuario, Integer tipo)
			throws RepresentanteException {

		if (representanteDTO.getIsClaveElector() == true) {
			representanteDTO.setClaveElector(null);
		}

		if (representanteDTO.getIsClaveElector() == true
				|| (representanteDTO.getClaveElector() != null && representanteDTO.getClaveElector().length() == 18)) {
			Representantes existeClave = representanteRepository.findByClaveElector(representanteDTO.getClaveElector());

			if (existeClave != null) {
				throw new RepresentanteException("La clave de elector ya se encuentra registrada", 400);
			} else {

				if (representanteDTO.getIdDistritoFederal() != null && representanteDTO.getIdMunicipio() != null
						&& representanteDTO.getIdEstado() != null && representanteDTO.getIdSeccionElectoral() != null) {

					Representantes representante = new Representantes();

					MapperUtil.map(representanteDTO, representante);

					if (representanteDTO.getIsCalle()) {
						representante.setCalle(sinCalle);
					}
					if (representanteDTO.getIsClaveElector()) {
						representante.setClaveElector(sinClave);
					}

					representante.setFechaSistema(new Date(System.currentTimeMillis()));
					representante.setTipo(tipo);
					representante.setUsuario(idUsuario);
					representante.setDistritoFederal(representanteDTO.getIdDistritoFederal());
					representante.setEstado(representanteDTO.getIdEstado());
					representante.setMunicipio(representanteDTO.getIdMunicipio());
					representante.setSeccionElectoral(representanteDTO.getIdSeccionElectoral());

					if (!representanteDTO.getIneLado1().equals("")) {

						representante.setRutaIneLado1(
								RUTA_INE + "/" + representante.getClaveElector() + UUID.randomUUID().toString());
					}
					if (!representanteDTO.getIneLado2().equals("")) {

						representante.setRutaIneLado2(
								RUTA_INE + "/" + representante.getClaveElector() + UUID.randomUUID().toString());
					}
					if (!representanteDTO.getInePdf().equals("")) {

						representante.setRutaInePdf(
								RUTA_INE + "/" + representante.getClaveElector() + UUID.randomUUID().toString());
					}

					representanteRepository.save(representante);

					return representanteRepository.getIdMax();

				} else {
					throw new RepresentanteException("No se encontraron datos.", 404);
				}
			}
		} else {
			throw new RepresentanteException("El numero de caracteres ingresado en la clave de elector es incorrecto",
					400);
		}

	}

	@Override
	public List<TipoRepDTO> getAllTipo() {
		
		List<TipoRepDTO> lstRepDTO = null;
		List<Perfil> lstPerfil = null;
		
		lstPerfil = representanteRepository.getAllTipoRep();
		
		if (lstPerfil != null) {
			
			lstRepDTO = MapperUtil.mapAll(lstPerfil, TipoRepDTO.class);
		}
		return lstRepDTO;
	}

	@Override
	public List<RepresentantesClaveDTO> getAllRepresentantes(String claveElector, boolean check)throws RepresentanteException {
		
		List<RepresentantesClaveDTO> lstRepreDTO = null;
		List<RepresentanteClaveElectoral> lstRepre = null;
		
		if(claveElector != null &&  claveElector.length() == 18 && check == false) {
			
			lstRepre = representanteRepository.getAllRepresentantes(claveElector);
			
			if(lstRepre != null) {
				
				lstRepreDTO = MapperUtil.mapAll(lstRepre, RepresentantesClaveDTO.class);	
				
			}else {
					throw new RepresentanteException("La clave de elector ingresada no esta registrada", 400);
				}
			
			return lstRepreDTO;
			
		}else if(check == true){
			claveElector = "No cuenta con Clave Elector";
			
			lstRepre = representanteRepository.getAllRepresentantes(claveElector);
			
			lstRepreDTO = MapperUtil.mapAll(lstRepre, RepresentantesClaveDTO.class);
			
			return lstRepreDTO;
			
		}else {
			throw new RepresentanteException("El numero de caracteres ingresado en la clave de elector es incorrecto",
						400);
		}
	}

	@Override
	public Long asignaRepresentante(long usuario, long perfil, AsignacionRepresentantesDTO dto)
			throws RepresentanteException {

		boolean estatalFederal = perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL;
		if(estatalFederal || perfil == PERFIL_CRG ) {

		Representantes representante = representanteRepository.getRepresentante(dto.getRepresentanteId());

		if (representante != null) {
			long tipoRepresentante = representante.getTipo().longValue();

			if (dto.getRepresentanteId() != 0 && dto.getCargo() != 0) {

				if (perfil == PERFIL_ESTATAL && tipoRepresentante == PERFIL_FEDERAL ) {
					long asignacion = 1;
					guardado(usuario, perfil, dto, asignacion);
				}
				
				if (estatalFederal && tipoRepresentante == PERFIL_LOCAL) {
					long asignacion = 2;
					guardado(usuario, perfil, dto, asignacion);
				} 
				
				if (estatalFederal && tipoRepresentante == PERFIL_MUNICIPAL) {
					long asignacion = 3;
					guardado(usuario, perfil, dto, asignacion);
				}

				if (estatalFederal && tipoRepresentante == PERFIL_CRG) {
					long asignacion = 4;
					guardado(usuario, perfil, dto, asignacion);
				}

				if (estatalFederal && tipoRepresentante == PERFIL_RG) {
					long asignacion = 5;
					guardado(usuario, perfil, dto, asignacion);
				}

				if ((estatalFederal || perfil == PERFIL_CRG) && tipoRepresentante == PERFIL_RC) {
					long asignacion = 6;
					guardado(usuario, perfil, dto, asignacion);
				}

			} else {
				throw new RepresentanteException("Ingrese  los campos obligatorios", 400);
			}
		} else {
			throw new RepresentanteException("El representante solicitado no existe", 400);
		}
		}else {
			throw new RepresentanteException("No cuenta con perfisos suficientes para realizar la operacion", 401);
		}
		return representanteRepository.getIdMaxAsignados();
	}
	
	
	public void guardado(long usuario, long perfil, AsignacionRepresentantesDTO dto, long asignacion) {
		
		RepresentantesAsignados representante = new RepresentantesAsignados();

		MapperUtil.map(dto, representante);

		representante.setUsuarioId(usuario);

		representanteRepository.asignaRepresentante(representante);

		representanteRepository.updateRepresentante(perfil, representante, asignacion);
		
	}

}