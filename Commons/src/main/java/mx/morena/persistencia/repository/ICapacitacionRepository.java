package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.entidad.RegistroCapacitacion;

public interface ICapacitacionRepository {
	
	List<Capacitacion> getRepresentanteRcByClave( String claveElector);
	
	List<Capacitacion> getRepresentanteRgByClave( String claveElector);
	
	List<Capacitacion> getRepresentanteByRc(Long tipo);
	
	List<Capacitacion> getRepresentanteByRg(Long tipo);
	
	Long getTipoRepresentante(String claveElector);

	long saveCapacitacion(RegistroCapacitacion rc);

	long updateNombramiento(RegistroCapacitacion rc);
	
	Long getCapacitacionByDfAndRepresentante(Long idEntidad, Long idFederal, Long tipoRepresentante, String tomoCapacitacion);
	
	Long getNombramientoByDfAndRepresentante(Long idEntidad, Long idFederal, Long tipoRepresentante, String tomoCapacitacion, Boolean isNombramiento);

	Long getCountCapacitacionRC(Long idEntidad, Long idFederal, Long perfilRc);

	Long getCountRcNombramiento(Long idEntidad, Long idFederal, Long perfilRc, boolean b);
}
