package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Partido;
import mx.morena.persistencia.repository.IPartidosRepository;
import mx.morena.persistencia.rowmapper.PartidosRowMapper;

@Repository
public class PartidosRepositoryImpl implements IPartidosRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Partido> getGobernador(Long entidad) {

		String sql = "SELECT id, entidad_id, partido, candidato, tipo_partido, cargo, a_paterno, a_materno, nombres FROM app_partidos_entidad "
				+ " where entidad_id = ? ";

		try {
			System.out.println(sql);
			return template.queryForObject(sql, new Object[] { entidad }, new int[] { Types.NUMERIC },
					new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getMunicipal(Long municipio) {
		String sql = "SELECT id, clave_municipio, municipio, tipo_partido, partido, cargo, p_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_municipio "
				+ " where clave_municipio = ?";

		try {
			return template.queryForObject(sql, new Object[] { municipio }, new int[] { Types.NUMERIC },
					new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getSindico(Long municipio) {

		String sql = "SELECT id, clave_municipio, municipio, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_sindico  "
				+ " where clave_municipio = ?";

		try {
			return template.queryForObject(sql, new Object[] { municipio }, new int[] { Types.NUMERIC },
					new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Partido> getDiputadoLocal(Long federal) {
		String sql = "SELECT id, distrito_federal, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_diputado_local "
				+ " where distrito_federal = ?";

		try {
			return template.queryForObject(sql, new Object[] { federal }, new int[] { Types.NUMERIC },
					new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getDiputadoFederal(Long federal) {
		
		String sql = "SELECT id, distrito_federal, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_diputado_federal "
				+ " where distrito_federal = ?";

		try {
			return template.queryForObject(sql, new Object[] { federal }, new int[] { Types.NUMERIC },
					new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Partido>();
		}
	}

	@Override
	public List<Partido> getGobernador() {

		String sql = "SELECT id, entidad_id as ubicacion, partido, candidato, tipo_partido, cargo, a_paterno, a_materno, nombres FROM app_partidos_entidad ";

		try {
			System.out.println(sql);
			return template.queryForObject(sql, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getMunicipal() {
		String sql = "SELECT id, clave_municipio as ubicacion, municipio, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_municipio ";

		try {
			return template.queryForObject(sql, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getSindico() {

		String sql = "SELECT id, clave_municipio as ubicacion, municipio, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_sindico  ";

		try {
			return template.queryForObject(sql, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Partido> getDiputadoLocal() {
		String sql = "SELECT id, distrito_federal as ubicacion, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_diputado_local ";

		try {
			return template.queryForObject(sql, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getDiputadoFederal() {

		String sql = "SELECT id, distrito_federal as ubicacion, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ " FROM app_partidos_diputado_federal ";

		try {
			return template.queryForObject(sql, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Partido>();
		}
	}

	@Override
	public List<Partido> getGobernadorByEntidad(Long entidad) {
		String sql = "SELECT id, entidad_id as ubicacion, partido, candidato, tipo_partido, cargo, a_paterno, a_materno, nombres "
				+ "FROM app_partidos_entidad where entidad_id = ? ";

		try {
			System.out.println(sql);
			return template.queryForObject(sql, new Object[] { entidad }, new int[] { Types.NUMERIC }, new PartidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getMunicipalByMunicipio(Long municipio) {
		String sql = "SELECT id, clave_municipio as ubicacion, municipio, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ "FROM app_partidos_municipio where clave_municipio = ? ";

		try {
			return template.queryForObject(sql, new Object[] { municipio }, new int[] { Types.NUMERIC }, new PartidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getSindicoByMunicipio(Long municipio) {
		String sql = "SELECT id, clave_municipio as ubicacion, municipio, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ "FROM app_partidos_sindico where clave_municipio = ? ";

		try {
			return template.queryForObject(sql, new Object[] { municipio }, new int[] { Types.NUMERIC },new PartidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getDiputadoLocalByFederal(Long federal) {
		String sql = "SELECT id, distrito_federal as ubicacion, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ "FROM app_partidos_diputado_local where distrito_federal = ? ";

		try {
			return template.queryForObject(sql, new Object[] { federal }, new int[] { Types.NUMERIC }, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Partido> getDiputadoFederalByFederal(Long federal) {
		String sql = "SELECT id, distrito_federal as ubicacion, tipo_partido, partido, cargo, a_paterno, a_materno, nombres, candidato, id_cargo "
				+ "FROM app_partidos_diputado_federal where distrito_federal = ? ";

		try {
			return template.queryForObject(sql, new Object[] { federal }, new int[] { Types.NUMERIC }, new PartidosRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Partido>();
		}
	}
}
