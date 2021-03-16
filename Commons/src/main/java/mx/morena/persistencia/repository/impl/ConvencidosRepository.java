package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.rowmapper.ConvencidoRowMapper;
import mx.morena.persistencia.rowmapper.ConvencidosRowMapper;

@Repository
public class ConvencidosRepository implements IConvencidosRepository {

	@Autowired
	private JdbcTemplate template;

	

	@Override
	public List<Convencidos> getByDistritoFederal(Long idFederal) {

		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where distrito_federal_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal }, new int[] { Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getByMunicipio(Long municipio_id) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where municipio_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { municipio_id }, new int[] { Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where municipio_id = ? ";

		Object[] objects = new Object[seccion.size()];
		int[] intr = new int[seccion.size()];
		for (int i = 0; i < seccion.size(); i++) {
			objects[i] = seccion.get(i).getId();
			intr[i] = Types.NUMERIC;
		}

		return template.queryForObject(sql, objects, intr, new ConvencidosRowMapper());
	}
	
	@Override
	public List<Convencidos> findByClaveElector(String claveElector) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where clave_elector = ? ";
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByCurp(String curp) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where curp = ? ";
		try {
			return template.queryForObject(sql, new Object[] { curp }, new int[] { Types.VARCHAR },
				new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByIdAndEstatus(Long idCot, char estatus) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where id = ? and estatus = ? ";

		return template.queryForObject(sql, new Object[] { idCot, estatus },
				new int[] { Types.NUMERIC, Types.NVARCHAR }, new ConvencidoRowMapper());
	}

	@Override
	public void save(Convencidos convencidos) {
		String sql = "INSERT INTO app_convencidos (id," + " apellido_materno, " + " apellido_paterno, " + " banco, "
				+ " calle, " + " clabe_interbancaria, " + " clave_elector, " + " colonia, " + " correo, "
				+ " codigo_postal, " + " curp, " + " dv, " + " estatus, " + " fecha_baja, " + " fecha_reactivacion, "
				+ " fecha_registro, " + " fecha_sistema, " + " mov, " + " nombre, " + " numero_exterior, "
				+ " numero_interior, " + " telefono_casa, " + " telefono_celular, " + " distrito_federal_id, "
				+ " estado_id, " + " municipio_id, " + " usuario_id_usuario) VALUES( "
				+ " (SELECT MAX(id)+1 FROM app_convencidos), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		template.update(sql,
				new Object[] {  convencidos.getaMaterno(), convencidos.getaPaterno(),
						convencidos.getBanco(), convencidos.getCalle(), convencidos.getClabeInterbancaria(),
						convencidos.getClaveElector(), convencidos.getColonia(), convencidos.getCorreo(),
						convencidos.getCp(), convencidos.getCurp(), convencidos.isDv(), convencidos.getEstatus(),
						convencidos.getFechaBaja(), convencidos.getFechaReactivacion(), convencidos.getFechaRegistro(),
						convencidos.getFechaSistema(), convencidos.isMov(), convencidos.getNombre(),
						convencidos.getNumExterior(), convencidos.getNumInterior(), convencidos.getTelCasa(),
						convencidos.getTelCelular(), convencidos.getDistritoFederal(), convencidos.getEstado(),
						convencidos.getMunicipio(), convencidos.getUsuario() });

	}

	@Override
	public void updateStatusCot(Long id, char estatus, Date fechaBaja) {
		String sql = "UPDATE app_convencidos SET estatus = ?, fecha_baja = ? WHERE id = ?";
		
		template.update(sql,
				new Object[] { estatus, fechaBaja, id });
	}

}
