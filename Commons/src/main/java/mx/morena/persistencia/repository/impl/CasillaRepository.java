package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.AsignacionCasillas;
import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.rowmapper.AsignacionCasillasRowMapper;
import mx.morena.persistencia.rowmapper.CasillasRowMapper;
import mx.morena.persistencia.rowmapper.CountCasillasRowMapper;

@Repository
public class CasillaRepository implements ICasillaRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Casilla> getCasillas(Long entidad) {

		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla";

		return template.queryForObject(sql, new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillas(Long entidad, Long federal) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { federal, }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());

	}

	@Override
	public List<Casilla> getCasillas(Long entidad, Long federal, Long municipio) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? and municpio_id = ? ";

		return template.queryForObject(sql, new Object[] { federal, municipio },
				new int[] { Types.NUMERIC, Types.NUMERIC }, new CasillasRowMapper());

	}

	@Override
	public List<Casilla> getCasillasFederal(Long distritoFederalId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where federal_id = ? ";

		return template.queryForObject(sql, new Object[] { distritoFederalId, }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillasMunicipio(Long municipioId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada  FROM app_casilla"
				+ " where  municpio_id = ? ";

		return template.queryForObject(sql, new Object[] { municipioId }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public List<Casilla> getCasillasSeccion(Long seccionId) {
		String sql = "SELECT id, federal_id, local_id, municpio_id, seccion_id, tipo_casilla, tipologia, tipo_domicilio, calle, numero, colonia, cp, ubicacion, referencia, is_asignada FROM app_casilla"
				+ " where  seccion_id = ? ";

		return template.queryForObject(sql, new Object[] { seccionId }, new int[] { Types.NUMERIC },
				new CasillasRowMapper());
	}

	@Override
	public Long countByDistritoAndTipologia(Long distritoId, String tipologia) {
		String sql = "select count(*) from app_casilla ac where federal_id = ? and tipologia = ?";
		return template.queryForObject(sql, new Object[] {distritoId, tipologia}, new int[] {Types.NUMERIC, Types.VARCHAR},
				new CountCasillasRowMapper());
	}

	@Override
	public Long countByLocalAndTipologia(Long localId, String tipologia) {
		String sql = "select count(*) from app_casilla ac where local_id = ? and tipologia = ?";
		return template.queryForObject(sql, new Object[] {localId, tipologia}, new int[] {Types.NUMERIC, Types.VARCHAR},new CountCasillasRowMapper());
	}
	
	@Override
	public Long countByMunicipioAndTipologia(Long idMunicipio, String tipologia) {
		String sql = "select count(*) from app_casilla ac where ac.municpio_id = ? and ac.tipologia = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, tipologia}, new int[] { Types.NUMERIC, Types.VARCHAR },
					new CountCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		
		}
	}

	@Override
	public List<AsignacionCasillas> getCasillasByRuta(Long entidad, Long idDistritoF, Long idRuta) {
		String sql = " SELECT id, distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, ruta, id_casilla, tipo_casilla,"
				+ " seccion_id, status, id_ruta_rg, id_crg FROM app_asignacion_casillas where ruta = ? and ruta != 0 ";
		try {
			return template.queryForObject(sql, new Object[] { idRuta }, new int[] { Types.NUMERIC },
				new AsignacionCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		
		}
	}

	@Override
	public List<AsignacionCasillas> getCasillasById(Long entidad, Long idCasilla) {
		String sql = " SELECT id, distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, ruta, id_casilla, tipo_casilla, "
				+ "seccion_id, status, id_ruta_rg, id_crg FROM app_asignacion_casillas where id_casilla = ? and ruta != 0 ";
		try {
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
				new AsignacionCasillasRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		
		}
	}

}
