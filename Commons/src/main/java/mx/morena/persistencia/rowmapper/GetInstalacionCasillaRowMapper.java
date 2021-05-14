package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.InstalacionCasilla;

public class GetInstalacionCasillaRowMapper implements RowMapper<InstalacionCasilla> {

	@Override
	public InstalacionCasilla mapRow(ResultSet rs, int rowNum) throws SQLException {

		InstalacionCasilla casilla = new InstalacionCasilla();
		
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
		
		// nuevos campos
		casilla.setInstaloCasilla(rs.getString("instalo_casilla"));
		casilla.setInstaloLugarDistinto(rs.getString("instalo_lugar_distinto"));
		casilla.setCausaLugarDistinto(rs.getString("causa_lugar_distinto"));
		
		casilla.setBoletasGobernador(rs.getLong("boletas_gobernador"));
		casilla.setFolioIniGobernador(rs.getLong("folio_inicial_gobernador"));
		casilla.setFolioFinGobernador(rs.getLong("folio_final_gobernador"));
		
		casilla.setBoletasDipLocal(rs.getLong("boletas_diputado_local"));
		casilla.setFolioIniDipLocal(rs.getLong("folio_inicial_diputado_local"));
		casilla.setFolioFinDipLocal(rs.getLong("folio_final_diputado_local"));
		
		casilla.setBoletasPresidenteMunicipal(rs.getLong("boletas_presidente_municipal"));
		casilla.setFolioIniPresidenteMunicipal(rs.getLong("folio_inicial_presidente_municipal"));
		casilla.setFolioFinPresidenteMunicipal(rs.getLong("folio_final_presidente_municipal"));
		
		casilla.setBoletasSindico(rs.getLong("boletas_sindico"));
		casilla.setFolioIniSindico(rs.getLong("folio_inicial_sindico"));
		casilla.setFolioFinSindico(rs.getLong("folio_final_sindico"));
		
		casilla.setBoletasDipFederal(rs.getLong("boletas_diputado_federal"));
		casilla.setFolioIniDipFederal(rs.getLong("folio_inicial_diputado_federal"));
		casilla.setFolioFinDipFederal(rs.getLong("folio_final_diputado_federal"));
		
		casilla.setSellaronBoletas(rs.getString("sellaron_boletas"));
		casilla.setIdPartidoSello(rs.getLong("id_partido_sello"));
		
		casilla.setNombrePresidente(rs.getString("nombre_presidente"));
		casilla.setNombreSecretario(rs.getString("nombre_secretario"));
		casilla.setNombreEscrutador1(rs.getString("nombre_escrutador_1"));
		casilla.setNombreEscrutador2(rs.getString("nombre_escrutador_2"));
		casilla.setNombreEscrutador3(rs.getString("nombre_escrutador_3"));
		
		

		return casilla;
	}

}
