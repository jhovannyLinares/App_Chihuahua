//package mx.morena.persistencia.repository.impl;
//
//import java.sql.Types;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import mx.morena.persistencia.entidad.Localidad;
//import mx.morena.persistencia.repository.ILocalidadRepository;
//import mx.morena.persistencia.rowmapper.LocalidadRowMapper;
//import mx.morena.persistencia.rowmapper.LocalidadesRowMapper;
//
//@Repository
//public class LocalidadRepository implements ILocalidadRepository {
//
//	@Autowired
//	private JdbcTemplate template;
//
//	@Override
//	public Localidad getById(Long localidad) {
//		String sql = "select id,nombre ,tipo  from app_localidad group by id,nombre ,tipo order by id";
//
//		return template.queryForObject(sql, new LocalidadRowMapper());
//	}
//
//	@Override
//	public List<Localidad> getByMunicipio(Long localidad) {
//		String sql = "select id,nombre ,tipo,seccion_id  from app_localidad al where municipio_id = ?  order by id";
//
//		return template.queryForObject(sql, new Object[] { localidad }, new int[] { Types.NUMERIC }, new LocalidadesRowMapper());
//	}
//
//}
