package mx.morena.persistencia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.SeccionElectoral;
@Repository
public interface ISeccionElectoralRepository extends JpaRepository<SeccionElectoral, Long>{

	List<SeccionElectoral> findByCotId(Long cotId);

}
