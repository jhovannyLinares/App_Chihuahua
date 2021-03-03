package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
@Repository
public interface ISeccionElectoralRepository extends JpaRepository<SeccionElectoral, Long>{

}
