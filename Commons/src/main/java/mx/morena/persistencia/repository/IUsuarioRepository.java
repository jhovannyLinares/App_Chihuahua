package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Modulo;
import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Usuario;

public interface IUsuarioRepository {

	Usuario findByUsuario(String usuario);

	Usuario findById(long idUsuario);

	boolean update(Usuario usuario);

	Perfil findPerfilById(long perfil);

	List<Modulo> findModulosByPerfil(Long perfil);

}
