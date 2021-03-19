package mx.morena.negocio.servicios.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}