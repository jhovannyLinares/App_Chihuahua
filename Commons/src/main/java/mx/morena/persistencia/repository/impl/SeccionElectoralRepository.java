package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;

@Repository
public class SeccionElectoralRepository implements ISeccionElectoralRepository {

	@Override
	public List<SeccionElectoral> findByCotId(Long cotId) {
		// TODO Auto-generated method stub
		return null;
	}

//	List<SeccionElectoral> findByCotId(Long cotId);

}
