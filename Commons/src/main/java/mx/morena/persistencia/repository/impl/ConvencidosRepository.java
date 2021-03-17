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
	
	
	private String campos=" id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, "
			+ "colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, "
			+ "mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, "
			+ "municipio_id, usuario_id_usuario, seccion_id +";

	@Override
	public List<Convencidos> getByDistritoFederal(Long idFederal, Long tipo) {

		String sql = "SELECT " + campos
				+ "FROM app_convencidos where distrito_federal_id = ? and tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getByMunicipio(Long municipio_id, Long tipo) {
		String sql = "SELECT " + campos
				+ "FROM app_convencidos where municipio_id = ? and tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { municipio_id, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}


	@Override
	public List<Convencidos> findByClaveElector(String claveElector) {
		String sql = "SELECT " + campos
				+ "FROM app_convencidos where clave_elector = ? ";
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByCurp(String curp, Long tipo) {
		String sql = "SELECT " + campos
				+ "FROM app_convencidos where curp = ? and tipo = ?";
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
				+ "FROM app_convencidos where id = ? and estatus = ? and tipo = ?";
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
				+ " estado_id, " + " municipio_id, " + " usuario_id_usuario, " + "tipo) VALUES( "
				+ " (SELECT MAX(id)+1 FROM app_convencidos), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		template.update(sql,
				new Object[] { convencidos.getApellidoMaterno(), convencidos.getApellidoPaterno(),
						convencidos.getBanco(), convencidos.getCalle(), convencidos.getClabeInterbancaria(),
						convencidos.getClaveElector(), convencidos.getColonia(), convencidos.getCorreo(),
						convencidos.getCp(), convencidos.getCurp(), convencidos.isDv(), convencidos.getEstatus(),
						convencidos.getFechaBaja(), convencidos.getFechaReactivacion(), convencidos.getFechaRegistro(),
						convencidos.getFechaSistema(), convencidos.isMov(), convencidos.getNombre(),
						convencidos.getNumExterior(), convencidos.getNumInterior(), convencidos.getTelCasa(),
						convencidos.getTelCelular(), convencidos.getDistritoFederal(), convencidos.getEstado(),
						convencidos.getMunicipio(), convencidos.getUsuario(), convencidos.getTipo() });

	}

	@Override
	public void updateStatusCot(Long id, char estatus, Date fechaBaja, Long tipo) {
		String sql = "UPDATE app_convencidos SET estatus = ?, fecha_baja = ? WHERE id = ? and tipo = ?";

		template.update(sql, new Object[] { estatus, fechaBaja, id, tipo },
				new int[] { Types.CHAR, Types.DATE, Types.NUMERIC, Types.NUMERIC });
	}

	@Override
	public Long getIdMax() {
		String sql = "SELECT MAX(id) FROM app_convencidos";
		return template.queryForObject(sql, new IdMaxConvencidos());

	}

	@Override
	public List<Convencidos> getBySeccionesElectoralesIn(Long idSeccion, Long tipo) {
		String sql = "SELECT " + campos
		+ "FROM app_convencidos where seccion_id = ? and tipo = ?";
		
		try {
			return template.queryForObject(sql, new Object[] { idSeccion, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
