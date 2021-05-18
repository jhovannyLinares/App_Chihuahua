package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Incidencias;
import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;
import mx.morena.persistencia.rowmapper.IncidenciaRowMapper;

@Repository
public class IncidenciasCasillasRepository implements IIncidenciasCasillasRepository {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public int save(IncidenciasCasillas ic) {
		
		String sql = "INSERT INTO app_incidencias_casillas (id,  id_casilla,  id_incidencia, tipo_reporte )"
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_incidencias_casillas), 0)+1, ?, ?, ?)";	
//				+ " VALUES ((SELECT MAX(id)+1 FROM app_incidencias_casillas), ?, ?)";	

		try {
			template.update(sql, new Object[] { ic.getIdCasilla(), ic.getIdIncidencia(), ic.getTipoReporte() });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	
	}

	@Override
	public List<Incidencias> getByIdCasilla(Long idCasilla, Integer tipoReporte) {
		String sql = "select aic.id_incidencia as idIncidencia, ai.incidencia from app_incidencias_casillas aic "
				+ "inner join app_incidencias ai on ai.\"idIncidencia\" = aic.id_incidencia "
				+ "where aic.id_casilla = ? and aic.tipo_reporte = ? ";
		
		try {	
			return template.queryForObject(sql, new Object[] { idCasilla, tipoReporte }, new int[] { Types.NUMERIC, Types.NUMERIC }, new IncidenciaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Incidencias> getIncidenciaByIdCasilla(Long idCasilla) {
		String sql = "select aic.id_incidencia as idIncidencia, ai.incidencia from app_incidencias_casillas aic "
				+ "inner join app_incidencias ai on ai.\"idIncidencia\" = aic.id_incidencia "
				+ "where aic.id_casilla = ? and aic.tipo_reporte is null ";
		
		try {	
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC }, new IncidenciaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
