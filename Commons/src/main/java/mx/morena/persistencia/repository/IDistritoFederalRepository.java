package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.DistritoFederal;

@Repository
public interface IDistritoFederalRepository extends JpaRepository<DistritoFederal, Long> {

}
