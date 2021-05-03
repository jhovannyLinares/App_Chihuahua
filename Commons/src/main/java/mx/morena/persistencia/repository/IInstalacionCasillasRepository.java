package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.InstalacionCasilla;

public interface IInstalacionCasillasRepository {

	int save(InstalacionCasilla ic);

	Long getMaxId();

	List<InstalacionCasilla> getById(Long idCasilla);

	Long getCountRgByDfAndAsistencia(Long idFederal, String SI);

	Long getCountRcByDfAndAsistencia(Long idFederal, String SI);

	Long getCountRgByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo, Long idMunicipio);

	Long getCountRcByLocalAndAsistencia(Long local, String SI, Long idFederal, Long tipo, Long idMunicipio);

	Long getCountRgByLocalAndAsistenciaCrg(String SI,Long idCrg, Long casillaRuta, Long tipo, Long idFederal, Long municipio);

	Long getCountRcByLocalAndAsistenciaCrg(String SI, Long idCrg, Long casillaRuta, Long tipo, Long idFederal, Long municipio);

	Long getCountRcByRutaRg(Long local, String SI, String idRutaRg, Long seccion, Long tipo, String tipoCasilla);

}
