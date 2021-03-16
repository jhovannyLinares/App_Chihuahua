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
	public List<Convencidos> getByDistritoFederal(Long idFederal,Long tipo) {

		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where distrito_federal_id = ? and tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal,tipo }, new int[] { Types.NUMERIC,Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getByMunicipio(Long municipio_id,Long tipo) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where municipio_id = ? and tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { municipio_id,tipo }, new int[] { Types.NUMERIC,Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion,Long tipo) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where municipio_id = ? and tipo = ?";

		Object[] objects = new Object[seccion.size()+1];
		
		int[] intr = new int[seccion.size()+1];
		
		for (int i = 0; i < seccion.size(); i++) {
			objects[i] = seccion.get(i).getId();
			intr[i] = Types.NUMERIC;
		}
		
		objects[seccion.size()] = tipo;
		intr[seccion.size()] = Types.NUMERIC;

		return template.queryForObject(sql, objects, intr, new ConvencidosRowMapper());
	}
	
	@Override
	public List<Convencidos> findByClaveElector(String claveElector,Long tipo) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where clave_elector = ? and tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { claveElector,tipo }, new int[] { Types.VARCHAR,Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByCurp(String curp,Long tipo) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where curp = ? and tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { curp,tipo }, new int[] { Types.VARCHAR,Types.NUMERIC },
				new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByIdAndEstatus(Long idCot, char estatus,Long tipo) {
		String sql = "SELECT id, apellido_materno, apellido_paterno, banco, calle, clabe_interbancaria, clave_elector, colonia, correo, codigo_postal, curp, dv, estatus, fecha_baja, fecha_reactivacion, fecha_registro, fecha_sistema, mov, nombre, numero_exterior, numero_interior, telefono_casa, telefono_celular, distrito_federal_id, estado_id, municipio_id, usuario_id_usuario "
				+ "FROM app_convencidos where id = ? and estatus = ? and tipo = ? ";

		return template.queryForObject(sql, new Object[] { idCot, estatus,tipo },
				new int[] { Types.NUMERIC, Types.NVARCHAR,Types.NUMERIC }, new ConvencidoRowMapper());
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
				new Object[] {  convencidos.getaMaterno(), convencidos.getaPaterno(),
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
	public void updateStatusCot(Long id, char estatus, Date fechaBaja,Long tipo) {
		String sql = "UPDATE app_convencidos SET estatus = ?, fecha_baja = ? WHERE id = ? and tipo = ?";
		
		template.update(sql,
				new Object[] { estatus, fechaBaja, id,tipo });
	}

}
