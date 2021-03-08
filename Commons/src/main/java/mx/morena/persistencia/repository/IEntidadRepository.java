package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Entidad;

@Repository
public interface IEntidadRepository extends JpaRepository<Entidad, Long> {

}
