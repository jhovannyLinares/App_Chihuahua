package mx.morena.negocio.servicios;

import mx.morena.negocio.exception.UsuarioException;
import mx.morena.security.dto.UsuarioDTO;

public interface IUsuarioService {

	UsuarioDTO login(String username, String pwd) throws UsuarioException;

}