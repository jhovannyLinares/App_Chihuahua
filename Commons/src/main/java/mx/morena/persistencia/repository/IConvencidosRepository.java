package mx.morena.persistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Convencidos;

@Repository
public interface IConvencidosRepository extends JpaRepository<Convencidos, Long>{
	
	 Convencidos findByClaveElector(String claveElector);
	
	 List<Convencidos> getByIdFederal(Long idFederal);
	 
	 List<Convencidos> getByIdMunicipio(Long idMunicipio);
	 
	 List<Convencidos> getByIdSeccion(Long idSeccion);

}
