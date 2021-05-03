package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.InstalacionCasilla;

public class InstalacionRowMapper implements RowMapper<List<InstalacionCasilla>> {

	@Override
	public List<InstalacionCasilla> mapRow(ResultSet rs, int rowNum) throws SQLException {


		List<InstalacionCasilla> casillas = new ArrayList<InstalacionCasilla>();

		InstalacionCasilla casilla;

		do {

			casilla = new InstalacionCasilla();

			casilla.setDesayuno(rs.getString("desayuno"));
			casilla.setFuncionariosFila(rs.getString("funcionarios_de_fila"));
			casilla.setHoraInstalacion(rs.getTimestamp("hora_instalacion"));
			casilla.setId(rs.getLong("id"));
			casilla.setIdCasilla(rs.getLong("id_casilla"));
			casilla.setInicioVotacion(rs.getTimestamp("inicio_votacion"));
			casilla.setLlegaronFuncionarios(rs.getString("llegaron_funcionarios"));
			casilla.setLlegoRc(rs.getString("llego_rc"));
			casilla.setLlegoRg(rs.getString("llego_rg"));
			casilla.setPaqueteCompleto(rs.getString("paquete_completo"));

			casillas.add(casilla);

		} while (rs.next());

		return casillas;

	}

//	List<DistritoFederal> distritoFederals = new ArrayList<DistritoFederal>();
//
//	DistritoFederal distritoFederal = null;
//	do {
//
//		distritoFederal = new DistritoFederal();
//
//		distritoFederal.setId(rs.getLong("id"));
//		distritoFederal.setCabeceraFederal(rs.getLong("id")   +" - "+rs.getString("cabecera")); 
//
//		distritoFederals.add(distritoFederal);
//
//	} while (rs.next());
//
//	return distritoFederals;

}
