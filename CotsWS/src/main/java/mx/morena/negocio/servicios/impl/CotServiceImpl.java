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
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;

@Service
public class CotServiceImpl implements ICotService {

	@Autowired
	private IConvencidosRepository cotRepository;
	
	@Autowired
	private ISeccionElectoralRepository seccionRepository;
	
	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	private static final Integer PERFIL_ESTATAL = 1;
	private static final Integer PERFIL_FEDERAL = 2;
	private static final Integer PERFIL_MUNICIPAL = 4;
	
	@Override
	public String save(CotDTO cotDto, int perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			boolean existeCurp = cotRepository.existsByCurp(cotDto.getCurp());
			boolean existeClave = cotRepository.existsByClaveElector(cotDto.getClaveElector());

			if (existeClave) {
				throw new CotException("La clave de elector ya esta en uso, intente con otra", 400);
			} else if (existeCurp) {
				throw new CotException("La CURP ya esta en uso, intente con otra", 400);
			}
			
			if (!existeCurp && !existeClave) {
				Convencidos personaCot = new Convencidos();
				//Solo se reciben id, por lo tanto se tiene que hacer al busqueda
				DistritoFederal distritoF = distritoFederalRepository.findById(cotDto.getIdDistritoFederal()).get();
				Municipio municipio = municipioRepository.findById(cotDto.getIdMunicipio()).get();
				
				if(distritoF != null && municipio != null) {
					cotDto.setFechaRegistro(new Date(System.currentTimeMillis()));
					cotDto.setEstatus('A');
					MapperUtil.map(cotDto, personaCot);
					
					personaCot.setEstado(distritoF.getEntidad());
					personaCot.setDistritoFederal(distritoF);
					personaCot.setMunicipio(municipio);;
					System.out.println(personaCot);
					cotRepository.save(personaCot);
				} else {
					throw new CotException("No se encontraron datos.", 404);
				}
				
				//ArrayList<Long> secciones = new ArrayList<Long>();
				//secciones.add(1L);
				//secciones.add(191L);
				//secciones.add(192L);
				// asignarSecciones(1L, secciones, personaCot.getId());// personaCot.getId());

				return "Cot " + cotDto.getNombre() + " " + cotDto.getaPaterno() + " guardado.";
			}

		} else {
			throw new CotException("No cuenta con suficientes permisos", 401);
		}

		return null;
	}

	@Override
	public String asignarSecciones(List<Long> idSecciones, Long idCot, int perfil) throws CotException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			List<SeccionElectoral> secciones = seccionRepository.findAllById(idSecciones);
			Convencidos cot = cotRepository.findById(idCot).get();

			if (secciones != null && cot != null) {
				for (SeccionElectoral sec : secciones) {

					if (sec.getCot() == null) {
						sec.setCot(cot);
						System.out.println("asignarSecciones:");
						System.out.println(sec);
						seccionRepository.save(sec);
					} else {
						throw new CotException("La sección " + sec.getDescripcion() + " ya la tiene asignada otro COT.", 400);
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
