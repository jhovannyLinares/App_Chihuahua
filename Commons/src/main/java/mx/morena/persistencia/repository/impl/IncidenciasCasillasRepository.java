package mx.morena.persistencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;

@Repository
public class IncidenciasCasillasRepository implements IIncidenciasCasillasRepository {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public int save(IncidenciasCasillas ic) {
		
		String sql = "INSERT INTO app_incidencias_casillas (id,  id_casilla,  id_incidencia )"
				+ " VALUES (COALESCE((SELECT MAX(id) FROM app_incidencias_casillas), 0)+1, ?, ?)";	
//				+ " VALUES ((SELECT MAX(id)+1 FROM app_incidencias_casillas), ?, ?)";	

		try {
			template.update(sql, new Object[] {ic.getIdCasilla(), ic.getIdIncidencia()});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	
	}

}
