package mx.morena.negocio.servicios;

import mx.morena.negocio.dto.UsuarioRequest;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.security.dto.UsuarioDTO;

public interface IUsuarioService {

	UsuarioDTO login(String username, String pwd) throws UsuarioException;

	Boolean updatePwd(long idUsuario, UsuarioRequest usuario) throws UsuarioException;
	
	Boolean getRepresentante(Long idPerfil, Long idUsuario, String claveElector) throws UsuarioException;

}
