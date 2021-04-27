package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Casilla;

public class CasillasRowMapper implements RowMapper<List<Casilla>> {

	@Override
	public List<Casilla> mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<Casilla> casillas = new ArrayList<Casilla>();

		Casilla casilla = null;
		do {
			casilla = new Casilla();
			casilla.setId(rs.getLong("id"));
			casilla.setFederal(rs.getLong("federal_id"));
			casilla.setEntidad(rs.getLong("entidad_id"));
			casilla.setMunicipio(rs.getLong("municpio_id"));
			casilla.setSeccionElectoral(rs.getLong("seccion_id"));
			casilla.setTipo(rs.getString("tipo_casilla"));
			casilla.setTipologia(rs.getString("tipologia"));
			casilla.setTipoDomicilio(rs.getString("tipo_domicilio"));
			casilla.setCalle(rs.getString("calle"));
			casilla.setNumero(rs.getString("numero"));
			casilla.setColonia(rs.getString("colonia"));
			casilla.setCp(rs.getString("cp"));
			casilla.setUbicacion(rs.getString("ubicacion"));
			casilla.setReferencia(rs.getString("referencia"));
			casilla.setAsignado(rs.getBoolean("is_asignada"));

			casillas.add(casilla);

		} while (rs.next());

		return casillas;
	}

}
