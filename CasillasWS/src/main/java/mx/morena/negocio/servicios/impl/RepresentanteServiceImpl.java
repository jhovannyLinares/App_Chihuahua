package mx.morena.negocio.servicios.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.RepresentanteDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.servicios.IRepresentanteService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class RepresentanteServiceImpl extends MasterService implements IRepresentanteService {

	@Autowired
	private IRepresentanteRepository representanteRepository;

	private static final byte REP_FEDERAL = 1;
	private static final byte REP_LOCAL = 2;
	private static final byte REP_MUNICIPAL = 3;
	private static final byte REP_CRG = 4;
	private static final byte REP_RG = 5;
	private static final byte REP_RC = 6;

	@Override
	public Long saveRepFederal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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
	public Long saveRepLocal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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
	public Long saveRepMunicipal(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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
	public Long saveRepCRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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
	public Long saveRepRG(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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
	public Long saveRepRC(RepresentanteDTO representante, long perfil, long idUsuario) throws RepresentanteException {
		Long resp = null;
		
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

	public Long guardarRepresentante(RepresentanteDTO representanteDTO, long idUsuario, byte tipo) throws RepresentanteException {
		
		Representantes representante = new Representantes();
		if (representanteDTO.getClaveElector() != null && representanteDTO.getClaveElector().length() == 18) {
			Representantes existeClave = representanteRepository.findByClaveElector(representanteDTO.getClaveElector());

			if (existeClave != null)
				throw new RepresentanteException("La clave de elector ya se encuentra registrada", 400);
		}

		if (representanteDTO.getIdDistritoFederal() != null && representanteDTO.getIdMunicipio() != null
				&& representanteDTO.getIdEstado() != null && representanteDTO.getIdSeccionElectoral() != null) {
			
			MapperUtil.map(representanteDTO, representante);

			representante.setFechaRegistro(new Date(System.currentTimeMillis()));
			representante.setTipo(tipo);
			representante.setRuta(null);
			representante.setUsuario(idUsuario);
			representante.setDistritoFederal(representanteDTO.getIdDistritoFederal());
			representante.setEstado(representanteDTO.getIdEstado());
			representante.setMunicipio(representanteDTO.getIdMunicipio());
			representante.setSeccionElectoral(representanteDTO.getIdSeccionElectoral());

			System.out.println(representanteDTO);
			System.out.println(representante);
			 representanteRepository.save(representante);
		} else {
			throw new RepresentanteException("No se encontraron datos.", 404);
		}

		return representanteRepository.getIdMax();
	}

}