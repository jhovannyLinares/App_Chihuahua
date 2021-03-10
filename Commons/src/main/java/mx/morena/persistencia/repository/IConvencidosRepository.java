package mx.morena.persistencia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;

@Repository
public interface IConvencidosRepository extends JpaRepository<Convencidos, Long>{
	
	 List<Convencidos> getByClaveElector(String claveElector);
	
	 List<Convencidos> getByDistritoFederal(Optional<DistritoFederal> dFederal);
	 
	 List<Convencidos> getByMunicipio(Optional<Municipio> municipio);
	 
	 List<Convencidos> getBySeccionesElectorales(Long idSeccion);
	 
	 Convencidos findByClaveElector(String claveElector);

	 public Convencidos getByCurp(String curp);
	 
	 public Convencidos getByIdAndEstatus(Long idCot, char estatus);
}
