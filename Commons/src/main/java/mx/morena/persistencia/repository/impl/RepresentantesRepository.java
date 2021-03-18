package mx.morena.persistencia.repository.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.RepresentanteRowMapper;

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
				+ "numero_interior, " + "propuesto, " + "ruta, " + "telefono_casa, " + "telefono_celular, "
				+ "tipo_representante, " + "distrito_federal_id, " + "estado_id, " + "municipio_id, "
				+ "seccion_electoral_id, " + "usuario_id, " + "fecha_sistema)"
				+ "VALUES ((SELECT MAX(id)+1 FROM app_representantes), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		template.update(sql,
				new Object[] { representantes.getApellidoMaterno(), representantes.getApellidoPaterno(),
						representantes.getCalle(), representantes.getClaveElector(), representantes.getColonia(),
						representantes.getCorreo(), representantes.getCp(), representantes.getFechaRegistro(),
						representantes.getIneLado1(), representantes.getIneLado2(), representantes.getInePdf(),
						representantes.getNombre(), representantes.getNumExterior(), representantes.getNumInterior(),
						representantes.getPropuesto(), representantes.getRuta(), representantes.getTelCasa(),
						representantes.getTelCelular(), representantes.getTipo(), representantes.getDistritoFederal(),
						representantes.getEstado(), representantes.getMunicipio(), representantes.getSeccionElectoral(),
						representantes.getUsuario(), representantes.getFechaSistema() });
	}

	@Override
	public Long getIdMax() {
		
		String sql = "SELECT MAX(id) FROM app_representantes";
		return template.queryForObject(sql, new IdMaxConvencidos());
	}

}
