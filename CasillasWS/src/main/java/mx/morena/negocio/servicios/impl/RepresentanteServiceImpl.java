package mx.morena.negocio.servicios.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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
					representante.setRuta(null);
					representante.setUsuario(idUsuario);
					representante.setDistritoFederal(representanteDTO.getIdDistritoFederal());
					representante.setEstado(representanteDTO.getIdEstado());
					representante.setMunicipio(representanteDTO.getIdMunicipio());
					representante.setSeccionElectoral(representanteDTO.getIdSeccionElectoral());

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

}