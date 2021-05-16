package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.DatosRc;

public class DatosRcRowMapper implements RowMapper<DatosRc> {

	@Override
	public DatosRc mapRow(ResultSet rs, int rowNum) throws SQLException {

		DatosRc rc = new DatosRc();
		
//		rc.setNombreRepresentante1(rs.getString(""));
//		rc.setNombreRepresentante2(rs.getString(""));
//		rc.setNombreRepresentante3(rs.getString(""));
//		rc.setNombreRepresentante4(rs.getString(""));
		
		rc.setEntidad(rs.getString("entidad"));
		rc.setDistritoFederal(rs.getString("federal"));
		rc.setDistritoLocal(rs.getString("local"));
		rc.setSeccion(rs.getLong("seccion_electoral_id"));
		rc.setIdCasilla(rs.getLong("casilla_id"));
		rc.setCasilla(rs.getString("casilla"));
		rc.setTipoCasilla(rs.getString("tipo_casilla"));
		
		rc.setCalle(rs.getString("calle"));
		rc.setNumero(rs.getString("numero"));
		rc.setColonia(rs.getString("colonia"));
//		rc.setLocalidad(rs.getString(""));
		rc.setUbicacion(rs.getString("ubicacion"));
		
		return rc;
	}

}
