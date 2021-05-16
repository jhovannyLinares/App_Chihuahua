package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.PartidosReporteCasilla;
import mx.morena.persistencia.entidad.RepresentacionPartidos;
import mx.morena.persistencia.entidad.RepresentacionPartidosReporte;
import mx.morena.persistencia.repository.IPartidoVotacionRepository;
import mx.morena.persistencia.rowmapper.RepresentacionPartidoReporteRowMapper;
import mx.morena.persistencia.rowmapper.RepresentacionPartidoRowMapper;

@Repository
public class PartidoVotacionRepository implements IPartidoVotacionRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<RepresentacionPartidos> getPartidos() {
		String sql = "select * from app_representacion_partidos";
		try {
			return template.queryForObject(sql, new RepresentacionPartidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int save(PartidosReporteCasilla partidos) {
		String sql = "INSERT INTO app_partidos_reporte_casillas (id, id_casilla, id_partido, tiene_representante, tipo_reporte) "
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_partidos_reporte_casillas), 0)+1, ?, ?, ?, ?)";		

		try {
			template.update(sql, new Object[] { partidos.getIdCasilla(), partidos.getIdPartido(),
					partidos.isTieneRepresentante(), partidos.getTipoReporte() });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<RepresentacionPartidosReporte> getPartidosByIdCasillaAndReporte(Long idCasilla, Integer tipoReporte) {
		String sql = "select aprc.id_partido as id, arp.partido, aprc.tiene_representante from app_partidos_reporte_casillas aprc "
				+ "inner join app_representacion_partidos arp on arp.id = aprc.id_partido "
				+ "where aprc.id_casilla = ? and aprc.tipo_reporte = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idCasilla, tipoReporte }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new RepresentacionPartidoReporteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
