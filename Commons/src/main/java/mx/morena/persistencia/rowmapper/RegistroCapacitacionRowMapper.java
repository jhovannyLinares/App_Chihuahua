package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.RegistroCapacitacion;

public class RegistroCapacitacionRowMapper implements RowMapper<List<RegistroCapacitacion>>{

	@Override
	public List<RegistroCapacitacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<RegistroCapacitacion> lstRegistro = new ArrayList<RegistroCapacitacion>();
		RegistroCapacitacion registro = null;
		
		do {
			
			registro = new RegistroCapacitacion();
			
			registro.setId(rs.getLong("id"));
			registro.setIdRepresentante(rs.getLong("id_representante"));
			registro.setTomoCapacitacion(rs.getString("tomo_capacitacion"));
			registro.setFechaCapacitaion(rs.getTimestamp("fecha_capacitacion"));
			registro.setHoraCapacitacion(rs.getTimestamp("hora_capacitacion"));
			registro.setLugarCapacitacion(rs.getString("lugar_capacitacion"));
			registro.setCalle(rs.getString("calle"));
			registro.setNumInt(rs.getString("numero_interior"));
			registro.setNumExt(rs.getString("numero_exterior"));
			registro.setColonia(rs.getString("colonia"));
			registro.setMunicipio(rs.getString("municipio"));
			registro.setIsNombramiento(rs.getBoolean("is_nombramiento"));
			
			lstRegistro.add(registro);
			
			
		} while (rs.next());
					
		return lstRegistro;
	}

}
