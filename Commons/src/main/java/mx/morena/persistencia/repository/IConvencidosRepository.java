package mx.morena.persistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;

@Repository
public interface IConvencidosRepository extends JpaRepository<Convencidos, Long>{
	
	 List<Convencidos> getByClaveElector(String claveElector);
	
	 List<Convencidos> getByDistritoFederal(Long idFederal);
	 
	 List<Convencidos> getByMunicipio(Long idMunicipio);
	 
	 List<Convencidos> getBySeccionesElectorales(Long idSeccion);
	 
	 Convencidos findByClaveElector(String claveElector);

}