package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Casilla;
import mx.morena.persistencia.repository.ICasillaRepository;
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

}
