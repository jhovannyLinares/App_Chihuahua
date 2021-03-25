package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.RepresentanteRowMapper;
import mx.morena.persistencia.rowmapper.RepresentantesCrgRowMapper;
import mx.morena.persistencia.rowmapper.TipoRepresentanteRowMapper;

@Repository
public class RepresentantesRepository implements IRepresentanteRepository {

	@Autowired
	private JdbcTemplate template;

	private String campos = "id, apellido_materno, apellido_paterno, calle, "
			+ "clave_elector, colonia, correo, codigo_postal, fecha_registro, "
			+ "ine_img_lado1, ine_img_lado2, ine_pdf, nombre, numero_exterior, "
			+ "numero_interior, propuesto, ruta, telefono_casa, telefono_celular, "
			+ "tipo_representante, distrito_federal_id, estado_id, municipio_id, seccion_electoral_id, "
			+ "usuario_id";

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
		String sql = "select id, nombre from app_perfil";
		try {
			return template.queryForObject(sql,new Object[] {}, new int[] { }, new TipoRepresentanteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
