package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.rowmapper.ConvencidoRowMapper;
import mx.morena.persistencia.rowmapper.ConvencidosRowMapper;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;

@Repository
public class ConvencidosRepository implements IConvencidosRepository {

	@Autowired
	private JdbcTemplate template;
	
	
	private String campos = " ac.id, ac.apellido_materno, ac.apellido_paterno, ac.banco, ac.calle, ac.clabe_interbancaria, ac.clave_elector, "
			+ "	ac.colonia, ac.correo, ac.codigo_postal, ac.curp, ac.dv, ac.estatus, ac.fecha_baja, ac.fecha_reactivacion, ac.fecha_registro, "
			+ "	ac.fecha_sistema, ac.mov, ac.nombre, ac.numero_exterior, ac.numero_interior, ac.telefono_casa, ac.telefono_celular, "
			+ "	ac.distrito_federal_id, ac.estado_id, ac.municipio_id, ac.usuario_id_usuario, ac.seccion_id";

	@Override
	public List<Convencidos> getByDistritoFederal(Long idFederal, Long tipo) {

		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, ae.nombre as nombre_estado, am.nombre as nombre_municipio, al.nombre as nombre_seccion"
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_entidad ae on ac.estado_id = ae.id "
				+ " inner join app_municipio am on ac.municipio_id = am.id "
				+ " inner join app_localidad al on ac .seccion_id = al.id "
				+ " where ac.distrito_federal_id = ? and ac.tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getByMunicipio(Long municipio_id, Long tipo) {
		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, ae.nombre as nombre_estado, am.nombre as nombre_municipio, al.nombre as nombre_seccion"
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_entidad ae on ac.estado_id = ae.id "
				+ " inner join app_municipio am on ac.municipio_id = am.id "
				+ " inner join app_localidad al on ac.seccion_id = al.id "
				+ " where ac.municipio_id = ? and ac.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { municipio_id, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getBySeccionesElectoralesIn(Long idSeccion, Long tipo) {
		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, ae.nombre as nombre_estado, am.nombre as nombre_municipio, al.nombre as nombre_seccion"
		+ " FROM app_convencidos ac "
		+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
		+ " inner join app_entidad ae on ac.estado_id = ae.id "
		+ " inner join app_municipio am on ac.municipio_id = am.id "
		+ " inner join app_localidad al on ac.seccion_id = al.id "
		+ " where ac.seccion_id = ? and ac.tipo = ?";
		
		try {
			return template.queryForObject(sql, new Object[] { idSeccion, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Convencidos> findByClaveElector(String claveElector) {
		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, ae.nombre as nombre_estado, am.nombre as nombre_municipio, al.nombre as nombre_seccion"
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_entidad ae on ac.estado_id = ae.id "
				+ " inner join app_municipio am on ac.municipio_id = am.id "
				+ " inner join app_localidad al on ac.id = al.id "
				+ " where ac.clave_elector = ? ";
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByCurp(String curp, Long tipo) {
		String sql = "SELECT " + campos + " FROM app_convencidos ac WHERE ac.curp = ? AND ac.tipo = ? LIMIT 1 ";
		try {
			return template.queryForObject(sql, new Object[] { curp, tipo }, new int[] { Types.VARCHAR, Types.NUMERIC },
					new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByIdAndEstatus(Long idCot, char estatus, Long tipo) {
		String sql = "SELECT " + campos
				+ " FROM app_convencidos ac where ac.id = ? and ac.estatus = ? and ac.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { idCot, estatus, tipo },
					new int[] { Types.NUMERIC, Types.CHAR, Types.NUMERIC }, new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void save(Convencidos convencidos) {
		String sql = "INSERT INTO app_convencidos (id," + " apellido_materno, " + " apellido_paterno, " + " banco, "
				+ " calle, " + " clabe_interbancaria, " + " clave_elector, " + " colonia, " + " correo, "
				+ " codigo_postal, " + " curp, " + " dv, " + " estatus, " + " fecha_baja, " + " fecha_reactivacion, "
				+ " fecha_registro, " + " fecha_sistema, " + " mov, " + " nombre, " + " numero_exterior, "
				+ " numero_interior, " + " telefono_casa, " + " telefono_celular, " + " distrito_federal_id, "
				+ " estado_id, " + " municipio_id, " + " usuario_id_usuario, " + "tipo, seccion_id) VALUES( "
				+ " (SELECT MAX(id)+1 FROM app_convencidos), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

		template.update(sql,
				new Object[] { convencidos.getApellidoMaterno(), convencidos.getApellidoPaterno(),
						convencidos.getBanco(), convencidos.getCalle(), convencidos.getClabeInterbancaria(),
						convencidos.getClaveElector(), convencidos.getColonia(), convencidos.getCorreo(),
						convencidos.getCp(), convencidos.getCurp(), convencidos.isDv(), convencidos.getEstatus(),
						convencidos.getFechaBaja(), convencidos.getFechaReactivacion(), convencidos.getFechaRegistro(),
						convencidos.getFechaSistema(), convencidos.isMov(), convencidos.getNombre(),
						convencidos.getNumExterior(), convencidos.getNumInterior(), convencidos.getTelCasa(),
						convencidos.getTelCelular(), convencidos.getIdFederal(), convencidos.getIdEstado(),
						convencidos.getIdMunicipio(), convencidos.getUsuario(), convencidos.getTipo(),convencidos.getIdSeccion() });

	}

	@Override
	public void updateStatusCot(Long id, char estatus, Date fechaBaja, Long tipo, String tipoFecha) {
		String sql = "UPDATE app_convencidos SET estatus = ?, " + tipoFecha + " = ? WHERE id = ? and tipo = ?";

		template.update(sql, new Object[] { estatus, fechaBaja, id, tipo },
				new int[] { Types.CHAR, Types.DATE, Types.NUMERIC, Types.NUMERIC });
	}

	@Override
	public Long getIdMax() {
		String sql = "SELECT MAX(id) FROM app_convencidos";
		return template.queryForObject(sql, new IdMaxConvencidos());

	}

	


}
