package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.PartidosReporteCasilla;
import mx.morena.persistencia.entidad.RepresentacionPartidos;
import mx.morena.persistencia.entidad.RepresentacionPartidosReporte;

public interface IPartidoVotacionRepository {

	List<RepresentacionPartidos> getPartidos();
	
	int save(PartidosReporteCasilla partidos);
	
	List<RepresentacionPartidosReporte> getPartidosByIdCasillaAndReporte(Long idCasilla, Integer tipoReporte);
}
