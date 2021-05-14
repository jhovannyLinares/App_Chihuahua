package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.ReporteAsignacionRg;
import mx.morena.persistencia.entidad.RepresentanteClaveElectoral;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.RepresentantesAsignados;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.LongRowMapper;
import mx.morena.persistencia.rowmapper.LongsRowMapper;
import mx.morena.persistencia.rowmapper.ReporteAsignacionCrgRowMapper;
import mx.morena.persistencia.rowmapper.ReporteAsignacionRgRowMapper;
import mx.morena.persistencia.rowmapper.RepresentanteClaveRowMapper;
import mx.morena.persistencia.rowmapper.RepresentanteRowMapper;
import mx.morena.persistencia.rowmapper.RepresentantesCrgRowMapper;
import mx.morena.persistencia.rowmapper.TipoRepresentanteRowMapper;

@Repository
public class RepresentantesRepository implements IRepresentanteRepository {
	
	Logger logger = LoggerFactory.getLogger(RepresentantesRepository.class);

	@Autowired
	private JdbcTemplate template;

	private String campos = "id, apellido_materno, apellido_paterno, calle, "
			+ "clave_elector, colonia, correo, codigo_postal, fecha_registro, "
			+ "ine_img_lado1, ine_img_lado2, ine_pdf, nombre, numero_exterior, "
			+ "numero_interior, propuesto, ruta, telefono_casa, telefono_celular, "
			+ "tipo_representante, distrito_federal_id, estado_id, municipio_id, seccion_electoral_id, "
			+ "usuario_id, is_asignado";

