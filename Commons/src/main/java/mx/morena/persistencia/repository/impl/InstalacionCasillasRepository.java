package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.List;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.rowmapper.InstalacionRowMapper;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class InstalacionCasillasRepository implements IInstalacionCasillasRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(InstalacionCasilla ic) {
		String sql = "INSERT INTO app_instalacion_casilla (id, " + "id_casilla, " + "hora_instalacion, " + "llegaron_funcionarios, " + "funcionarios_de_fila, " 
				+ " paquete_completo, " + "llego_rg, " + "desayuno, " + "inicio_votacion," + "llego_rc) "
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_instalacion_casilla), 0)+1, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		try {
		template.update(sql, new Object[] {ic.getIdCasilla(), ic.getHoraInstalacion(), ic.getLlegaronFuncionarios(), ic.getFuncionariosFila(),
				 ic.getPaqueteCompleto(), ic.getLlegoRg(), ic.getDesayuno(), ic.getInicioVotacion(), ic.getLlegoRc()});
		return 1;
		} catch (Exception e) {
			return 0;
		}
	
	}

	@Override
	public Long getMaxId() {
		String sql = "SELECT MAX(id) FROM app_instalacion_casilla";
		return template.queryForObject(sql, new LongRowMapper());
	}

	@Override
	public List<InstalacionCasilla> getById(Long idCasilla) {

		String sql = " SELECT id, id_casilla, llegaron_funcionarios, funcionarios_de_fila, paquete_completo, llego_rg, desayuno, hora_instalacion, inicio_votacion, llego_rc "
				+ " FROM public.app_instalacion_casilla "
				+ " where id_casilla = ? ";
		try {
			
			return template.queryForObject(sql, new Object[] { idCasilla }, new int[] { Types.NUMERIC },
					new InstalacionRowMapper());

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Long getCountRgByDfAndAsistencia(Long idFederal, String SI) {
		String sql = "select count(*) from app_instalacion_casilla aic " 
				+"inner join app_casilla ac "
				+"on aic.id_casilla = ac.id " 
				+"where ac.federal_id = ? and aic.llego_rg = ?";
		return template.queryForObject(sql, new Object[] { idFederal, SI }, new int[] { Types.NUMERIC, Types.VARCHAR }, new LongRowMapper());
	}

	@Override
	public Long getCountRcByDfAndAsistencia(Long idFederal, String SI) {
		String sql = "select count(*) from app_instalacion_casilla aic " 
				+"inner join app_casilla ac "
				+"on aic.id_casilla = ac.id " 
				+"where ac.federal_id = ? and aic.llego_rc = ?";
		return template.queryForObject(sql, new Object[] { idFederal, SI }, new int[] { Types.NUMERIC, Types.VARCHAR }, new LongRowMapper());
	}

	
}
