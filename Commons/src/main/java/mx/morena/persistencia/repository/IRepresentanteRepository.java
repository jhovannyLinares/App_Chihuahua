package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Representantes;

@Repository
public interface IRepresentanteRepository extends JpaRepository<Representantes, Long>{
	
	Representantes findByClaveElector(String claveElector);
	
}