	@Override
	public Representantes findByClaveElector(String claveElector) {
		String sql = "SELECT " + campos + " FROM app_representantes where clave_elector = ? ";
		try {
			return template.queryForObject(sql, new Object[] { claveElector }, new int[] { Types.VARCHAR },
					new RepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void save(Representantes representantes) {
		String sql = " INSERT INTO app_representantes (id, " + "apellido_materno, " + "apellido_paterno, " + "calle, "
				+ "clave_elector, " + "colonia, " + "correo, " + "codigo_postal, " + "fecha_registro, "
				+ "ine_img_lado1, " + "ine_img_lado2, " + "ine_pdf, " + "nombre, " + "numero_exterior, "
				+ "numero_interior, " + "propuesto, " + "telefono_casa, " + "telefono_celular, "
				+ "tipo_representante, " + "distrito_federal_id, " + "estado_id, " + "municipio_id, "
				+ "seccion_electoral_id, " + "usuario_id, " + "fecha_sistema)"
				+ "VALUES ((SELECT MAX(id)+1 FROM app_representantes), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		template.update(sql,
				new Object[] { representantes.getApellidoMaterno(), representantes.getApellidoPaterno(),
						representantes.getCalle(), representantes.getClaveElector(), representantes.getColonia(),
						representantes.getCorreo(), representantes.getCp(), representantes.getFechaRegistro(),
						representantes.getRutaIneLado1(), representantes.getRutaIneLado2(), representantes.getRutaInePdf(),
						representantes.getNombre(), representantes.getNumExterior(), representantes.getNumInterior(),
						representantes.getPropuesto(),  representantes.getTelCasa(),
						representantes.getTelCelular(), representantes.getTipo(), representantes.getDistritoFederal(),
						representantes.getEstado(), representantes.getMunicipio(), representantes.getSeccionElectoral(),
						representantes.getUsuario(), representantes.getFechaSistema() });
	}

	@Override
	public Long getIdMax() {
		
		String sql = "SELECT MAX(id) FROM app_representantes";
		return template.queryForObject(sql, new IdMaxConvencidos());
	}

	@Override
	public Representantes getById(Long idCrg) {
		
		String sql = "select " + campos +" from app_representantes ar where id = ? and tipo_representante = 7";
		try {
			return template.queryForObject(sql, new Object[] { idCrg },
					new int[] { Types.NUMERIC }, new RepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Representantes> getAllCrg(Long tipoRepresentante) {
		System.out.print("repository tipo_rep" +tipoRepresentante);
		String sql = "SELECT ar.id, ar.nombre, ar.apellido_paterno, ar.apellido_materno, ar.tipo_representante from app_representantes ar where ar.tipo_representante = ?";
		try {
			return template.queryForObject(sql, new Object[] {tipoRepresentante }, new int[] { Types.NUMERIC },
					new RepresentantesCrgRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Perfil> getAllTipoRep() {
		String sql = "select id, tipo_representante from app_tipo_representantes";
		try {
			return template.queryForObject(sql,new Object[] {}, new int[] { }, new TipoRepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Long asignaRepresentante(RepresentantesAsignados representante) {
		String sql = "INSERT INTO app_representantes_asignados "  
				   + " (id, " + " representante_id," + " usuario_id," + " cargo)"  
				   + " values (COALESCE((SELECT MAX(id) FROM app_representantes_asignados), 0)+1,?, ?, ? )"; 
//				   + " values ((SELECT MAX(id)+1 FROM app_representantes_asignados),?, ?, ? )"; 
//		COALESCE((SELECT MAX(id) FROM app_reporte_casillas), 0)+1
		try {
		template.update(sql,
				new Object[] {representante.getRepresentanteId(), representante.getUsuarioId(), representante.getCargo()});
		return 1L;
		}catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}
	
	@Override
	public List<RepresentanteClaveElectoral> getAllRepresentantes(String claveElector) {
		
		String sql = "select ar.id, ar.nombre, ar.apellido_paterno, ar.apellido_materno, ar.tipo_representante as idTipoRep, atr.tipo_representante as nombreTipoRep, ar.distrito_federal_id as idDistrito, adf.cabecera as nombredistrito, ara.distrito_federal_id as iddistriroasignado, adf2.cabecera as nombredistritoasignado, ar.is_asignado "
				+ "from app_representantes ar "
				+ "left join app_tipo_representantes atr "
				+ "on ar.tipo_representante = atr.id "
				+ "left join app_distrito_federal adf "
				+ "on ar.distrito_federal_id = adf.id "
				+ "left join app_representantes_asignados ara "
				+ "on ar.id = ara.representante_id "
				+ "left join app_distrito_federal adf2 "
				+ "on ara.distrito_federal_id = adf2.id "
				+ "where ar.clave_elector = ?";
		
		try {
			return template.queryForObject(sql,new Object[] { claveElector }, new int[] { Types.VARCHAR }, new RepresentanteClaveRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	@Override
	public Long getIdMaxAsignados() {
		
		String sql = "SELECT MAX(id) FROM app_representantes_asignados";
		return template.queryForObject(sql, new IdMaxConvencidos());
	}

	@Override
	public Long updateRepresentante(long perfil, RepresentantesAsignados rep, long asignacion) {
		String update = "UPDATE app_representantes_asignados set ";
		String sql = null;
		String where = null;
		String values = null;
		try {

		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();

		if (asignacion == 1) {
			values = " distrito_federal_id = ? ";
			where = " where representante_id = ? ";
			sql = update.concat(values).concat(where);
			para.add(rep.getDistritoFederalId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		if (asignacion == 2) {
			values = " distrito_local_id = ? ";
			where = " where representante_id = ? ";
			para.add(rep.getDistritoLocalId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		if (asignacion == 3) {
			values = " municipio_id = ? ";
			where = " where representante_id = ? ";
			para.add(rep.getMunicipioId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		if (asignacion == 4) {
			values = " distrito_federal_id = ?, ";
			values = values.concat(" zona_id = ? ");
			where = " where representante_id = ? ";
			para.add(rep.getDistritoFederalId());
			para.add(rep.getZonaId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		if (asignacion == 5) {
			values = " distrito_federal_id = ?, ";
			values = values.concat(" zona_id = ?, ");
			values = values.concat(" ruta_id = ? ");
			where = " where representante_id = ? ";
			para.add(rep.getDistritoFederalId());
			para.add(rep.getZonaId());
			para.add(rep.getRutaId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		if (asignacion == 6) {
			values = " distrito_federal_id = ?, ";
			values = values.concat(" zona_id = ?, ");
			values = values.concat(" ruta_id = ?, ");
			values = values.concat(" seccion_electoral_id = ?, ");
			values = values.concat(" casilla_id = ? ");
			where = " where representante_id = ? ";
			para.add(rep.getDistritoFederalId());
			para.add(rep.getZonaId());
			para.add(rep.getRutaId());
			para.add(rep.getSeccionElectoralId());
			para.add(rep.getCasillaId());
			para.add(rep.getRepresentanteId());
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
			type.add(Types.NUMERIC);
		}

		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		sql = update.concat(values).concat(where);

		template.update(sql, parametros, types);
		
		return 1L;

		} catch (EmptyResultDataAccessException e) {
			return 0L;
		}
	}

	@Override
	public Representantes getRepresentante(Long representanteId) {
		String sql = " SELECT * FROM app_representantes where id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { representanteId }, new int[] { Types.NUMERIC },
					new RepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long updateStatusRepresentantes(Long id) {
		String sql = "update app_representantes set is_asignado = true where id = ?";
		
		try {
		template.update(sql, new Object[] {id},
				new int[] {Types.NUMERIC });
		return 1L;
		}catch (EmptyResultDataAccessException e) {
			return 0L;
		}
		
	}

	@Override
	public Long getByTipo(Long perfil, Long idDistrito, Long perfilUsuario) {
		String select = "select count(*) from app_representantes"; // where tipo_representante = ?";
		
		String sql = null;
		String where = " where tipo_representante = ?";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (perfilUsuario == 1) {
//			where = where;
			para.add(perfil);
			type.add(Types.NUMERIC);
		}
		else if ( perfilUsuario == 2) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		} else if (perfilUsuario == 7) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}else if (perfilUsuario == 8) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}else if (perfilUsuario == 9) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}
		
		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}
		
		try {			
			 sql = select.concat(where);
			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Long getRepAsignadoByTipo(Long perfil, Long idDistrito, Long perfilUsuario) {
		String select = "select count(*) from app_representantes"; // where tipo_representante = ? and is_asignado = true";
		
		
		String sql = null;
		String where = " where tipo_representante = ? and is_asignado = true ";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (perfilUsuario == 1) {
//			where = where;
			para.add(perfil);
			type.add(Types.NUMERIC);
		}
		else if ( perfilUsuario == 2) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		} else if (perfilUsuario == 7) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}else if (perfilUsuario == 8) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}else if (perfilUsuario == 9) {
			where = where.concat(" and distrito_federal_id = ? ");
			para.add(perfil);
			type.add(Types.NUMERIC);
			para.add(idDistrito);
			type.add(Types.NUMERIC);
		}
		
		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}
		
		try {			
			 sql = select.concat(where);
			return template.queryForObject(sql, parametros, types, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getRcCaptura(Long perfilRc) {
		String sql = "select COUNT(*) from app_representantes ar "
				+ "where ar.tipo_representante = ?";
		return template.queryForObject(sql, new Object[] { perfilRc }, new int[] { Types.NUMERIC }, new LongRowMapper());
	}

	@Override
	public Long getRcAsignados(Long perfilRc) {
		String sql = "select COUNT(*) from app_representantes ar "
				+ "where ar.tipo_representante = ? and ar.is_asignado = TRUE";
		return template.queryForObject(sql, new Object[] { perfilRc }, new int[] { Types.NUMERIC }, new LongRowMapper());
	}

	@Override
	public Long getByDistritoAndTipo(Long id, Long perfil) {
		String sql = "select count(*) from app_representantes where distrito_federal_id = ? and tipo_representante = ?";
		return template.queryForObject(sql, new Object[] { id, perfil }, new int[] { Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
	}

	@Override
	public Long getRepAsignadoByDistrito(Long id, Long perfil) {
		String sql = "select count(*) from app_representantes where distrito_federal_id = ? and tipo_representante = ? and is_asignado = true";
		return template.queryForObject(sql, new Object[] { id, perfil }, new int[] { Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
	}
	
	@Override
	public Representantes getRepresentanteByIdAndTipo(Long representanteId, Long tipoRepresentante) {
		String sql = " SELECT * FROM app_representantes where id = ? and tipo_representante = ? ";
		try {
			return template.queryForObject(sql, new Object[] { representanteId, tipoRepresentante }, new int[] { Types.NUMERIC, Types.NUMERIC },
					new RepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ReporteAsignacionRg> getReporteRg(Long idDistrito) {
		
		String sql = "select ac.federal_id as dFederal, ac.local_id as dLocal, am.nombre as municipio, aac.ruta as ruta, count(aac.id_casilla) as casillas from app_casilla ac "
				+ " inner join app_municipio am "
				+ " on ac.municpio_id = am.id "
				+ " inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla "
				+ " inner join app_representantes_asignados ara "
				+ " on ac.id = ara.casilla_id "
				+ " where ac.federal_id = ? group by ac.federal_id, ac.local_id, am.nombre, aac.ruta, aac.id_casilla ";
		
		try {
			return template.queryForObject(sql,new Object[] { idDistrito }, new int[] { Types.NUMERIC }, new ReporteAsignacionRgRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public Long getCargo(Long idDistrito, Long cargo) {
		
		String sql = "select count(ara.cargo) from app_casilla ac "
				+ " inner join app_municipio am "
				+ " on ac.municpio_id = am.id "
				+ " inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla "
				+ " inner join app_representantes_asignados ara "
				+ " on ac.id = ara.casilla_id "
				+ " where ac.federal_id = ? and ara.cargo = ? ";
		return template.queryForObject(sql, new Object[] { idDistrito, cargo }, new int[] { Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
	}

	@Override
	public List<ReporteAsignacionRg> getReporteRgEstatal() {
		
		String sql = "select ac.federal_id as dFederal, ac.local_id as dLocal, am.nombre as municipio, aac.ruta as ruta, count(aac.id_casilla) as casillas from app_casilla ac "
				+ " inner join app_municipio am "
				+ " on ac.municpio_id = am.id "
				+ " inner join app_asignacion_casillas aac "
				+ " on ac.id = aac.id_casilla "
				+ " inner join app_representantes_asignados ara "
				+ " on ac.id = ara.casilla_id "
				+ " group by ac.federal_id, ac.local_id, am.nombre, aac.ruta, aac.id_casilla order by ac.federal_id";
		
		try {
			return template.queryForObject(sql,new Object[] { }, new int[] { }, new ReporteAsignacionRgRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Long getRepCrgAsignadoByDistrito(Long idEntidad, Long idDistrito, Long idMunicipio, Long perfil) {
		String sql = " select count(*) from app_representantes ar where ar.tipo_representante = ? and is_asignado = true and ar.estado_id = ? "
				+ "and ar.distrito_federal_id = ? and ar.municipio_id = ? ";
		try {
			return template.queryForObject(sql, new Object[] { perfil, idEntidad, idDistrito, idMunicipio }, new int[]
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Long> getRutasByCrgAsignadoAndDistrito(Long idEntidad, Long idDistritoF, Long idDistritoL, Long idMunicipio, Long perfil) {
		String sql = " SELECT aac.ruta FROM app_asignacion_casillas aac inner join app_representantes ar on aac.id_crg = ar.id "
				+ "inner join app_casilla ac on aac.id_casilla = ac.id where (aac.id_crg is not null and aac.id_crg != 0) "
				+ "and ar.tipo_representante = ? and aac.distrito_federal_id = ? and ac.entidad_id = ? "
				+ "and ac.local_id = ? and ac.municpio_id = ? and aac.ruta != 0 group by aac.ruta ";
		try {
			return template.queryForObject(sql, new Object[] { perfil, idDistritoF, idEntidad, idDistritoL, idMunicipio }, new int[]
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC },
					new LongsRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long getCargoByDistritoAndMunicipio(Long idDistritoF, Long idDistritoL, Long idMunicipio, Long cargo) {
		String sql = " select count(*) from app_casilla ac inner join app_municipio am on ac.municpio_id = am.id "
				+ "inner join app_representantes_asignados ara on ac.id = ara.casilla_id "
				+ "where ac.federal_id = ? and ac.local_id = ? and ac.municpio_id = ? and ara.cargo = ? ";
		try {
			return template.queryForObject(sql, new Object[] { idDistritoF, idDistritoL, idMunicipio, cargo }, new int[]
					{ Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC }, new LongRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<ReporteAsignacionRg> getDistritosAndMunicipios(Long idEntidad, Long idDistritoF, Long idDistritoL, Long idMunicipio) {
		String select = "select ac.federal_id, ac.local_id, ac.municpio_id, am.nombre, count(ac.id) as casilla "
				+ "from app_casilla ac inner join app_municipio am on am.id = ac.municpio_id ";
		
		String sql = null;
		String where = "";
		String group = "";
		List<Object> para = new ArrayList<Object>();
		List<Integer> type = new ArrayList<Integer>();
		
		if (idEntidad != null) {
			where = " where am.entidad_id = ? ";
			para.add(idEntidad);
			type.add(Types.NUMERIC);
		}

		if (idDistritoF != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.federal_id = ? ");
			} else {
				where = " where ac.federal_id = ? ";
			}
			para.add(idDistritoF);
			type.add(Types.NUMERIC);
		}

		if (idDistritoL != null) {
			if (para.size() > 0) {
				where = where.concat(" and ac.local_id = ? ");
			} else {
				where = " where ac.local_id = ? ";
			}
			para.add(idDistritoL);
			type.add(Types.NUMERIC);
		}

		if (idMunicipio != null) {

			if (para.size() > 0) {
				where = where.concat(" and ac.municpio_id = ? ");
			} else {
				where = " where ac.municpio_id = ? ";
			}
			para.add(idMunicipio);
			type.add(Types.NUMERIC);
		}

		Object[] parametros = new Object[para.size()];
		int[] types = new int[para.size()];

		for (int i = 0; i < para.size(); i++) {
			parametros[i] = para.get(i);
			types[i] = type.get(i);
		}

		try {
			group = " group by ac.federal_id, ac.local_id, ac.municpio_id, am.nombre, casilla order by ac.federal_id ";
			sql = select.concat(where).concat(group);
			logger.debug(sql);

			return template.queryForObject(sql, parametros, types, new ReporteAsignacionCrgRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
