package mx.morena.persistencia.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.repository.IConvencidosRepository;

@Repository
public class ConvencidosRepository implements IConvencidosRepository {

	@Override
	public List<Convencidos> getByClaveElector(String claveElector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Convencidos> getByDistritoFederal(DistritoFederal dFederal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Convencidos> getByMunicipio(Municipio municipio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Convencidos> getBySeccionesElectoralesIn(List<SeccionElectoral> seccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convencidos findByClaveElector(String claveElector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convencidos getByCurp(String curp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convencidos getByIdAndEstatus(Long idCot, char estatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Convencidos convencidos) {
		// TODO Auto-generated method stub
		
	}

}
