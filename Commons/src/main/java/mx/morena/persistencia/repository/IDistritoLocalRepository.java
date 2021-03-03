package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.DistritoLocal;

@Repository
public interface IDistritoLocalRepository extends JpaRepository<DistritoLocal, Long> {

}
