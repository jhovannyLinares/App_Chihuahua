package mx.morena.persistencia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.EnvioActas;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.rowmapper.IdMaxConvencidos;
import mx.morena.persistencia.rowmapper.IdMaxEnvioActasRowMapper;

@Repository
public class EnvioActasRepository implements IEnvioActasRepository {
	
	@Autowired
	private JdbcTemplate template;
	
	private String campos = " tipo_votacion,ruta_acta,id_casilla,registro_acta ";

	@Override
	public void save(EnvioActas Actas) {
		String sql = " INSERT INTO app_envio_actas ( " + campos +" ) VALUES(?,?,?,?)";
		
		template.update(sql, 
				new Object[] { Actas.getTipo_votacion(), Actas.getRuta_acta(), Actas.getId_casilla(), Actas.getRegistro_acta() });

				
		
	}

	@Override
	public Long getIdMax() {
		String sql = "SELECT MAX(id_acta) FROM app_envio_actas";
		return template.queryForObject(sql, new IdMaxEnvioActasRowMapper());
	}

}
