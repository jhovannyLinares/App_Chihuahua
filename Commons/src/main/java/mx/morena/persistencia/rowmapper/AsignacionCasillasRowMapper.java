package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.ActasVotacion;
import mx.morena.persistencia.entidad.AfluenciasVoto;
import mx.morena.persistencia.entidad.AsignacionCasillas;

public class AsignacionCasillasRowMapper implements RowMapper<List<AsignacionCasillas>> {

	@Override
	public List<AsignacionCasillas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<AsignacionCasillas> casillas = new ArrayList<AsignacionCasillas>();

		AsignacionCasillas casilla = null;
		AfluenciasVoto afluencia = null;
		ActasVotacion actas = null;
		
		
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
			
			afluencia = new AfluenciasVoto();
			if(existeColumna(rs, "hrs_12")) {
				afluencia.setHrs12(rs.getBoolean("hrs_12"));
				afluencia.setHrs16(rs.getBoolean("hrs_16"));
				afluencia.setHrs18(rs.getBoolean("hrs_18"));
			}
			casilla.setAfluenciasVoto(afluencia);
			
			actas = new ActasVotacion();
			if(existeColumna(rs, "actas_gobernador")) {
				actas.setGobernador(rs.getBoolean("actas_gobernador"));
				actas.setDiputadoFedaral(rs.getBoolean("actas_federal"));
				actas.setDiputadoLocal(rs.getBoolean("actas_local"));
				actas.setPresidenteMunicipal(rs.getBoolean("actas_municipal"));
				actas.setSindico(rs.getBoolean("actas_sindico"));
			}
			casilla.setActasVotacion(actas);
			
			
			casillas.add(casilla);

		} while (rs.next());

		return casillas;
	}
	
	private boolean existeColumna(ResultSet rs, String campo) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		 int columns = metaData.getColumnCount();
		    for (int x = 1; x <= columns; x++) {
		        if (campo.equals(metaData.getColumnName(x))) {
		            return true;
		        }
		    }
		
		return false;
	}

}
