package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.ReporteCasilla;

public class EstadoVotacionRowMapper implements RowMapper<List<EstadoVotacion>>{

	@Override
	public List<EstadoVotacion> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<EstadoVotacion> reporteVotaciones = new ArrayList<EstadoVotacion>();

		EstadoVotacion reporteVoto = null;
		do {
			
			reporteVoto = new EstadoVotacion();

			reporteVoto.setIdCasilla(rs.getLong("id_casilla"));
			reporteVoto.setSeInstalo(rs.getBoolean("se_instalo"));
			reporteVoto.setLlegoRc(rs.getString("llego_rc"));
			reporteVoto.setComenzoVotacion(rs.getBoolean("comenzo_votacion"));
			
			
			

			reporteVotaciones.add(reporteVoto);

		} while (rs.next());

		return reporteVotaciones;

	}

}
