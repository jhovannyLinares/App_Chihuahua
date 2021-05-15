package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.AsignacionCasillas;

public class AsignacionCasillasRowMapper implements RowMapper<List<AsignacionCasillas>> {

	@Override
	public List<AsignacionCasillas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<AsignacionCasillas> casillas = new ArrayList<AsignacionCasillas>();

		AsignacionCasillas casilla = null;
		do {
			casilla = new AsignacionCasillas();
			casilla.setId(rs.getLong("id"));
			casilla.setFederalId(rs.getLong("distrito_federal_id"));
			casilla.setNombreDistrito(rs.getString("nombre_distrito"));
			casilla.setZonaCrg(rs.getLong("zona_crg"));
			casilla.setIdZonaCrg(rs.getString("id_zona_crg"));
			casilla.setRuta(rs.getLong("ruta"));
			casilla.setIdCasilla(rs.getLong("id_casilla"));
			casilla.setTipoCasilla(rs.getString("tipo_casilla"));
			casilla.setSeccionId(rs.getLong("seccion_id"));
			casilla.setStatus(rs.getLong("status"));
			casilla.setIdRutaRg(rs.getString("id_ruta_rg"));
			casilla.setIdCrg(rs.getLong("id_crg"));
			
			if (rs.findColumn("isOpen") > 0) {
				casilla.setOpen(rs.getLong("isOpen"));
			}
			casilla.setSeInstalo(rs.getBoolean("se_instalo"));
			casilla.setLlegoRc(rs.getString("llego_rc"));
			casilla.setComenzoVotacion(rs.getBoolean("comenzo_votacion"));

			casillas.add(casilla);

		} while (rs.next());

		return casillas;
	}

}
