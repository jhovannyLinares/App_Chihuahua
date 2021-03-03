package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Municipio;
@Repository
public interface IMunicipioRepository extends JpaRepository<Municipio, Long>{

}
