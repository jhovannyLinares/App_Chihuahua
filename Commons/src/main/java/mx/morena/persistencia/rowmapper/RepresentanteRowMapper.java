package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Representantes;

public class RepresentanteRowMapper implements RowMapper<Representantes> {

	@Override
	public Representantes mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Representantes representantes = new Representantes();
		
		representantes.setId(rs.getLong("id"));
		representantes.setNombre(rs.getString("nombre"));
		representantes.setApellidoPaterno(rs.getString("apellido_paterno"));
		representantes.setApellidoMaterno(rs.getString("apellido_materno"));
		representantes.setEstado(rs.getLong("estado_id"));
		representantes.setDistritoFederal(rs.getLong("distrito_federal_id"));
		representantes.setMunicipio(rs.getLong("municipio_id"));
		representantes.setSeccionElectoral(rs.getLong("seccion_electoral_id"));
		representantes.setUsuario(rs.getLong("usuario_id"));
		representantes.setFechaRegistro(rs.getDate("fecha_registro"));
		representantes.setClaveElector(rs.getString("clave_elector"));
		representantes.setCalle(rs.getString("calle"));
		representantes.setNumExterior(rs.getString("numero_exterior"));
		representantes.setNumInterior(rs.getString("numero_interior"));
		representantes.setColonia(rs.getString("colonia"));
		representantes.setCp(rs.getString("codigo_postal"));
		representantes.setTelCasa(rs.getString("telefono_casa"));
		representantes.setTelCelular(rs.getString("telefono_celular"));
		representantes.setCorreo(rs.getString("correo"));
		representantes.setPropuesto(rs.getString("propuesto"));
		representantes.setRutaIneLado1(rs.getString("ine_img_lado1"));
		representantes.setRutaIneLado2(rs.getString("ine_img_lado2"));
		representantes.setRutaInePdf(rs.getString("ine_pdf"));
		representantes.setTipo(rs.getInt("tipo_representante"));
		representantes.setIsAsignado(rs.getBoolean("is_asignado"));
		
		
		return representantes;
	}

}
