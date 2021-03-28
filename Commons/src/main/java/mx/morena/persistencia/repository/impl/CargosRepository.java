package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Cargo;
import mx.morena.persistencia.repository.ICargosRepository;
import mx.morena.persistencia.rowmapper.CargosRowMapper;

@Repository
public class CargosRepository implements ICargosRepository {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<Cargo> getCargos(Long tipoRepresentante) {
		String sql = "select * from app_representante_cargo arc inner join app_cargos ac on arc.id_cargo =ac.id where arc.id_representante = ?";
		try {
			return template.queryForObject(sql, new Object[] { tipoRepresentante }, new int[] { Types.NUMERIC }, new CargosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

//	@Override
//	public List<Zona> getZonas(Long idFederal) { 
//		String sql = "select * from app_zonas where distrito_federal_id = ?";
//		try {
//			return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC }, new ZonasRowMapper());
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}
	
//	@Override
//	public Convencidos saveZona(Long id, Long tipo) {
//		String sql = "INSERT INTO app_zonas (distrito_federal_id, nombre_distrito, zona_crg, id_zona_crg, id, id_usuario) VALUES(?, ?, ?, ?, ?, ?)";
//		
//			return template.update(sql, new Object[] { distrito_federal_id, nombre_distrito,zona_crg,id_zona_crg,id,id_usuario }, new int[] { Types.NUMERIC, Types.VARCHAR,Types.NUMERIC, Types.VARCHAR, Types.VARCHAR,Types.NUMERIC }
//					);
//		
//	}


}
