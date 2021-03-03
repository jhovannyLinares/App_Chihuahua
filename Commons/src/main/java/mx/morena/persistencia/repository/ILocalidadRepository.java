package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Localidad;
@Repository
public interface ILocalidadRepository extends JpaRepository<Localidad, Long>{

}
