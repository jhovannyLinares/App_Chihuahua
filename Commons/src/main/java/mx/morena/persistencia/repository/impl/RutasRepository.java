package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;
import mx.morena.persistencia.entidad.Rutas;
import mx.morena.persistencia.repository.IRutasRepository;

@Repository
public class RutasRepository implements IRutasRepository{
	
	@Autowired
	private JdbcTemplate templete;

	@Override
	public List<Representantes> getAllCrg(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rutas> findByCrgId(Long crgId, Long tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rutas getByIdAndEstatus(Long idCrg, char estatus, Long tipo) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public List<Rutas> findById(Long idRutas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIdCrg(Long idRuta, Long idCrg) {
		// TODO Auto-generated method stub
		
	}
	
	

}
