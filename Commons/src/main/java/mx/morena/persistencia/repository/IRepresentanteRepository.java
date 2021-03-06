package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.ReporteAsignacionRg;
import mx.morena.persistencia.entidad.RepresentanteClaveElectoral;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;


public interface IRepresentanteRepository {

	Representantes findByClaveElector(String claveElector);
	
	void save (Representantes representantes);
	
	Long getIdMax();
	
	public Representantes getById(Long idCrg);
	
	List<Representantes> getAllCrg(Long tipoRepresentante);
	
	List<Perfil> getAllTipoRep();
	
	List<RepresentanteClaveElectoral> getAllRepresentantes(String claveElector);
	
	List<ReporteAsignacionRg> getReporteRg(Long idDistrito);
	
	List<ReporteAsignacionRg> getReporteRgEstatal();
	
	Long getCargo(Long idDistrito, Long cargo);

	Long asignaRepresentante(RepresentantesAsignados representante);

	Long getIdMaxAsignados();

	Long updateRepresentante(long perfil, RepresentantesAsignados representante, long asignacion);

	Representantes getRepresentante(Long representanteId);

	Long updateStatusRepresentantes(Long id);
	
	Long getRcCaptura(Long perfil);
	
	Long getRcAsignados(Long perfil);

	Long getByTipo(Long perfil, Long idDistrito, Long perfilUsuario);

	Long getRepAsignadoByTipo(Long perfil, Long idDistrito, Long perfilUsuario);

	Long getByDistritoAndTipo(Long id, Long perfil);

	Long getRepAsignadoByDistrito(Long id, Long perfil);

	Representantes getRepresentanteByIdAndTipo(Long representanteId, Long tipoRepresentante);
	
	List<ReporteAsignacionRg> getDistritosAndMunicipios(Long idEntidad, Long idDistritoF, Long idDistritoL, Long idMunicipio);
	
	Long getRepCrgAsignadoByDistrito(Long idEntidad, Long idDistrito, Long idMunicipio, Long perfil);
	
	List<Long> getRutasByCrgAsignadoAndDistrito(Long idEntidad, Long idDistritoF, Long idDistritoL, Long idMunicipio, Long perfil);
	
	Long getCargoByDistritoAndMunicipio(Long idDistritoF, Long idDistritoL, Long idMunicipio, Long cargo);
}
