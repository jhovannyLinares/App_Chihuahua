package mx.morena.persistencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.rowmapper.LongRowMapper;

@Repository
public class InstalacionCasillasRepository implements IInstalacionCasillasRepository{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public int save(InstalacionCasilla ic) {
		String sql = "INSERT INTO app_instalacion_casilla (id, " + "id_casilla, " + "hora_instalacion, " + "llegaron_funcionarios, " + "funcionarios_de_fila, " 
				+ " paquete_completo, " + "llego_rg, " + "desayuno, " + "inicio_votacion) "
				+ " VALUES ((SELECT MAX(id)+1 FROM app_instalacion_casilla), ?, ?, ?, ?, ?, ?, ?, ? )";
		try {
		template.update(sql, new Object[] {ic.getIdCasilla(), ic.getHoraInstalacion(), ic.getLlegaronFuncionarios(), ic.getFuncionariosFila(),
				 ic.getPaqueteCompleto(), ic.getLlegoRg(), ic.getDesayuno(), ic.getInicioVotacion()});
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

	
}
