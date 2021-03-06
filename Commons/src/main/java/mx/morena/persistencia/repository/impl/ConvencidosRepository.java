package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.rowmapper.ConvencidoNotificadoRowMapper;
import mx.morena.persistencia.rowmapper.ConvencidoRowMapper;
import mx.morena.persistencia.rowmapper.ConvencidosRowMapper;
import mx.morena.persistencia.rowmapper.ConvencidosValRowMapper;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;

@Repository
public class ConvencidosRepository implements IConvencidosRepository {
	
	Logger logger = LoggerFactory.getLogger(ConvencidosRepository.class);

	@Autowired
	private JdbcTemplate template;
	
	
	private String campos = " ac.id, ac.apellido_materno, ac.apellido_paterno, ac.banco, ac.calle, ac.clabe_interbancaria, ac.clave_elector, "
			+ "	ac.colonia, ac.correo, ac.codigo_postal, ac.curp, ac.dv, ac.estatus, ac.fecha_baja, ac.fecha_reactivacion, ac.fecha_registro, "
			+ "	ac.fecha_sistema, ac.mov, ac.nombre, ac.numero_exterior, ac.numero_interior, ac.telefono_casa, ac.telefono_celular, "
			+ "	ac.distrito_federal_id, ac.estado_id, ac.municipio_id, ac.usuario_id_usuario, ac.seccion_id";

