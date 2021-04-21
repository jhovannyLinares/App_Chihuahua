package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.CapacitacionDTO;
import mx.morena.negocio.dto.RegistroCapacitacionDTO;
import mx.morena.negocio.exception.JornadaException;
import mx.morena.negocio.servicio.IJornadaService;
import mx.morena.negocio.util.MapperUtil;
import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.entidad.RegistroCapacitacion;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class JornadaServiceImpl extends MasterService implements IJornadaService{
	
	@Autowired
	private ICapacitacionRepository capacitacionRepository;

	@Override
	public List<CapacitacionDTO> getRepresentantes(String claveElector, boolean rc, boolean rg)
			throws JornadaException {
		
		List<CapacitacionDTO> lstCapDTO = null;
		List<Capacitacion> lstCap = null;
		
		if (claveElector != null) {
			if(claveElector.length() == 18) {
				
				Long tipo = capacitacionRepository.getTipoRepresentante(claveElector);
				
				if(tipo == PERFIL_RC) {
					lstCap = capacitacionRepository.getRepresentanteRcByClave(claveElector);
				}else if(tipo == PERFIL_RG) {
					lstCap = capacitacionRepository.getRepresentanteRgByClave(claveElector);
				}else {
					throw new JornadaException("La clave de elector ingresada no pertenece a los perfiles establecidos", 400);
				}
					lstCapDTO = MapperUtil.mapAll(lstCap, CapacitacionDTO.class);	
				
				return lstCapDTO;
			}else {
				throw new JornadaException("El numero de caracteres ingresado en la clave de elector es incorrecto", 400);
			}
		}
		if(rc == false && rg == false) {
			throw new JornadaException("Debe ingresar la clave de elector", 400);
		}
		
		if (rc == true) {

				lstCap = capacitacionRepository.getRepresentanteByRc(PERFIL_RC);
				
				if(lstCap != null) {
					
					lstCapDTO = MapperUtil.mapAll(lstCap, CapacitacionDTO.class);	
					
				}else {
						throw new JornadaException("No se encontraron Registros para mostrar", 400);
					}
				
				return lstCapDTO;
		}
			
		
		if (rg == true) {
			
			lstCap = capacitacionRepository.getRepresentanteByRg(PERFIL_RG);
			
			if(lstCap != null) {
				
				lstCapDTO = MapperUtil.mapAll(lstCap, CapacitacionDTO.class);	
				
			}else {
					throw new JornadaException("No se encontraron Registros para mostrar", 400);
				}
		}
		return lstCapDTO;
	}

	@Override
	@Transactional(rollbackFor={JornadaException.class})
	public String saveCapacitacion(RegistroCapacitacionDTO dto, long perfil, long usuario)
			throws JornadaException, IOException {
		
		RegistroCapacitacion rc = new RegistroCapacitacion();
				
		MapperUtil.map(dto, rc);
//		capacitacionRepository.saveCapacitacion(rc);
		if(capacitacionRepository.saveCapacitacion(rc) == 0) {
		 throw new JornadaException("No se guardo la informacion con exito", 409);	
		}
		
		
		return "Se registro la capacitacion del representante con id " + dto.getIdRepresentante();
	}

}
