package mx.morena.negocio.servicios.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Entidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IEntidadRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;

@Service
public class RepresentanteServiceImpl implements IRepresentanteService {

	@Autowired
	private IRepresentanteRepository representanteRepository;

	@Autowired
	private IEntidadRepository entidadRepository;

	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;

	@Autowired
	private IMunicipioRepository municipioRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	protected static final Integer PERFIL_ESTATAL = 1;
	protected static final Integer PERFIL_FEDERAL = 2;
	protected static final Integer PERFIL_LOCAL = 3;
	protected static final Integer PERFIL_MUNICIPAL = 4;
	protected static final Integer PERFIL_CRG = 7;
	protected static final Integer PERFIL_RG = 8;

	private static final byte REP_FEDERAL = 1;
	private static final byte REP_LOCAL = 2;
	private static final byte REP_MUNICIPAL = 3;
	private static final byte REP_CRG = 4;
	private static final byte REP_RG = 5;
	private static final byte REP_RC = 6;

	@Override
	public String saveRepFederal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_FEDERAL);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	@Override
	public String saveRepLocal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_LOCAL);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	@Override
	public String saveRepMunicipal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_LOCAL) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_MUNICIPAL);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	@Override
	public String saveRepCRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_CRG);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	@Override
	public String saveRepRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_CRG || perfil == PERFIL_LOCAL
				|| perfil == PERFIL_MUNICIPAL) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_RG);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	@Override
	public String saveRepRC(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		String resp = null;
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_CRG || perfil == PERFIL_LOCAL
				|| perfil == PERFIL_MUNICIPAL || perfil == PERFIL_CRG || perfil == PERFIL_RG) {
			
			if (representante != null) {
				resp = guardarRepresentante(representante, idUsuario, REP_RC);
			} else {
				throw new RepresentanteException("El representante debe tener datos.", 400);
			}
			
		} else {
			throw new RepresentanteException("Permisos insuficientes", 401);
		}
		
		return resp;
	}

	public String guardarRepresentante(RepresentanteDTO representanteDTO, long idUsuario, byte tipo) throws RepresentanteException {
		
		Representantes representante = new Representantes();
		if (representanteDTO.getClaveElector() != null && representanteDTO.getClaveElector().length() > 0) {
			Representantes existeClave = representanteRepository.findByClaveElector(representanteDTO.getClaveElector());

			if (existeClave != null)
				throw new RepresentanteException("La clave de elector ya se encuentra registrada", 400);
		}

			DistritoFederal distritoF = distritoFederalRepository.findById(representanteDTO.getIdDistritoFederal()).get();
			Municipio municipio = municipioRepository.findById(representanteDTO.getIdMunicipio()).get();
			Entidad entidad = entidadRepository.findById(representanteDTO.getIdEstado()).get();
			SeccionElectoral seccion = seccionRepository.findById(representanteDTO.getIdSeccionElectoral()).get();
			Usuario usuario = usuarioRepository.findById(idUsuario).get();
	
			if (distritoF != null && municipio != null && entidad != null && seccion != null && usuario != null) {
				representanteDTO.setFechaRegistro(new Date(System.currentTimeMillis()));
				representanteDTO.setTipo(tipo);
				MapperUtil.map(representanteDTO, representante);
	
				representante.setEstado(entidad);
				representante.setDistritoFederal(distritoF);
				representante.setMunicipio(municipio);
				representante.setSeccionElectoral(seccion);
				representante.setUsuario(usuario);
	
				System.out.println(representante);
				representanteRepository.save(representante);
			} else {
				throw new RepresentanteException("No se encontraron datos.", 404);
			}
		
		return "" + representanteDTO;
	}

}