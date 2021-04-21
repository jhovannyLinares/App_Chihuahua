package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.persistencia.rowmapper.CapacitacionRowMapper;

@Repository
public class CapacitacionRepository implements ICapacitacionRepository{

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public List<Capacitacion> getRepresentanteByClave(String claveElector) {
		String sql = "SELECT ar.id, ar.clave_elector as clave, atr.tipo_representante as representante, "
				+ "ara.casilla_id as asignado, ac.descripcion as cargo FROM app_representantes ar "
				+ "inner join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "inner join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_representante_cargo arc "
				+ "on ar.id = arc.id_representante "
				+ "left join app_cargos ac "
				+ "on arc.id_cargo = ac.id "
				+ "where ar.clave_elector = ?";
		
		try {
			return template.queryForObject(sql,new Object[] { claveElector }, new int[] { Types.VARCHAR }, new CapacitacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
