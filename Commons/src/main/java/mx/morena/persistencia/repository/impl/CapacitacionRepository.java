package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.entidad.RegistroCapacitacion;
import mx.morena.persistencia.repository.ICapacitacionRepository;
import mx.morena.persistencia.rowmapper.CapacitacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class CapacitacionRepository implements ICapacitacionRepository{

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public List<Capacitacion> getRepresentanteRcByClave(String claveElector) {
		String sql = "SELECT ar.id, ar.clave_elector as clave, atr.tipo_representante as representante, "
				+ "ara.casilla_id as asignado, ac.descripcion as cargo, arc3.is_nombramiento as nombramiento FROM app_representantes ar "
				+ "inner join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "inner join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_registro_capacitacion arc3 " 
				+ "on ar.id = arc3.id_representante "
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
	
	@Override
	public List<Capacitacion> getRepresentanteRgByClave(String claveElector) {
		String sql = "SELECT ar.id, ar.clave_elector as clave, atr.tipo_representante as representante, "
				+ "ara.ruta_id as asignado, ac.descripcion as cargo, arc3.is_nombramiento as nombramiento FROM app_representantes ar "
				+ "inner join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "inner join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_registro_capacitacion arc3 " 
				+ "on ar.id = arc3.id_representante "
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

	@Override
	public List<Capacitacion> getRepresentanteByRc(Long tipo) {
		String sql = "SELECT ar.id, ar.clave_elector as clave, atr.tipo_representante as representante, "
				+ "ara.casilla_id as asignado, ac.descripcion as cargo, arc3.is_nombramiento as nombramiento FROM app_representantes ar "
				+ "inner join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "inner join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_registro_capacitacion arc3 " 
				+ "on ar.id = arc3.id_representante "
				+ "left join app_representante_cargo arc "
				+ "on ar.id = arc.id_representante "
				+ "left join app_cargos ac "
				+ "on arc.id_cargo = ac.id "
				+ "where ar.tipo_representante = ? group by atr.tipo_representante, ar.id, ara.casilla_id, ac.descripcion, arc3.is_nombramiento";
		
		try {
			return template.queryForObject(sql,new Object[] { tipo }, new int[] { Types.INTEGER }, new CapacitacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Capacitacion> getRepresentanteByRg(Long tipo) {
		String sql = "SELECT ar.id, ar.clave_elector as clave, atr.tipo_representante as representante, "
				+ "ara.ruta_id as asignado, ac.descripcion as cargo, arc3.is_nombramiento as nombramiento FROM app_representantes ar "
				+ "inner join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "inner join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_registro_capacitacion arc3 " 
				+ "on ar.id = arc3.id_representante "
				+ "left join app_representante_cargo arc "
				+ "on ar.id = arc.id_representante "
				+ "left join app_cargos ac "
				+ "on arc.id_cargo = ac.id "
				+ "where ar.tipo_representante = ? group by atr.tipo_representante, ar.id, ara.ruta_id, ac.descripcion, arc3.is_nombramiento";
		
		try {
			return template.queryForObject(sql,new Object[] { tipo }, new int[] { Types.INTEGER }, new CapacitacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getTipoRepresentante(String claveElector) {
		String sql = "select tipo_representante from app_representantes ar "
				+ "where clave_elector = ?";
		
		try {
			return template.queryForObject(sql,new Object[] { claveElector }, new int[] { Types.VARCHAR }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public long saveCapacitacion(RegistroCapacitacion rc) {
		String sql = "INSERT INTO app_registro_capacitacion "  
				+ "(id, id_representante, tomo_capacitacion, fecha_capacitacion, hora_capacitacion, lugar_capacitacion, calle, numero_interior, numero_exterior, colonia, municipio)"
				+ "VALUES ((SELECT MAX(id)+1 FROM app_registro_capacitacion), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			template.update(sql, new Object[] {rc.getIdRepresentante(), rc.getTomoCapacitacion(), rc.getFechaCapacitaion(), rc.getHoraCapacitacion(), 
					rc.getLugarCapacitacion(), rc.getCalle(), rc.getNumInt(), rc.getNumExt(), rc.getColonia(), rc.getMunicipio()});
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public long updateNombramiento(RegistroCapacitacion rc) {
		String sql = "update app_registro_capacitacion set is_nombramiento = ? where id_representante = ?";

		try {
			template.update(sql, new Object[] { rc.getIsNombramiento(), rc.getIdRepresentante() },
					new int[] { Types.BOOLEAN, Types.NUMERIC });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Long getCapacitacionByDfAndRepresentante(Long idEntidad, Long idFederal, Long tipoRepresentante, String tomoCapacitacion) {
		String sql = " select count(*) from app_representantes ar inner join app_registro_capacitacion arc on arc.id_representante = ar.id "
				+ "inner join app_representantes_asignados ara on ar.id = ara.representante_id "
				+ "where ara.entidad_id = ? and ara.distrito_federal_id = ? and ar.tipo_representante = ? and arc.tomo_capacitacion = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, idFederal, tipoRepresentante, tomoCapacitacion }, 
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getNombramientoByDfAndRepresentante(Long idEntidad, Long idFederal, Long tipoRepresentante, String tomoCapacitacion, Boolean isNombramiento) {
		String sql = " select count(*) from app_representantes ar inner join app_registro_capacitacion arc on arc.id_representante = ar.id "
				+ "inner join app_representantes_asignados ara on ar.id = ara.representante_id "
				+ "where ara.entidad_id = ? and ara.distrito_federal_id = ? and ar.tipo_representante = ? "
				+ "and arc.tomo_capacitacion = ? and arc.is_nombramiento = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idEntidad, idFederal, tipoRepresentante, tomoCapacitacion, isNombramiento }, 
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.BOOLEAN }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getCountCapacitacionRC(Long idEntidad, Long idFederal, Long perfilRc) {
		String sql = "select count(*) from app_registro_capacitacion arc "  
				+ "inner join app_representantes ara "  
				+ " on arc.id_representante = ara.id where ara.tipo_representante = ?" //;
				+ " and ara.estado_id = ? and ara.distrito_federal_id = ?";
		try {
			return template.queryForObject(sql, new Object[] {perfilRc, idEntidad, idFederal}, 
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getCountRcNombramiento(Long idEntidad, Long idFederal, Long perfilRc, boolean b) {
		String sql = "select count(*) from app_registro_capacitacion arc " 
				+ "inner join app_representantes ara "  
				+ "on arc.id_representante = ara.id where ara.tipo_representante = ? and arc.is_nombramiento = ?" //;
				+ " and ara.estado_id = ? and ara.distrito_federal_id = ?";
		try {
			return template.queryForObject(sql, new Object[] {perfilRc, b, idEntidad, idFederal}, 
					new int[] { Types.NUMERIC, Types.BOOLEAN, Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
