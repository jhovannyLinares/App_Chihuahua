package mx.morena.negocio.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.UsuarioRequest;
import mx.morena.negocio.exception.UsuarioException;
import mx.morena.negocio.servicios.IUsuarioService;
import mx.morena.persistencia.entidad.Modulo;
import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.dto.ModuloDTO;
import mx.morena.security.dto.UsuarioDTO;
import mx.morena.security.servicio.MasterService;
import mx.morena.security.servicio.Token;

@Service
public class UsuarioServiceImpl extends MasterService implements IUsuarioService {

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

			if (usuarioValido) {

				UsuarioDTO user = new UsuarioDTO();
				user.setId(usuario.getId());
				user.setUsuario(username);

				Perfil perfil = usuarioRepository.findPerfilById(usuario.getPerfil());

				user.setPerfil(perfil.getNombre());
				user.setToken(Token.create(username, usuario.getPerfil(), usuario.getId()));
				user.setExpiration(Token.segundos);

				user.setModulos(getModulos(usuario.getPerfil()));

				return user;
			}

		}

		throw new UsuarioException("Usuario no existe", 401);

	}

	private List<ModuloDTO> getModulos(Long perfil) {

		List<ModuloDTO> modulosDTO = new ArrayList<ModuloDTO>();

		List<Modulo> modulos = usuarioRepository.findModulosByPerfil(perfil);

		for (Modulo modulo : modulos) {
			if (modulo.getModuloPadre() == 0) {
				modulosDTO.add(convert(modulo));
			}

		}

		for (Modulo modulo : modulos) {
			if (modulo.getModuloPadre() != 0) {
				for (ModuloDTO moduloDTO : modulosDTO) {
					if (modulo.getModuloPadre().equals(moduloDTO.getModuloId())) {
						List<ModuloDTO> subModulos = moduloDTO.getSubModulos();
						ModuloDTO mod = convert(modulo);
						mod.setModuloPadre(modulo.getModuloPadre());
						subModulos.add(mod);
					}
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

	@Override
	public Boolean updatePwd(long idUsuario, UsuarioRequest usuarioUpdate) throws UsuarioException {

		Usuario usuario = usuarioRepository.findById(idUsuario);

		usuario.setEmail(usuarioUpdate.getEmail());
		usuario.setPassword(usuarioUpdate.getPassword());

		usuarioRepository.update(usuario);

		return true;

	}

	@Override
	public Boolean getRepresentante(Long idPerfil, Long idUsuario, String claveElector) throws UsuarioException {
		
		if (idPerfil == PERFIL_RC || idPerfil == PERFIL_RG) {
			
			String clave = usuarioRepository.getClaveElector(idUsuario);
				
			if(clave.equals(claveElector)) {
				return true;
			}else {
				return false;
			}
				
			}else {
				throw new UsuarioException("No cuenta con permisos suficientes para realizar la consulta", 401);
			}
	}

}
