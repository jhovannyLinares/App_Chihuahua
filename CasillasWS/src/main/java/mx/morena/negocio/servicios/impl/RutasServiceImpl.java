package mx.morena.negocio.servicios.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.CatalogoCrgDTO;
import mx.morena.negocio.dto.RutasResponseDTO;
import mx.morena.negocio.exception.RutasException;
import mx.morena.negocio.servicios.IRutasService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.repository.IRutasRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class RutasServiceImpl extends MasterService implements IRutasService{

	@Autowired
	private IRutasRepository rutasRepository;
	
	@Override
	public List<CatalogoCrgDTO> getCatalogo(Long perfil) throws RutasException{
		
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_MUNICIPAL) {
			List<Representantes> crg = rutasRepository.getAllCrg(REP_CRG);
				
			if (crg != null) {
				return crg.stream().map(this::convertirADto).collect(Collectors.toList());
			} else {
				return null;
			}
		} else {
			throw new RutasException("Permisos insuficientes.", 401);
		}
	}
	
	public CatalogoCrgDTO convertirADto(Representantes crg) {
		CatalogoCrgDTO crgDto = new CatalogoCrgDTO();
		
		crgDto.setNombre(crg.getNombre() + " " + crg.getApellidoPaterno() + " " + crg.getApellidoMaterno());
		crgDto.setTipo(crg.getTipo());

		
		return crgDto;
	}

	@Override
	@Transactional(rollbackFor={RutasException.class})
	public String asignarRutas(List<Long> idRutas, Long idCrg, long perfil) throws RutasException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {
			
			Rutas crg = rutasRepository.getByIdAndEstatus(idCrg, ESTATUS_ALTA, COT);
			
			if (crg != null) {

			List<Rutas> rutas = rutasRepository.findByCrgId(idCrg, COT);

			if (rutas != null) {
				for (Rutas ruta : rutas) {
					rutasRepository.updateIdCrg(ruta.getId(), 0l);
				}
			}

			for (Long idRuta : idRutas) {

					List<Rutas> rutasCrg = rutasRepository.findById(idRuta);

					for (Rutas rut : rutasCrg) {

						if (rut.getRuta().equals(0l)) {
							rutasRepository.updateIdCrg(idRuta, idCrg);
						} else {
							throw new RutasException("La ruta " + rut.getId() + " ya la tiene asignada otro Crg", 400);
						}

					}

				}

			} else {
				throw new RutasException("No se encontro el Crg ingresado", 404);
			}

		} else {
			throw new RutasException("No cuenta con suficientes permisos", 401);
		}

		return "Se asignaron rutas al Crg";
	}

	@Override
	public List<RutasResponseDTO> getRutas(Long idFederal, Long zonaCRG, Long ruta, Long casilla, Long perfil)
			throws RutasException {
		if (perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL) {

			List<RutasResponseDTO> lstRutasDTO = null;
			List<Rutas> lstRutas = null;

				lstRutas = rutasRepository.getRutas(idFederal, zonaCRG, ruta, casilla);	

			if (lstRutas != null) {
				lstRutasDTO = MapperUtil.mapAll(lstRutas, RutasResponseDTO.class);
				return lstRutasDTO;
			}else {
				throw new RutasException("No se encontraron serultados con los datos ingresados", 401);
			}
		} else {
			throw new RutasException("No cuenta con permisos suficientes.", 401);
		}
	}

}
