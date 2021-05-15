package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.PartidosReporteCasilla;
import mx.morena.persistencia.entidad.RepresentacionPartidos;

public interface IPartidoVotacionRepository {

	List<RepresentacionPartidos> getPartidos();
	
	int save(PartidosReporteCasilla partidos);
	
	List<RepresentacionPartidos> getPartidosByIdCasillaAndReporte(Long idCasilla, Integer tipoReporte);
}
