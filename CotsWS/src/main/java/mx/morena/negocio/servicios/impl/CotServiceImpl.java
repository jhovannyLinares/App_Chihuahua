package mx.morena.negocio.servicios.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.CotDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.ICotService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;

@Service
public class CotServiceImpl implements ICotService {

	@Autowired
	private IConvencidosRepository cotRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	private static final Integer PERFIL_ESTATAL = 1;
	private static final Integer PERFIL_FEDERAL = 2;
	private static final Integer PERFIL_MUNICIPAL = 4;
	private static final char ESTATUS_ALTA = 'A';

	@Override
	public Long save(CotDTO cotDto, long perfil, long idUsuario) throws CotException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			Convencidos existeCurp = cotRepository.getByCurp(cotDto.getCurp());
			List<Convencidos> existeClave = cotRepository.findByClaveElector(cotDto.getClaveElector());

			if (existeClave != null) {
				throw new CotException("La clave de elector ya esta en uso, intente con otra", 400);
			} else if (existeCurp != null) {
				throw new CotException("La CURP ya esta en uso, intente con otra", 400);
			} else {
				Convencidos personaCot = new Convencidos();

				cotDto.setFechaRegistro(new Date(System.currentTimeMillis()));
				cotDto.setEstatus(ESTATUS_ALTA);
				MapperUtil.map(cotDto, personaCot);

				personaCot.setEstado(cotDto.getIdEstado());
				personaCot.setDistritoFederal(cotDto.getIdDistritoFederal());
				personaCot.setMunicipio(cotDto.getIdMunicipio());
				personaCot.setUsuario(idUsuario);

				System.out.println(personaCot);
				cotRepository.save(personaCot);

				return personaCot.getId();
			}

		} else {
			throw new CotException("No cuenta con suficientes permisos", 401);
		}

	}

	@Override
	public String asignarSecciones(List<String> idSecciones, Long idCot, long perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			List<SeccionElectoral> secciones = seccionRepository.findAllById(idSecciones);
			Convencidos cot = cotRepository.getByIdAndEstatus(idCot, ESTATUS_ALTA);

			if (secciones != null && cot != null) {
				for (SeccionElectoral sec : secciones) {

					if (sec.getCot() == null) {
						sec.setCot(cot);
						System.out.println("asignarSecciones:");
						System.out.println(sec);
						seccionRepository.save(sec);
					} else {
						throw new CotException("La sección " + sec.getDescripcion() + " ya la tiene asignada otro COT.",
								400);
					}

				}

				return "Secciones asignadas al COT";

			} else {
				throw new CotException("No se encontraron datos.", 404);
			}
		} else {
			throw new CotException("No cuenta con suficientes permisos.", 401);
		}
	}

}