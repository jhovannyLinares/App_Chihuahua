package mx.morena.negocio.servicios;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.morena.negocio.dto.EntidadDTO;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.DistritoLocal;
import mx.morena.persistencia.entidad.Localidad;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;

public interface ICatalogoService {
	
	List<DistritoFederal> getFederalByEntidad(HttpServletResponse response, Long id);
	
	List<DistritoLocal> getLocalByFederal(HttpServletResponse response, Long id);
	
	List<Municipio> getMunicipioByLocal(HttpServletResponse response, Long id);
	
	List<Localidad> getLocalidadByMunicipio(HttpServletResponse response, Long id);
	
	List<SeccionElectoral> getSeccionByLocalidad(HttpServletResponse response, Long id);

	List<EntidadDTO> getEntidades();

}
