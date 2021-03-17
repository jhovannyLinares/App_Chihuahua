package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

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

					personaCot.setFechaRegistro(new Date(System.currentTimeMillis()));
					personaCot.setEstatus(ESTATUS_ALTA);
					personaCot.setFechaSistema(new Timestamp(new Date().getTime()));
					personaCot.setEstado(cotDto.getIdEstado());
					personaCot.setDistritoFederal(cotDto.getIdDistritoFederal());
					personaCot.setMunicipio(cotDto.getIdMunicipio());
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
	public String asignarSecciones(List<Long> idSecciones, Long idCot, long perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {

			for (Long idSeccion : idSecciones) {

				SeccionElectoral se = seccionRepository.findById(idSeccion);

				if (se.getCot().equals(0l)) {
					seccionRepository.updateIdCot(idSeccion, idCot);
				}

			}

		} else {
			throw new CotException("No cuenta con suficientes permisos.", 401);
		}

		return "Secciones asignadas al COT";

	}

	@Override
	public String suspender(Long idCot, long perfil, long idUsuario) throws CotException {

		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {

			Convencidos cot = cotRepository.getByIdAndEstatus(idCot, ESTATUS_ALTA, COT);

			if (cot != null) {
				List<SeccionElectoral> secciones = seccionRepository.findByCotId(idCot, COT);

				cotRepository.updateStatusCot(idCot, ESTATUS_SUSPENDIDO, new Date(System.currentTimeMillis()), COT);

				if (secciones != null) {
					seccionRepository.updateIdCot(0l, idCot);
					System.out.println("Se han liberado las secciones del COT");
				} else {
					System.out.println("No hay secciones por liberar");
				}

				return "COT suspendido";

			} else {
				throw new CotException("No se encontraron datos.", 404);
			}

		} else {
			throw new CotException("Permisos insuficientes.", 401);
		}
	}

	@Override
	public String activar(Long idCot, long perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			
			cotRepository.updateStatusCot(idCot, ESTATUS_ALTA, new Date(System.currentTimeMillis()), COT);
			
			return "COT activo";
		} else {
			throw new CotException("Permisos insuficientes.", 401);
		}
	}

}