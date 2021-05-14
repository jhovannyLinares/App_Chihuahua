package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.RepresentantesAsignados;

public class RepresentanteAsignadoRowMapper implements RowMapper<RepresentantesAsignados> {

	@Override
	public RepresentantesAsignados mapRow(ResultSet rs, int rowNum) throws SQLException {

		RepresentantesAsignados rep = new RepresentantesAsignados();

		rep.setId(rs.getLong("id"));
		rep.setRepresentanteId(rs.getLong("representante_id"));
		rep.setUsuarioId(rs.getLong("usuario_id"));
		rep.setCargo(rs.getInt("cargo"));
		rep.setDistritoFederalId(rs.getLong("distrito_federal_id"));
		rep.setDistritoLocalId(rs.getLong("distrito_local_id"));
		rep.setMunicipioId(rs.getLong("municipio_id"));
		rep.setRutaId(rs.getLong("ruta_id"));
		rep.setSeccionElectoralId(rs.getLong("seccion_electoral_id"));
		rep.setCasillaId(rs.getLong("casilla_id"));
		rep.setEntidadId(rs.getLong("entidad_id"));
		rep.setZonaId(rs.getLong("zona_id"));
		
		return rep;
	}

}
