package mx.morena.persistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsuario(String usuario);

}
