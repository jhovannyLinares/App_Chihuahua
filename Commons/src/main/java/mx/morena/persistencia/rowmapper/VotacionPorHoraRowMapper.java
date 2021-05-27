package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.VotacionPorHora;

public class VotacionPorHoraRowMapper implements RowMapper<VotacionPorHora> {

	@Override
	public VotacionPorHora mapRow(ResultSet rs, int rowNum) throws SQLException {

		VotacionPorHora vph = new VotacionPorHora();
		
		vph.setOnce(rs.getLong("once"));
		vph.setTres(rs.getLong("tres"));
		vph.setSeis(rs.getLong("seis"));
		
		return vph;
	}

}