	@Override
	public List<Convencidos> getByDistritoFederal(Long idFederal, Long tipo) {

		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, al.entidad as nombre_estado , al.municipio as nombre_municipio" 
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_secciones al on ac.seccion_id = al.id "
				+ " where ac.distrito_federal_id = ? and ac.tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idFederal, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}


	@Override
	public List<Convencidos> findByClaveElector(String claveElector) {
		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, al.entidad as nombre_estado , al.municipio as nombre_municipio"
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_localidad al on ac.seccion_id = al.id "
				+ " where ac.clave_elector = ? ";
		
		logger.debug(sql);
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	public List<Convencidos> findByClaveElectorVal(String claveElector) {
		String sql = "SELECT " + campos
				+ " FROM app_convencidos ac "
				+ " where ac.clave_elector = ? ";
		
		logger.debug(sql);
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new ConvencidosValRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Convencidos getByCurp(String curp) {
		String sql = "SELECT " + campos + " FROM app_convencidos ac WHERE ac.curp = ? LIMIT 1 ";
		try {
			return template.queryForObject(sql, new Object[] { curp }, new int[] { Types.VARCHAR },
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
				+ " COALESCE((SELECT MAX(id) FROM app_convencidos), 0)+1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
//				+ " (SELECT MAX(id)+1 FROM app_convencidos), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
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

	@Override
	public List<Convencidos> getAllCots(Long tipo) {
		String sql = "SELECT " + campos + ", am.nombre as nombre_municipio, adf.nombre as nombre_distrito, adf.nombre as nombre_estado, al.tipo as nombre_seccion "
				+ "FROM app_convencidos ac inner join app_municipio am on am.id = ac.municipio_id "
				+ "inner join app_distrito_federal adf on adf.id = ac.distrito_federal_id "
				+ "inner join app_secciones al on al.id = ac.seccion_id WHERE ac.tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { tipo }, new int[] { Types.NUMERIC },
					new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Convencidos> getByDfAndMpio(Long idDistrito, Long idMunicipio, Long tipo) {
		String sql = "SELECT " + campos + ", adf.nombre as nombre_distrito, al.entidad as nombre_estado , al.municipio as nombre_municipio" 
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_localidad al on ac.seccion_id = al.id "
				+ " where ac.distrito_federal_id = ? and ac.municipio_id = ? and ac.tipo = ?";
		try {
			return template.queryForObject(sql, new Object[] { idDistrito, idMunicipio, tipo },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}


	@Override
	public void update(Convencidos convencidos) {
		String sql = "UPDATE app_convencidos "
				+ "SET apellido_materno = ?, apellido_paterno = ?, banco = ?, calle = ?, clabe_interbancaria = ?, "
				+ "clave_elector = ?, colonia = ?, correo = ?, codigo_postal = ?, curp = ?, "
				+ "nombre = ?, numero_exterior = ?, numero_interior = ?, telefono_casa = ?, telefono_celular = ?, "
				+ "distrito_federal_id = ?, estado_id = ?, municipio_id = ?, seccion_id = ? "
				+ "WHERE id = ? and tipo = ?";

		template.update(sql,
				new Object[] { convencidos.getApellidoMaterno(), convencidos.getApellidoPaterno(),
						convencidos.getBanco(), convencidos.getCalle(), convencidos.getClabeInterbancaria(),
						convencidos.getClaveElector(), convencidos.getColonia(), convencidos.getCorreo(),
						convencidos.getCp(), convencidos.getCurp(), convencidos.getNombre(),
						convencidos.getNumExterior(), convencidos.getNumInterior(), convencidos.getTelCasa(),
						convencidos.getTelCelular(), convencidos.getIdFederal(), convencidos.getIdEstado(),
						convencidos.getIdMunicipio(), convencidos.getIdSeccion(), convencidos.getId(), convencidos.getTipo() });

	}



	@Override
	public List<Convencidos> getConvencidos(Long distritoFederalId, Long idMunicipio, Long idSeccion,
			String claveElector, Long convencido) {

		String select = "SELECT" + campos
				+ ", adf.nombre as nombre_distrito, al.entidad as nombre_estado , al.municipio as nombre_municipio"
				+ " FROM app_convencidos ac "
				+ " inner join app_distrito_federal adf on ac.distrito_federal_id = adf.id "
				+ " inner join app_localidad al on ac.seccion_id = al.id ";

		String sql = null;
		String where = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (distritoFederalId != null) {
			where = " where ac.distrito_federal_id = ? ";
			para.add(distritoFederalId);
			type.add(Types.NUMERIC);
		}

		if (idMunicipio != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.municipio_id = ? ");
			} else {
				where = " where ac.municipio_id = ? ";
			}
			para.add(idMunicipio);
			type.add(Types.NUMERIC);
		}

		if (idSeccion != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.seccion_id = ? ");
			} else {
				where = " where ac.seccion_id = ? ";
			}
			para.add(idSeccion);
			type.add(Types.NUMERIC);
		}

		if (claveElector != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.clave_elector = ? ");
			} else {
				where = " where ac.clave_elector = ? ";
			}
			para.add(claveElector);
			type.add(Types.VARCHAR);
		}

		if (convencido != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.tipo = ? ");
			} else {
				where = " where ac.tipo = ? ";
			}
			para.add(convencido);
			type.add(Types.NUMERIC);
		}

		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		try {

			logger.debug("\n");

			sql = select.concat(where);
			logger.debug(sql);

			logger.debug("*************    convencidos ");
			logger.debug("\n");

			return template.queryForObject(sql, parametros, types, new ConvencidosRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Convencidos findByClaveOCurp(String nombreCampo, String valorCampo, Long id) {
		String sql = "SELECT " + campos + " FROM app_convencidos ac WHERE ac." + nombreCampo + " = ? AND NOT ac.id = ? LIMIT 1 ";
		try {
			return template.queryForObject(sql, new Object[] { valorCampo, id }, new int[] { Types.VARCHAR, Types.NUMERIC },
					new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
	}
	
	@Override
	public Convencidos getByIdAndTipoA(Long id, Long tipo) {
		String sql = "SELECT " + campos + " FROM app_convencidos ac WHERE ac.id = ? AND ac.tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { id, tipo }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new ConvencidoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public Long countByDistritoAndTipo(Long distritoId, Long tipo, char estatus) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ? and estatus = ? ";
		try {
			return template.queryForObject(sql, new Object[] { distritoId, tipo, estatus }, new int[] { Types.NUMERIC, Types.NUMERIC, Types.VARCHAR },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public Long countByDistAndTipo(Long distritoId, Long tipo) {
		String sql = "select count(*) from app_convencidos ac where distrito_federal_id = ? and tipo = ?  ";
		try {
			return template.queryForObject(sql, new Object[] { distritoId, tipo}, new int[] { Types.NUMERIC, Types.NUMERIC },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public Long countByLocalAndTipo(Long localId, Long tipo) {
		String sql = "select count(*) from app_convencidos ac "
				+ "inner join app_secciones as2 "
				+ "on ac.seccion_id = as2.id "
				+ "where as2.local_id = ? and tipo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { localId, tipo}, new int[] { Types.NUMERIC, Types.NUMERIC },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Long countByMunicipioAndTipo(Long idMunicipio, Long tipo, char estatus) {
		String sql = "select count(*) from app_convencidos ac where ac.municipio_id = ? and ac.tipo = ? and ac.estatus = ?";
		try {
			return template.queryForObject(sql, new Object[] { idMunicipio, tipo, estatus}, new int[] { Types.NUMERIC, Types.NUMERIC, Types.CHAR },
					new IdMaxConvencidos());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public Convencidos getByIdAndTipoAndIsNotificado(Long idEntidad, Long id, Long tipo, boolean isNotificado) {
		String sql = "SELECT ac.id, ac.nombre, ac.apellido_materno, ac.apellido_paterno, ac.is_notificado FROM app_convencidos ac "
				+ "WHERE ac.id = ? AND ac.tipo = ? AND ac.is_notificado = ? AND ac.estado_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { id, tipo, isNotificado, idEntidad }, new int[]
					{ Types.NUMERIC, Types.NUMERIC, Types.BOOLEAN, Types.NUMERIC }, new ConvencidoNotificadoRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public Long updateMarcarOrDesmarcar(Long id, Long tipo, boolean isNotificado) {
		String sql = " UPDATE app_convencidos SET is_notificado = ? WHERE id = ? and tipo = ? ";

		try {
			template.update(sql, new Object[] { isNotificado, id, tipo },
					new int[] { Types.BOOLEAN, Types.NUMERIC, Types.NUMERIC });
			return 1L;
		} catch (Exception e) {
			return 0L;
		}
	}

}
