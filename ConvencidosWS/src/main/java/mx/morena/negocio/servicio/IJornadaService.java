package mx.morena.negocio.servicio;

import java.io.IOException;
import java.util.List;

import mx.morena.negocio.dto.CapacitacionDTO;
import mx.morena.negocio.dto.RegistroCapacitacionDTO;
import mx.morena.negocio.exception.JornadaException;

public interface IJornadaService {
	
	List<CapacitacionDTO> getRepresentantes(String claveElector, boolean rc, boolean rg) throws JornadaException;

	String saveCapacitacion(RegistroCapacitacionDTO dto, long perfil, long usuario) throws JornadaException, IOException;

}
