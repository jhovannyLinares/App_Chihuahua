package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;
import mx.morena.persistencia.entidad.Modulo;
import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.dto.ModuloDTO;
import mx.morena.security.dto.UsuarioDTO;
import mx.morena.security.servicio.Token;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public UsuarioDTO login(String username, String pwd) throws UsuarioException {

		Usuario usuario = usuarioRepository.findByUsuario(username);

		boolean usuarioValido = false;

		if (usuario != null) {

			if (usuario.getPassword().equalsIgnoreCase(pwd)) {
				usuarioValido = true;
			} else {
				throw new UsuarioException("Contraseña no valida", 401);
			}

		} else {
			throw new UsuarioException("Usuario no existe", 401);
		}

		if (usuarioValido) {

			UsuarioDTO user = new UsuarioDTO();
			user.setUsuario(username);
			user.setPerfil(usuario.getPerfil().getNombre());
			user.setModulos(getModulos(usuario.getPerfil()));
			user.setToken(Token.create(username, usuario.getPerfil().getId()));

			return user;
		}

		return null;
	}

	private List<ModuloDTO> getModulos(Perfil perfil) {

		List<ModuloDTO> modulosDTO = new ArrayList<ModuloDTO>();

		List<Modulo> modulos = perfil.getModulos();

		for (Modulo modulo : modulos) {
			if (modulo.getModuloPadre() == null) {

				modulosDTO.add(convert(modulo));

			}

		}

		for (Modulo modulo : modulos) {
			if (modulo.getModuloPadre() != null) {
				for (ModuloDTO moduloDTO : modulosDTO) {
					List<ModuloDTO> subModulos = moduloDTO.getSubModulos();
					subModulos.add(convert(modulo));
				}
			}
		}

		return modulosDTO;
	}

	private ModuloDTO convert(Modulo modulo) {

		ModuloDTO dto = new ModuloDTO();

		dto.setModuloId(modulo.getId());
		dto.setDescripcion(modulo.getDescripcion());
		dto.setUrl(modulo.getUrl());
		dto.setSubModulos(new ArrayList<ModuloDTO>());

		return dto;
	}

}