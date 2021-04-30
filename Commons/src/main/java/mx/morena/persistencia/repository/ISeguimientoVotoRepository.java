package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.entidad.SeccionElectoral;

public interface ISeguimientoVotoRepository {
	
	Long countByDistAndTipo(Long distritoId, Long tipo);
	
	Long countByLocalAndTipo(Long perdil, Long tipo);
	
	Long countByNotificados(Long distrito, Long tipo);
	
	Long countNotificados(Long distritoId);

	List<SeccionElectoral> getDistritos();
	
	List<Convencidos> getConvencidos(Long idSeccion);
	
	Long getIdLocal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getIdFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getIdMunicipal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	String getNomMunicipal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasByDistrito(Long idDistritoFederal);
	
	Long getCasillasInstaladas1(Long idDistritoFederal);
	
	Long getCasillasInstaladas2(Long idDistritoFederal);
	
	Long getCasillasInstaladas3(Long idDistritoFederal);
	
	Long getCasillasInstaladas4(Long idDistritoFederal);
	
	Long getCasillasInstaladas5(Long idDistritoFederal);
	
	Long getCasillasInstaladas6(Long idDistritoFederal);
	
	Long getTotalCasillasInstaladas(Long idDistritoFederal);
	
	Long getCasillasByLocal(Long idDistritoFederal);
	
	Long getCasillasInstaladas1L(Long idDistritoFederal);
	
	Long getCasillasInstaladas2L(Long idDistritoFederal);
	
	Long getCasillasInstaladas3L(Long idDistritoFederal);
	
	Long getCasillasInstaladas4L(Long idDistritoFederal);
	
	Long getCasillasInstaladas5L(Long idDistritoFederal);
	
	Long getCasillasInstaladas6L(Long idDistritoFederal);
	
	Long getTotalCasillasInstaladasL(Long idDistritoFederal);
	
Long getCasillasByMunicipal(Long idDistritoFederal);
	
	Long getCasillasInstaladas1M(Long idDistritoFederal);
	
	Long getCasillasInstaladas2M(Long idDistritoFederal);
	
	Long getCasillasInstaladas3M(Long idDistritoFederal);
	
	Long getCasillasInstaladas4M(Long idDistritoFederal);
	
	Long getCasillasInstaladas5M(Long idDistritoFederal);
	
	Long getCasillasInstaladas6M(Long idDistritoFederal);
	
	Long getTotalCasillasInstaladasM(Long idDistritoFederal);
	
	String getNombreMunicipio(Long idDistritoFederal);
	
Long getCasillasByDistritoFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas1Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas2Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas3Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas4Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas5Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getCasillasInstaladas6Federal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	Long getTotalCasillasInstaladasFederal(Long idDistritoFederal, Long idDistritoLocal, Long idMunicipal);
	
	List<Rutas> findByFederal(Long idFederal);
	
	Long getCasillasByRuta(Long ruta);
	
	List<SeccionElectoral> getSeccionByDistrito(Long df);
	
	String getTipoCasilla( Long seccion);
	
	Long getCasillasInstaladas1Crg(Long idRuta);
	
	Long getCasillasInstaladas2Crg(Long idRuta);
	
	Long getCasillasInstaladas3Crg(Long idRuta);
	
	Long getCasillasInstaladas4Crg(Long idRuta);
	
	Long getCasillasInstaladas5Crg(Long idRuta);
	
	Long getCasillasInstaladas6Crg(Long idRuta);
	
	Long getTotalCasillasInstaladasCrg(Long idRuta);

}
