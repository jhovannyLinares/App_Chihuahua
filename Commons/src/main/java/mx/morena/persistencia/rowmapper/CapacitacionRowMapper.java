package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Capacitacion;
import mx.morena.persistencia.entidad.RegistroCapacitados;

public class CapacitacionRowMapper implements RowMapper<List<Capacitacion>>{

	@Override
	public List<Capacitacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Capacitacion> lstCap = new ArrayList<Capacitacion>();
		Capacitacion rep = null;
		RegistroCapacitados  reg = null;
		
		do {
			rep = new Capacitacion();
			
			rep.setIdRepresentante(rs.getLong("id"));
			rep.setClaveElector(rs.getString("clave"));
			rep.setTipoRepresentante(rs.getString("representante"));
			rep.setAsignado(rs.getLong("asignado"));
			rep.setCargo(rs.getString("cargo"));
			rep.setIsNombramiento(rs.getBoolean("nombramiento"));
			reg = new RegistroCapacitados();
			reg.setTomoCapacitacion(rs.getString("capacitacion"));
			reg.setFechaCapacitaion(rs.getString("fecha"));
			reg.setHoraCapacitacion(rs.getTime("hora"));
			reg.setLugarCapacitacion(rs.getString("lugar"));
			reg.setCalle(rs.getString("calle"));
			reg.setNumExt(rs.getString("exterior"));
			reg.setNumInt(rs.getString("interior"));
			reg.setColonia(rs.getString("colonias"));
			reg.setMunicipio(rs.getString("municipio"));
			
			rep.setCapacitacion(reg);
			
			lstCap.add(rep);
			
		}while (rs.next());
		
		return lstCap;
	}

}
