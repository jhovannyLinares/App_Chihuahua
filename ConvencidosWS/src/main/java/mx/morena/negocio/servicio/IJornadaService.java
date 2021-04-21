package mx.morena.negocio.servicio;

import java.util.List;

import mx.morena.negocio.dto.CapacitacionDTO;
import mx.morena.negocio.exception.JornadaException;

public interface IJornadaService {
	
	List<CapacitacionDTO> getRepresentantes(String claveElector, boolean rc, boolean rg) throws JornadaException;

}
